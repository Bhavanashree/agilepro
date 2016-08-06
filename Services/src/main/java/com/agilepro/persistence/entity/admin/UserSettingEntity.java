package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
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
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_KEY", fields = { "spaceIdentity", "userEntity", "key" }) })
public class UserSettingEntity extends WebutilsEntity
{
	/**
	 * The user entity.
	 **/
	@ManyToOne
	@Column(name = "USER_ID", nullable = false)
	@PropertyMapping(type = UserSettingModel.class, from = "userId", subproperty = "id")
	private UserEntity userEntity;

	/**
	 * The key.
	 **/
	@Column(name = "SET_KEY", length = 50, nullable = false)
	@UniqueConstraint(name = "key", message = "Please provide a different key, provided key is already present")
	private String key;

	/**
	 * The value.
	 **/
	@Column(name = "VALUE", length = 2000, nullable = false)
	private String value;

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
	 * @param userEntity
	 *            the new user entity
	 */
	public void setUserEntity(UserEntity userEntity)
	{
		this.userEntity = userEntity;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * Sets the key.
	 *
	 * @param key
	 *            the new key
	 */
	public void setKey(String key)
	{
		this.key = key;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
}
