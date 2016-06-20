package com.agilepro.persistence.entity.admin;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.customer.priceplan.ExpressionValueDetails;
import com.agilepro.commons.models.customer.priceplan.VariableValueDetails;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.DeleteWithParent;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class CustomerInvoiceDetails. After Evaluation of expressions of customer
 * price plan Customer invoice details get saved.
 */

@Table(name = "CUSTOMER_INVOICE_DETAILS")
public class CustomerInvoiceDetailsEntity extends WebutilsEntity
{

	/**
	 * Customer id.
	 */
	@DeleteWithParent
	@ManyToOne
	@Column(name = "CUSTOMER_ID")
	private CustomerEntity customer;

	/**
	 * Customer Price Plan Id.
	 */
	@DeleteWithParent
	@ManyToOne
	@Column(name = "PRICE_PLAN_ID")
	private CustomerPricePlanEntity pricePlan;

	/**
	 * The price plan variables, map key as variable name & value as variable
	 * value.
	 */
	@Column(name = "VARIABLE_VALUES", length = 2000)
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private List<VariableValueDetails> variableValues;

	/**
	 * The price plan expressions, map key as expression name & value as
	 * expression value.
	 */
	@Column(name = "EXPRESSION_VALUES", length = 2000)
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private List<ExpressionValueDetails> expressionValues;

	/**
	 * total amount paid by customer.
	 */
	@Column(name = "TOTAL_AMOUNT")
	private Double totalAmount;

	/**
	 * The invoice date.
	 */
	@Column(name = "INVOICE_DATE")
	private Date date;

	/**
	 * Instantiates a new customer invoice details entity.
	 */
	public CustomerInvoiceDetailsEntity()
	{}

	/**
	 * Instantiates a new customer invoice details entity.
	 *
	 * @param customerId
	 *            the customer id
	 * @param pricePlanId
	 *            the price plan id
	 * @param totalAmount
	 *            the total amount
	 * @param date
	 *            the date
	 * @param createdOn
	 *            the created on
	 */
	public CustomerInvoiceDetailsEntity(Long customerId, Long pricePlanId, Double totalAmount, Date date, Date createdOn)
	{
		this.customer = new CustomerEntity();
		this.pricePlan = new CustomerPricePlanEntity();
		this.totalAmount = totalAmount;
		this.date = date;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public CustomerEntity getCustomer()
	{
		return customer;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customer
	 *            the new customer id
	 */
	public void setCustomer(CustomerEntity customer)
	{
		this.customer = customer;
	}

	/**
	 * Gets the customer Price Plan Id.
	 *
	 * @return the customer Price Plan Id
	 */
	public CustomerPricePlanEntity getPricePlan()
	{
		return pricePlan;
	}

	/**
	 * Sets the customer Price Plan Id.
	 *
	 * @param pricePlan
	 *            the new customer Price Plan Id
	 */
	public void setPricePlan(CustomerPricePlanEntity pricePlan)
	{
		this.pricePlan = pricePlan;
	}

	/**
	 * Gets the price plan variables, map key as variable name & value as
	 * variable value.
	 *
	 * @return the price plan variables, map key as variable name & value as
	 *         variable value
	 */
	public List<VariableValueDetails> getVariableValues()
	{
		return variableValues;
	}

	/**
	 * Sets the price plan variables, map key as variable name & value as
	 * variable value.
	 *
	 * @param variableValues
	 *            the new price plan variables, map key as variable name & value
	 *            as variable value
	 */
	public void setVariableValues(List<VariableValueDetails> variableValues)
	{
		this.variableValues = variableValues;
	}

	/**
	 * Gets the price plan expressions, map key as expression name & value as
	 * expression value.
	 *
	 * @return the price plan expressions, map key as expression name & value as
	 *         expression value
	 */
	public List<ExpressionValueDetails> getExpressionValues()
	{
		return expressionValues;
	}

	/**
	 * Sets the price plan expressions, map key as expression name & value as
	 * expression value.
	 *
	 * @param expressionValues
	 *            the new price plan expressions, map key as expression name &
	 *            value as expression value
	 */
	public void setExpressionValues(List<ExpressionValueDetails> expressionValues)
	{
		this.expressionValues = expressionValues;
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
	 * Gets the The invoice date.
	 *
	 * @return the The invoice date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Sets the The invoice date.
	 *
	 * @param date
	 *            the new The invoice date
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}
}