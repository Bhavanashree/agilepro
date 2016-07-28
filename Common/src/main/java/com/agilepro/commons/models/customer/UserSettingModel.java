package com.agilepro.commons.models.customer;

import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class UserSettingModel.
 * 
 * @author Pritam
 */
@Model(name = "UserSetting")
public class UserSettingModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The user id.
	 **/
	@Required
	private Long userId;

	/**
	 * The key.
	 **/
	@Required
	private String key;

	/**
	 * The value.
	 **/
	@Required
	@MaxLen(2000)
	private String value;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * Instantiates a new user setting model.
	 */
	public UserSettingModel()
	{}

	/**
	 * Instantiates a new user setting model.
	 *
	 * @param userId
	 *            the user id
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public UserSettingModel(Long userId, String key, String value)
	{
		super();
		this.userId = userId;
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId
	 *            the new user id
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
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

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}
}
