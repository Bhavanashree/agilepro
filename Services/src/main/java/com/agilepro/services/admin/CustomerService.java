package com.agilepro.services.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.controller.IRealEstateServerConstants;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.persistence.repository.admin.ICustomerRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.utils.exceptions.NullValueException;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.UserRoleEntity;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserRoleService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class CustomerService.
 * 
 * @author Pritam
 */
@Service
public class CustomerService extends BaseCrudService<CustomerEntity, ICustomerRepository>
{
	/**
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(CustomerService.class);

	/**
	 * Userservice for UserEntity table.
	 */
	@Autowired
	private UserService userService;

	/**
	 * UserRoleService for saving the role.
	 */
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * Instantiates a new customer service.
	 */
	public CustomerService()
	{
		super(CustomerEntity.class, ICustomerRepository.class);
	}

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the customer entity
	 */
	public CustomerEntity save(CustomerModel model)
	{
		if(model == null)
		{
			throw new NullValueException("Customer Model Object is null");
		}

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			// saving customer
			CustomerEntity customerUser = super.save(model);

			// saving user
			saveUser(model, customerUser);

			transaction.commit();
			return customerUser;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while saving model - {}", model);
		}
	}

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the customer entity
	 */
	public CustomerEntity update(CustomerModel model)
	{
		if(model == null)
		{
			throw new NullValueException("CustomerModel Object is null");
		}

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			// updating customer
			CustomerEntity customerUser = super.update(model);

			// updating user
			updateUser(model, customerUser);

			transaction.commit();
			return customerUser;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating model - {}", model);
		}
	}

	/**
	 * Deletes entity with specified id.
	 * @param id Entity id to delete
	 * 
	 * @return boolean result
	 */
	public boolean deleteById(long id)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			userService.deleteByBaseEntity(CustomerEntity.class.getName(), id);
			
			boolean res = super.deleteById(id);

			transaction.commit();
			return res;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while deleting customer with id - {}", id);
		}
	}

	/**
	 * Find customer with subdomain.
	 *
	 * @param subdomain
	 *            the subdomain
	 * @return the customer entity
	 */
	public CustomerEntity findCustomerWithSubdomain(String subdomain)
	{
		return repository.findCustomerBySubdomain(subdomain);
	}

	/**
	 * Save user.
	 *
	 * @param model
	 *            the model
	 * @param customerEntity
	 *            the customer user
	 */
	private void saveUser(CustomerModel model, CustomerEntity customerEntity)
	{
		UserEntity userEntity = new UserEntity();

		// model is having the password
		userEntity.setPassword(model.getPassword());

		userEntity.setUserName(customerEntity.getEmail());
		userEntity.setDisplayName(customerEntity.getName());
		userEntity.setBaseEntityId(customerEntity.getId());
		userEntity.setBaseEntityType(customerEntity.getClass().getName());
		userEntity.setSpaceIdentity( IRealEstateServerConstants.customerSpace(customerEntity.getId()) );

		UserRoleEntity roleEntity = new UserRoleEntity();
		roleEntity.setOwnerType(Object.class.getName());
		roleEntity.setOwnerId(0L);
		roleEntity.setRole(UserRole.CUSTOMER_SUPER_USER);
		roleEntity.setUser(userEntity);
		roleEntity.setSpaceIdentity( IRealEstateServerConstants.customerSpace(customerEntity.getId()) );

		userService.save(userEntity, null);
		userRoleService.save(roleEntity, null);
		logger.debug("Added new Customer with user-name - " + userEntity.getUserName());
	}

	/**
	 * Update user.
	 *
	 * @param model
	 *            the model
	 * @param customerEntity
	 *            the customer user
	 */
	private void updateUser(CustomerModel model, CustomerEntity customerEntity)
	{
		UserEntity userEntity = userService.fetchUserByBaseEntity(customerEntity.getClass().getName(), customerEntity.getId());

		if(userEntity == null)
		{
			throw new InvalidStateException("No user record found with base type details [Type: {}, Id: {}]", customerEntity.getClass().getName(), customerEntity.getId());
		}

		userEntity.setPassword(model.getPassword());
		userEntity.setUserName(model.getEmail());
		userEntity.setDisplayName(model.getName());
		userEntity.setVersion(userEntity.getVersion());

		userService.update(userEntity, null);
		logger.debug("Updated Customer user with user-name - " + userEntity.getUserName());
	}

	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		super.repository.deleteAll();
	}
}
