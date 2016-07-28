package com.agilepro.persistence.repository.admin;

import com.agilepro.persistence.entity.admin.UserSettingEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IUserSettingRepository.
 * 
 * @author Pritam
 */
public interface IUserSettingRepository extends IWebutilsRepository<UserSettingEntity>
{
	/**
	 * Fetch user setting.
	 *
	 * @param id
	 *            the id
	 * @param key
	 *            the key
	 * @return the user setting entity
	 */
	@RestrictBySpace
	public UserSettingEntity fetchUserSetting(@Condition(value = "userEntity.id") Long id, @Condition(value = "key") String key);

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
