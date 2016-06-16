package com.agilepro.commons.models.customer.priceplan;

import java.util.Date;
import java.util.List;

import com.agilepro.commons.PaymentCycle;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class CustomerPricePlanSearchResult.
 */
@Model
public class CustomerPricePlanSearchResult
{

	/**
	 * The customer price plan id.
	 */
	@NonDisplayable
	@Field(value = "id")
	private long id;

	/**
	 * The customer price plan name.
	 */
	@Field(value = "name")
	private String name;

	/**
	 * The price plan description.
	 */
	@Field(value = "description")
	private String description;

	/**
	 * The list of price plan numeric variables.
	 */
	@Field(value = "numericVariables")
	@IgnoreField
	private List<CustomerPricePlanVariable> numericVariables;

	/**
	 * The list of price plan expressions.
	 */
	@Field(value = "expressions")
	@IgnoreField
	private List<CustomerPricePlanExpression> expressions;

	/**
	 * The customer price plan payment cycle.
	 */
	@Field(value = "paymentCycle")
	@IgnoreField
	private PaymentCycle paymentCycle;

	/**
	 * Created by user.
	 */
	@Field("createdBy.displayName")
	private String createdBy;

	/**
	 * Created on date.
	 */
	@Field("createdOn")
	private Date createdOn;

	/**
	 * Updated by user.
	 */
	@Field("updatedBy.displayName")
	private String updatedBy;

	/**
	 * Update on date.
	 */
	@Field("updatedOn")
	private Date updatedOn;

	/**
	 * Gets the The customer price plan id.
	 *
	 * @return the The customer price plan id
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Sets the The customer price plan id.
	 *
	 * @param id
	 *            the new The customer price plan id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * Gets the customer price plan name.
	 *
	 * @return the customer price plan name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the customer price plan name.
	 *
	 * @param name
	 *            the new customer price plan name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the price plan description.
	 *
	 * @return the price plan description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the price plan description.
	 *
	 * @param description
	 *            the new price plan description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Gets the The list of price plan numeric variables.
	 *
	 * @return the The list of price plan numeric variables
	 */
	public List<CustomerPricePlanVariable> getNumericVariables()
	{
		return numericVariables;
	}

	/**
	 * Sets the The list of price plan numeric variables.
	 *
	 * @param variables
	 *            the new The list of price plan numeric variables
	 */
	public void setNumericVariables(List<CustomerPricePlanVariable> variables)
	{
		this.numericVariables = variables;
	}

	/**
	 * Gets the The list of price plan expressions.
	 *
	 * @return the The list of price plan expressions
	 */
	public List<CustomerPricePlanExpression> getExpressions()
	{
		return expressions;
	}

	/**
	 * Sets the The list of price plan expressions.
	 *
	 * @param expressions
	 *            the new The list of price plan expressions
	 */
	public void setExpressions(List<CustomerPricePlanExpression> expressions)
	{
		this.expressions = expressions;
	}

	/**
	 * Gets the The customer price plan payment cycle.
	 *
	 * @return the The customer price plan payment cycle
	 */
	public PaymentCycle getPaymentCycle()
	{
		return paymentCycle;
	}

	/**
	 * Sets the The customer price plan payment cycle.
	 *
	 * @param paymentCycle
	 *            the new The customer price plan payment cycle
	 */
	public void setPaymentCycle(PaymentCycle paymentCycle)
	{
		this.paymentCycle = paymentCycle;
	}

	/**
	 * Gets the created by user.
	 *
	 * @return the created by user
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}

	/**
	 * Sets the created by user.
	 *
	 * @param createdBy
	 *            the new created by user
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created on date.
	 *
	 * @return the created on date
	 */
	public Date getCreatedOn()
	{
		return createdOn;
	}

	/**
	 * Sets the created on date.
	 *
	 * @param createdOn
	 *            the new created on date
	 */
	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}

	/**
	 * Gets the updated by user.
	 *
	 * @return the updated by user
	 */
	public String getUpdatedBy()
	{
		return updatedBy;
	}

	/**
	 * Sets the updated by user.
	 *
	 * @param updatedBy
	 *            the new updated by user
	 */
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the update on date.
	 *
	 * @return the update on date
	 */
	public Date getUpdatedOn()
	{
		return updatedOn;
	}

	/**
	 * Sets the update on date.
	 *
	 * @param updatedOn
	 *            the new update on date
	 */
	public void setUpdatedOn(Date updatedOn)
	{
		this.updatedOn = updatedOn;
	}
}
