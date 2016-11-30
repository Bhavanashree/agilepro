package com.agilepro.services.admin;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.controller.IAgileProConstants;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.persistence.repository.admin.ICustomerRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.utils.exceptions.NullValueException;
import com.yukthi.webutils.common.models.mails.EmailServerSettings;
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
	 * The icustomer repository.
	 **/
	private ICustomerRepository customerRepository;

	/**
	 * Instantiates a new customer service.
	 */
	public CustomerService()
	{
		super(CustomerEntity.class, ICustomerRepository.class);
	}

	/**
	 * Inits the icustomerRepository.
	 */
	@PostConstruct
	private void init()
	{
		customerRepository = repositoryFactory.getRepository(ICustomerRepository.class);
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
			/*
			 * NotificationMailDetails notificationMailDetails = new
			 * NotificationMailDetails(model.getEmail(), model.getPassword(),
			 * mailSmtpHost, mailSmtpPort, false);
			 * 
			 * model.setNotificationMailDetails(notificationMailDetails);
			 */

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
	 * 
	 * @param id
	 *            Entity id to delete
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
		userEntity.setSpaceIdentity(IAgileProConstants.customerSpace(customerEntity.getId()));

		UserRoleEntity roleEntity = new UserRoleEntity();
		roleEntity.setOwnerType(Object.class.getName());
		roleEntity.setOwnerId(0L);
		roleEntity.setRole(UserRole.CUSTOMER_SUPER_USER);
		roleEntity.setUser(userEntity);
		roleEntity.setSpaceIdentity(IAgileProConstants.customerSpace(customerEntity.getId()));

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
	 * Fetch customer by email.
	 *
	 * @param emailId
	 *            the email id
	 * @return the customer model
	 */
	public CustomerModel fetchCustomerByEmail(String emailId)
	{
		return super.toModel(customerRepository.fetchCustomerByEmail(emailId), CustomerModel.class);
	}

	/**
	 * Fetches all available customers.
	 * 
	 * @return All customers.
	 */
	public List<CustomerEntity> fetchAllCustomers()
	{
		return customerRepository.fetchAllCustomers();
	}

	/**
	 * Fetch by no space.
	 *
	 * @param customerId
	 *            the customer id
	 * @return the customer entity
	 */
	public CustomerEntity fetchByNoSpace(Long customerId)
	{
		return customerRepository.fetchCustomerWithNoSpace(customerId);
	}
	
	/**
	 * Update mail server setting.
	 *
	 * @param settings the settings
	 * @param customerId the customer id
	 * @return true, if successful
	 */
	public boolean updateMailServerSetting(EmailServerSettings settings, Long customerId)
	{
		return customerRepository.updateMailServerSetting(settings, customerId);
	}

	/**
	 * Fetch customer name.
	 *
	 * @param customerId
	 *            the customer id
	 * @return the string
	 */
	public String fetchCustomerName(Long customerId)
	{
		return customerRepository.fetchCustomerName(customerId);
	}

	/**
	 * Fetch customer sub domain path.
	 *
	 * @param customerId
	 *            the customer id
	 * @return the string
	 */
	public String fetchCustomerSubDomainPath(Long customerId)
	{
		String str = customerRepository.fetchCustomerSubDomain(customerId);
		return str;
	}

	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		super.repository.deleteAll();
	}
}
