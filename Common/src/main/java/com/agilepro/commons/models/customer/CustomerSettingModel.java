package com.agilepro.commons.models.customer;

import java.util.List;

import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class CustomerSettingModel.
 */
@Model(name = "CustomerSetting")
public class CustomerSettingModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The user id.
	 **/
	private Long customerId;

	/**
	 * The key.
	 **/
	
	private String key;

	/**
	 * The value.
	 **/
	@IgnoreField
	private Object value;

	/**
	 *  The list of values.
	 **/
	@IgnoreField
	private List<String> listOfValues;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

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
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public Long getCustomerId()
	{
		return customerId;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customerId
	 *            the new customer id
	 */
	public void setCustomerId(Long customerId)
	{
		this.customerId = customerId;
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

	/**
	 * Gets the list of values.
	 *
	 * @return the list of values
	 */
	public List<String> getListOfValues()
	{
		return listOfValues;
	}

	/**
	 * Sets the list of values.
	 *
	 * @param listOfValues
	 *            the new list of values
	 */
	public void setListOfValues(List<String> listOfValues)
	{
		this.listOfValues = listOfValues;
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
	public Object getValue()
	{
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}
}
