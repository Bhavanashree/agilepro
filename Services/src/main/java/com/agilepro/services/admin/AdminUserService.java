package com.agilepro.services.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.admin.AdminUserModel;
import com.agilepro.controller.IRealEstateServerConstants;
import com.yukthi.webutils.repository.IUserRepository;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.UserRoleEntity;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserRoleService;
import com.yukthi.webutils.utils.WebUtils;

/**
 * The Class AdminUserService.
 */
@Service
public class AdminUserService extends BaseCrudService<UserEntity, IUserRepository>
{

	/** 
	 * The logger. 
	 * */
	private static Logger logger = LogManager.getLogger(AdminUserService.class);

	/**
	 *  The user role service. 
	 *  */
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * Instantiates a new admin user service.
	 */
	public AdminUserService()
	{
		super(UserEntity.class, IUserRepository.class);
	}

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the user entity
	 */
	public UserEntity save(AdminUserModel model)
	{

		UserEntity userEntity = null;

		userEntity = (UserEntity) WebUtils.convertBean(model, UserEntity.class);
		userEntity.setSpaceIdentity(IRealEstateServerConstants.ADMIN_USER_SPACE);

		// saving user
		super.save(userEntity, model);

		UserRoleEntity roleEntity = new UserRoleEntity();
		roleEntity.setOwnerType(Object.class.getName());
		roleEntity.setOwnerId(0L);
		roleEntity.setRole(UserRole.ADMINISTRATOR);
		roleEntity.setUser(userEntity);
		roleEntity.setSpaceIdentity(IRealEstateServerConstants.ADMIN_USER_SPACE);

		userRoleService.save(roleEntity, null);
		logger.debug("Added new Admin user with user-name - " + userEntity.getUserName());

		return userEntity;
	}

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the admin user model
	 */
	public AdminUserModel read(Long id)
	{
		AdminUserModel modelFrmDb = super.fetchFullModel(id, AdminUserModel.class);

		modelFrmDb.setPassword(new String());

		return modelFrmDb;
	}

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the user entity
	 */
	public UserEntity update(AdminUserModel model)
	{

		UserEntity adminUser = super.update(model);

		return adminUser;
	}
	
	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	public void delete(long id)
	{
	
		AdminUserModel modelFrmDb = super.fetchFullModel(id, AdminUserModel.class);
		
		modelFrmDb.setDeleted(true);
		
		super.update(modelFrmDb);
	}
	
	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		//super.repository.deleteAll();
	}
}
