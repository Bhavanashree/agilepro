package com.agilepro.commons.models.customer.priceplan;

import java.util.Date;
import java.util.List;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.annotations.IgnoreField;

/**
 * Details about the invoice.
 */
public class InvoiceDetails
{
	/**
	 * Name of the customer.
	 */
	@Field(value = "customer.name")
	private String customerName;

	/**
	 * Price plan name.
	 */
	@Field(value = "pricePlan.name")
	private String pricePlanName;

	/**
	 * Invoice date.
	 */
	@Field(value = "date")
	private Date invoiceDate;

	/**
	 * Variable value details.
	 */
	@Field(value = "variableValues")
	@IgnoreField
	private List<VariableValueDetails> variableValues;

	/**
	 * Expression value details.
	 */
	@Field(value = "expressionValues")
	@IgnoreField
	private List<ExpressionValueDetails> expressionValues;

	/**
	 * Total invoice amount.
	 */
	@Field(value = "totalAmount")
	private Double totalAmount;

	/**
	 * Instantiates a new invoice details.
	 */
	public InvoiceDetails()
	{}

	/**
	 * Gets the name of the customer.
	 *
	 * @return the name of the customer
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * Sets the name of the customer.
	 *
	 * @param customerName
	 *            the new name of the customer
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * Gets the price plan name.
	 *
	 * @return the price plan name
	 */
	public String getPricePlanName()
	{
		return pricePlanName;
	}

	/**
	 * Sets the price plan name.
	 *
	 * @param pricePlanName
	 *            the new price plan name
	 */
	public void setPricePlanName(String pricePlanName)
	{
		this.pricePlanName = pricePlanName;
	}

	/**
	 * Gets the invoice date.
	 *
	 * @return the invoice date
	 */
	public Date getInvoiceDate()
	{
		return invoiceDate;
	}

	/**
	 * Sets the invoice date.
	 *
	 * @param invoiceDate
	 *            the new invoice date
	 */
	public void setInvoiceDate(Date invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}

	/**
	 * Gets the variable value details.
	 *
	 * @return the variable value details
	 */
	public List<VariableValueDetails> getVariableValues()
	{
		return variableValues;
	}

	/**
	 * Sets the variable value details.
	 *
	 * @param variableValues
	 *            the new variable value details
	 */
	public void setVariableValues(List<VariableValueDetails> variableValues)
	{
		this.variableValues = variableValues;
	}

	/**
	 * Gets the expression value details.
	 *
	 * @return the expression value details
	 */
	public List<ExpressionValueDetails> getExpressionValues()
	{
		return expressionValues;
	}

	/**
	 * Sets the expression value details.
	 *
	 * @param expressionValues
	 *            the new expression value details
	 */
	public void setExpressionValues(List<ExpressionValueDetails> expressionValues)
	{
		this.expressionValues = expressionValues;
	}

	/**
	 * Gets the total invoice amount.
	 *
	 * @return the total invoice amount
	 */
	public Double getTotalAmount()
	{
		return totalAmount;
	}

	/**
	 * Sets the total invoice amount.
	 *
	 * @param totalAmount
	 *            the new total invoice amount
	 */
	public void setTotalAmount(Double totalAmount)
	{
		this.totalAmount = totalAmount;
	}
}