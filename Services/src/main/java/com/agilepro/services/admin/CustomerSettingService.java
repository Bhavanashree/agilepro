package com.agilepro.services.admin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.CustomerSettingModel;
import com.agilepro.controller.CbillerUserDetails;
import com.agilepro.persistence.entity.admin.CustomerSettingEntity;
import com.agilepro.persistence.repository.admin.ICustomerSettingRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.services.UserRoleService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class CustomerSettingService.
 */
@Service
public class CustomerSettingService extends BaseCrudService<CustomerSettingEntity, ICustomerSettingRepository>
{
	
	/**
	 *  The Constant HOLIDAY_KEY.
	 **/
	private static final String HOLIDAY_KEY = "listOfHolidays";

	/**
	 * UserService for UserEntity table.
	 */
	@Autowired
	private UserService userService;

	/**
	 * UserRoleService for saving the role.
	 */
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * Used to fetch current user info.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/** 
	 * The customer service.
	 **/
	@Autowired
	private CustomerService customerService;

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
	 * Check exsiting holidays.
	 *
	 * @param customerId
	 *            the customer id
	 * @return true, if successful
	 */
	private boolean checkExsitingHolidays(Long customerId)
	{
		CustomerSettingEntity entity = custSettingRepository.fetchCustomerSetting(customerId, HOLIDAY_KEY);

		if(entity != null)
		{
			return true;
		}

		return false;
	}

	/**
	 * Save holiday.
	 *
	 * @param model
	 *            the model
	 * @return the customer setting entity
	 */
	public CustomerSettingEntity saveHoliday(CustomerSettingModel model)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			CbillerUserDetails cbiller = (CbillerUserDetails) currentUserService.getCurrentUserDetails();

			Long customerId = cbiller.getCustomerId();

			CustomerSettingEntity customerSettingEntity = null;

			if(checkExsitingHolidays(customerId))
			{
				model.setValue(model.getListOfValues());
				model.setCustomerId(customerId);

				// update
				customerSettingEntity = super.update(model);
			}
			else
			{
				CustomerSettingEntity newSetting = new CustomerSettingEntity();
				newSetting.setKey(HOLIDAY_KEY);
				newSetting.setValue(model.getListOfValues());
				newSetting.setCustomerEntity(customerService.fetch(customerId));

				customerSettingEntity = super.save(newSetting);
			}
			transaction.commit();

			return customerSettingEntity;
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while saving  holiday - " + model, ex);
		}
	}

	/**
	 * Fetch customer setting.
	 *
	 * @return the customer setting model
	 */
	public CustomerSettingModel fetchCustomerSetting()
	{
		CbillerUserDetails cbiller = (CbillerUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();

		CustomerSettingEntity customerSettingEntity = custSettingRepository.fetchCustomerSetting(customerId, "weekHolidays");

		return super.toModel(customerSettingEntity, CustomerSettingModel.class);
	}
}
