package com.agilepro.persistence.entity.notification;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.customer.CustomerPaymentModel;
import com.agilepro.commons.models.notification.MailTemplateDefinitionModel;
import com.agilepro.commons.models.notification.MailTemplateModel;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.WebutilsEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * The Class MailTemplateDefinition.
 */
@Table(name = "MAIL_TEMPLATE_DEFINITION")
public class MailTemplateDefinitionEntity extends WebutilsEntity
{
	
	/**
	 * The name.
	 **/
	@Column(name = "NAME")
	private String name;

	/**
	 * The description.
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The context attribute.
	 **/
	@Column(name = "CONTEXT_ATTRIBUTE")
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private Map<String, String> contextAttribute;
	
	/**
	 * customer id for which payment is received.
	 **/
	@ManyToOne
	@PropertyMapping(type = CustomerPaymentModel.class, from = "customerId", subproperty = "id")
	@Column(name = "CUSTOMER_ID")
	private CustomerEntity customer;
	
	public MailTemplateDefinitionEntity()
	{

	}
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Gets the context attribute.
	 *
	 * @return the context attribute
	 */
	public Map<String, String> getContextAttribute()
	{
		return contextAttribute;
	}
	public CustomerEntity getCustomer()
	{
		return customer;
	}
	public void setCustomer(CustomerEntity customer)
	{
		this.customer = customer;
	}
	public void setContextAttribute(Map<String, String> contextAttribute)
	{
		this.contextAttribute = contextAttribute;
	}
}
