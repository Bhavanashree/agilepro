package com.agilepro.services.admin;

import org.springframework.stereotype.Service;
import com.agilepro.commons.models.customer.UserSettingModel;
import com.agilepro.persistence.entity.admin.UserSettingEntity;
import com.agilepro.persistence.repository.admin.IUserSettingRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class UserSettingService.
 * 
 * @author Pritam
 */
@Service
public class UserSettingService extends BaseCrudService<UserSettingEntity, IUserSettingRepository>
{
	/**
	 * Instantiates a new user setting service.
	 */
	public UserSettingService()
	{
		super(UserSettingEntity.class, IUserSettingRepository.class);
	}

	/**
	 * Fetch user setting.
	 *
	 * @param userId
	 *            the user id
	 * @return the user setting model
	 */
	public UserSettingModel fetchUserSetting(Long userId)
	{
		UserSettingEntity userSettingEntity = repository.fetchUserSetting(userId, "activeProjectId");

		return super.toModel(userSettingEntity, UserSettingModel.class);
	}
	
	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	} 
}
