package com.agilepro.commons.models.customer;

import java.util.Date;
import java.util.Map;

import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class CustomerInvoiceDetailsModel.
 */
@Model
public class CustomerInvoiceDetailsModel
{
	/**
	 * Customer Invoice detail id.
	 */
	@NonDisplayable
	private Long id;

	/**
	 * Customer id.
	 */

	@LOV(name = "customerList")
	private Long customerId;

	/**
	 * Customer Price Plan Id.
	 */
	@NonDisplayable
	private Long pricePlanId;
	/**
	 * The price plan variables, map key as variable name & value as variable
	 * value.
	 */
	@IgnoreField
	private Map<String, Double> variableValueMap;
	/**
	 * The price plan expressions, map key as expression name & value as
	 * expression value.
	 */
	@IgnoreField
	private Map<String, Double> expressionValueMap;
	/**
	 * total amount paid by customer.
	 */
	private Double totalAmount;
	/**
	 * The invoice date.
	 */
	private Date date;

	/**
	 * Instantiates a new customer invoice details model.
	 */
	public CustomerInvoiceDetailsModel()
	{}

	/**
	 * Instantiates a new customer invoice details model.
	 *
	 * @param id
	 *            the id
	 * @param customerId
	 *            the customer id
	 * @param pricePlanId
	 *            the price plan id
	 * @param variableValueMap
	 *            the variable value map
	 * @param expressionValueMap
	 *            the expression value map
	 * @param totalAmount
	 *            the total amount
	 * @param date
	 *            the date
	 */
	public CustomerInvoiceDetailsModel(Long id, Long customerId, Long pricePlanId, Map<String, Double> variableValueMap, Map<String, Double> expressionValueMap, Double totalAmount, Date date)
	{
		this.id = id;
		this.customerId = customerId;
		this.pricePlanId = pricePlanId;
		this.variableValueMap = variableValueMap;
		this.expressionValueMap = expressionValueMap;
		this.totalAmount = totalAmount;
		this.date = date;
	}

	/**
	 * Gets the customer Invoice detail id.
	 *
	 * @return the customer Invoice detail id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the customer Invoice detail id.
	 *
	 * @param id
	 *            the new customer Invoice detail id
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
	 * Gets the customer Price Plan Id.
	 *
	 * @return the customer Price Plan Id
	 */
	public Long getPricePlanId()
	{
		return pricePlanId;
	}

	/**
	 * Sets the customer Price Plan Id.
	 *
	 * @param pricePlanId
	 *            the new customer Price Plan Id
	 */
	public void setPricePlanId(Long pricePlanId)
	{
		this.pricePlanId = pricePlanId;
	}

	/**
	 * Gets the price plan variables, map key as variable name & value as
	 * variable value.
	 *
	 * @return the price plan variables, map key as variable name & value as
	 *         variable value
	 */
	public Map<String, Double> getVariableValueMap()
	{
		return variableValueMap;
	}

	/**
	 * Sets the price plan variables, map key as variable name & value as
	 * variable value.
	 *
	 * @param variableValueMap
	 *            the new price plan variables, map key as variable name & value
	 *            as variable value
	 */
	public void setVariableValueMap(Map<String, Double> variableValueMap)
	{
		this.variableValueMap = variableValueMap;
	}

	/**
	 * Gets the price plan expressions, map key as expression name & value as
	 * expression value.
	 *
	 * @return the price plan expressions, map key as expression name & value as
	 *         expression value
	 */
	public Map<String, Double> getExpressionValueMap()
	{
		return expressionValueMap;
	}

	/**
	 * Sets the price plan expressions, map key as expression name & value as
	 * expression value.
	 *
	 * @param expressionValueMap
	 *            the new price plan expressions, map key as expression name &
	 *            value as expression value
	 */
	public void setExpressionValueMap(Map<String, Double> expressionValueMap)
	{
		this.expressionValueMap = expressionValueMap;
	}

	/**
	 * Gets the total amount paid by customer.
	 *
	 * @return the total amount paid by customer
	 */
	public Double getTotalAmount()
	{
		return totalAmount;
	}

	/**
	 * Sets the total amount paid by customer.
	 *
	 * @param totalAmount
	 *            the new total amount paid by customer
	 */
	public void setTotalAmount(Double totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	/**
	 * Gets the invoice date.
	 *
	 * @return the invoice date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Sets the invoice date.
	 *
	 * @param date
	 *            the new invoice date
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}
}
