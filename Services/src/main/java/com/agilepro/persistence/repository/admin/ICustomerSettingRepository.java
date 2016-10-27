package com.agilepro.persistence.repository.admin;

import com.agilepro.persistence.entity.admin.CustomerSettingEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface ICustomerSettingRepository.
 */
public interface ICustomerSettingRepository extends IWebutilsRepository<CustomerSettingEntity>
{
	
	/**
	 * Fetch customer setting.
	 *
	 * @param id
	 *            the id
	 * @param key
	 *            the key
	 * @return the customer setting entity
	 */
	@RestrictBySpace
	public CustomerSettingEntity fetchCustomerSetting(@Condition(value = "customerEntity.id") Long id, @Condition(value = "key") String key);
}
