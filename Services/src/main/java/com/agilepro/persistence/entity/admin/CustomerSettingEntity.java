package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.customer.CustomerSettingModel;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * The Class CustomerSettingEntity.
 */
@Table(name = "CUSTOMER_SETTING")
@UniqueConstraints({ @UniqueConstraint(name = "CUSTOMER_ID_KEY", fields = { "customerEntity", "key" }) })
public class CustomerSettingEntity extends WebutilsExtendableEntity
{

	/**
	 * The customer entity.
	 **/
	@ManyToOne
	@Column(name = "CUSTOMER_ID", nullable = false)
	@PropertyMapping(type = CustomerSettingModel.class, from = "customerId", subproperty = "id")
	private CustomerEntity customerEntity;

	/**
	 * The key.
	 **/
	@Column(name = "SET_KEY", length = 50, nullable = false)
	private String key;

	/**
	 * The value.
	 **/
	@Column(name = "VALUE", nullable = false)
	@DataTypeMapping(type = DataType.CLOB, converterType = JsonConverter.class)
	private Object value;

	/**
	 * Gets the customer entity.
	 *
	 * @return the customer entity
	 */
	public CustomerEntity getCustomerEntity()
	{
		return customerEntity;
	}

	/**
	 * Sets the customer entity.
	 *
	 * @param customerEntity
	 *            the new customer entity
	 */
	public void setCustomerEntity(CustomerEntity customerEntity)
	{
		this.customerEntity = customerEntity;
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

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}
}
