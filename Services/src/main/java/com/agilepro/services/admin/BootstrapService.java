package com.agilepro.services.admin;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.UserRole;
import com.agilepro.controller.IAgileProConstants;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.bootstrap.BootstrapManager;
import com.yukthi.webutils.repository.IUserRepository;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.UserRoleEntity;
import com.yukthi.webutils.services.UserRoleService;
import com.yukthi.webutils.services.UserService;

/**
 * Service to bootstrap the system (set the initial configuration for first time
 * use).
 * 
 * @author akiran
 */
@Service
public class BootstrapService
{
	private static Logger logger = LogManager.getLogger(BootstrapService.class);

	/**
	 * Default user to be created when system starts up.
	 */
	@Autowired(required = false)
	private UserEntity defaultUser;

	/**
	 * Service to add default user.
	 */
	@Autowired
	private UserService userService;

	/**
	 * Service to add default role.
	 */
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * For transaction management.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;
	
	/**
	 * Manager to load bootstrap files if any.
	 */
	@Autowired
	private BootstrapManager bootstrapManager;

	/**
	 * Post construct method. Creates the default user, if configured and does
	 * not exist already in db.
	 */
	@PostConstruct
	private void init() throws Exception
	{
		createDefaultUser();
		bootstrapManager.load();
	}
	
	/**
	 * Creates a default user if one is specified.
	 */
	private void createDefaultUser()
	{
		// if no default user is defined
		if(defaultUser == null)
		{
			return;
		}

		// create default users in db
		if(userService.checkForUser(defaultUser.getUserName(), IAgileProConstants.ADMIN_USER_SPACE))
		{
			logger.info("Ignoring adding default user {} as it is already present", defaultUser.getUserName());
			return;
		}

		IUserRepository repository = repositoryFactory.getRepository(IUserRepository.class);

		// create default admin user
		UserRoleEntity roleEntity = new UserRoleEntity();
		roleEntity.setOwnerType(Object.class.getName());
		roleEntity.setOwnerId(0L);
		roleEntity.setRole(UserRole.ADMINISTRATOR);
		roleEntity.setUser(defaultUser);

		// ensure user and role gets created atomically
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			userService.save(defaultUser, null);
			userRoleService.save(roleEntity, null);

			transaction.commit();
			logger.debug("Added default user with user-name - " + defaultUser.getUserName());
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while creating default user");
		}
	}
}
