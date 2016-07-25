package com.agilepro.services.admin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.UserSettingModel;
import com.agilepro.persistence.entity.admin.UserSettingEntity;
import com.agilepro.persistence.repository.admin.IUserSettingRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
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
	 * The repository factory.
	 **/
	@Autowired
	private RepositoryFactory repositoryFactory;

	/** 
	 * The iuser setting repository.
	 * */
	private IUserSettingRepository iuserSettingRepository;
	
	/**
	 * Instantiates a new user setting service.
	 */
	public UserSettingService()
	{
		super(UserSettingEntity.class, IUserSettingRepository.class);
	}

	/**
	 * Initialize the iuserSettingRepository.
	 */
	@PostConstruct
	private void init()
	{
		iuserSettingRepository = repositoryFactory.getRepository(IUserSettingRepository.class);
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
		UserSettingEntity userSettingEntity = iuserSettingRepository.fetchUserSetting(userId);

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
