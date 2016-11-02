package com.agilepro.services.admin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.CustomerSettingModel;
import com.agilepro.controller.AgileProUserDetails;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.persistence.entity.admin.CustomerSettingEntity;
import com.agilepro.persistence.repository.admin.ICustomerSettingRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;

/**
 * The Class CustomerSettingService.
 */
@Service
public class CustomerSettingService extends BaseCrudService<CustomerSettingEntity, ICustomerSettingRepository>
{
	/**
	 * Used to fetch current user info.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * The repository factory.
	 **/
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * The iuser setting repository.
	 */
	private ICustomerSettingRepository custSettingRepository;

	/** 
	 * The customer setting for update. 
	 **/
	private CustomerSettingEntity customerSettingForUpdate;
	
	/** 
	 * The new setting. 
	 **/
	private CustomerSettingEntity newSetting;

	/**
	 * Instantiates a new user setting service.
	 */
	public CustomerSettingService()
	{
		super(CustomerSettingEntity.class, ICustomerSettingRepository.class);
	}

	/**
	 * Initialize the iuserSettingRepository.
	 */
	@PostConstruct
	private void init()
	{
		custSettingRepository = repositoryFactory.getRepository(ICustomerSettingRepository.class);
	}

	/**
	 * Gets the setting.
	 *
	 * @param key
	 *            the key
	 * @return the setting
	 */
	public CustomerSettingModel getSetting(String key)
	{
		AgileProUserDetails cbiller = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();

		CustomerSettingEntity customerSettingEntity = custSettingRepository.fetchCustomerSetting(customerId, key);

		return super.toModel(customerSettingEntity, CustomerSettingModel.class);
	}

	/**
	 * Check exsiting holidays.
	 *
	 * @param customerId
	 *            the customer id
	 * @param key
	 *            the key
	 * @return true, if successful
	 */
	private boolean checkExsitingHolidays(Long customerId, String key)
	{
		customerSettingForUpdate = custSettingRepository.fetchCustomerSetting(customerId, key);

		if(customerSettingForUpdate != null)
		{
			return true;
		}

		return false;
	}

	/**
	 * Sets the settings.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the customer setting entity
	 */
	public CustomerSettingEntity setSettings(String key, Object value)
	{
		AgileProUserDetails cbiller = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();
		CustomerSettingEntity custSettingentity = null;

		if(checkExsitingHolidays(customerId, key))
		{
			customerSettingForUpdate.setCustomer(new CustomerEntity(customerId));
			customerSettingForUpdate.setValue(value);
			custSettingentity = super.update(customerSettingForUpdate);
			customerSettingForUpdate = null;
		}
		else
		{
			newSetting = new CustomerSettingEntity();
			newSetting.setKey(key);
			newSetting.setValue(value);
			newSetting.setCustomer(new CustomerEntity(customerId));
			custSettingentity = super.save(newSetting);
		}
		return custSettingentity;
	}

	/**
	 * Fetch customer setting.
	 *
	 * @return the customer setting model
	 */
	public CustomerSettingModel fetchCustomerSetting()
	{
		AgileProUserDetails cbiller = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();

		CustomerSettingEntity customerSettingEntity = custSettingRepository.fetchCustomerSetting(customerId, "weekHolidays");

		return super.toModel(customerSettingEntity, CustomerSettingModel.class);
	}
}
