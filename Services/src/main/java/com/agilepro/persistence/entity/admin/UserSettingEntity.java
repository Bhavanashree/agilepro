package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.agilepro.commons.models.customer.UserSettingModel;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class UserSettingEntity.
 * 
 * @author Pritam
 */
@Table(name = "USER_SETTING")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_NAME", fields = { "spaceIdentity", "userEntity", "projectEntity"}) })
public class UserSettingEntity extends WebutilsEntity
{
	/** 
	 * The user entity. 
	 **/
	@OneToOne
	@Column(name = "USER_ID", nullable = false)
	@PropertyMapping(type = UserSettingModel.class, from = "userId", subproperty = "id")
	private UserEntity userEntity;
	
	/** 
	 * The project entity. 
	 **/
	@OneToOne
	@Column(name = "ACTIVE_PROJECT_ID", nullable = false)
	@PropertyMapping(type = UserSettingModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity projectEntity;

	/**
	 * Gets the user entity.
	 *
	 * @return the user entity
	 */
	public UserEntity getUserEntity()
	{
		return userEntity;
	}

	/**
	 * Sets the user entity.
	 *
	 * @param userEntity the new user entity
	 */
	public void setUserEntity(UserEntity userEntity)
	{
		this.userEntity = userEntity;
	}

	/**
	 * Gets the project entity.
	 *
	 * @return the project entity
	 */
	public ProjectEntity getProjectEntity()
	{
		return projectEntity;
	}

	/**
	 * Sets the project entity.
	 *
	 * @param projectEntity the new project entity
	 */
	public void setProjectEntity(ProjectEntity projectEntity)
	{
		this.projectEntity = projectEntity;
	}
}
