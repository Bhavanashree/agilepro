package com.agilepro.commons.models.customer;

import java.util.Date;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.annotations.Label;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * Customer invoice search results.
 */
@Model
public class CustomerInvoiceDetailsSearchResult
{
	/**
	 * Invoice id.
	 */
	@NonDisplayable
	@Field(value = "id")
	private Long id;

	/**
	 * Name of corresponding customer.
	 */
	@Field(value = "customer.name")
	private String customerName;

	/**
	 * Name of corresponding price plan name.
	 */
	@Field(value = "pricePlan.name")
	private String pricePlanName;

	/**
	 * Invoice total amount.
	 */
	@Field(value = "totalAmount")
	private Double totalAmount;

	/**
	 * Invoice Date.
	 */
	@Label("Evaluation Date")
	@Field(value = "date")
	private Date date;

	/**
	 * Created on date.
	 */
	@Field("createdOn")
	private Date createdOn;

	/**
	 * Gets the invoice id.
	 *
	 * @return the invoice id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the invoice id.
	 *
	 * @param id
	 *            the new invoice id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the name of corresponding customer.
	 *
	 * @return the name of corresponding customer
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * Sets the name of corresponding customer.
	 *
	 * @param customerName
	 *            the new name of corresponding customer
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * Gets the name of corresponding price plan name.
	 *
	 * @return the name of corresponding price plan name
	 */
	public String getPricePlanName()
	{
		return pricePlanName;
	}

	/**
	 * Sets the name of corresponding price plan name.
	 *
	 * @param pricePlanName
	 *            the new name of corresponding price plan name
	 */
	public void setPricePlanName(String pricePlanName)
	{
		this.pricePlanName = pricePlanName;
	}

	/**
	 * Gets the invoice total amount.
	 *
	 * @return the invoice total amount
	 */
	public Double getTotalAmount()
	{
		return totalAmount;
	}

	/**
	 * Sets the invoice total amount.
	 *
	 * @param totalAmount
	 *            the new invoice total amount
	 */
	public void setTotalAmount(Double totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	/**
	 * Gets the invoice Date.
	 *
	 * @return the invoice Date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Sets the invoice Date.
	 *
	 * @param date
	 *            the new invoice Date
	 */
	public void setDate(Date date)
	{
		this.date = date;
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
}
