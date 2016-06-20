package com.agilepro.commons.models.customer;
/*
import java.util.Date;
import java.util.List;*/

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.validation.annotations.GreaterThanEquals;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class PaymentSearchQuery.
 */

@Model
public class PaymentSearchQuery
{

	/**
	 * To search the From Date.
	 * 
	 **/
	@Condition(value = "paymentDate", op = Operator.GE)
	private Date minDate;

	/**
	 * To search the To Date.
	 **/
	@GreaterThanEquals(field = "minDate")
	@Condition(value = "paymentDate", op = Operator.LE)
	private Date maxDate;

	/**
	 * To Search the minimum Amount paid Customer.
	 **/
	
	@Condition(value = "amount", op = Operator.GE)
	@Min(50)
	private Long minAmount;

	/**
	 * The max amount. To Search the maximum Amount paid Customer.
	 **/
	@Condition(value = "amount", op = Operator.LE)
	@Max(100000)
	private Long maxAmount;

	/**
	 * To Search Maximum Due Amount kept by Customer.
	 **/
	@Condition(value = "amount", op = Operator.GE)
	private Long maxDueAmount;

	/** 
	 * The customer id.
	 **/
	@LOV(name = "customerList")
	@Condition(value = "customer.id", op = Operator.EQ)
	private Long customerId;
	
	/**
	 * Instantiates a new payment entity.
	 */
	public PaymentSearchQuery()
	{
	}

	/**
	 * Instantiates a new payment entity.
	 *
	 * @param minDate
	 *            the minDate
	 * @param maxDate
	 *            the maxDate
	 * @param minAmount
	 *            the minAmount
	 * @param maxAmount
	 *            the maxAmount
	 * @param maxDueAmount
	 *            the maxDueAmount
	 * @param customerId
	 * 				customerId
	 */
	public PaymentSearchQuery(Date minDate, Date maxDate, Long minAmount, Long maxAmount, Long maxDueAmount, Long customerId)
	{
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.maxDueAmount = maxDueAmount;
		this.customerId = customerId;
	}

	/**
	 * Gets the search the From Date.
	 *
	 * @return the search the From Date
	 */
	public Date getMinDate()
	{
		return minDate;
	}

	/**
	 * Sets the search the From Date.
	 *
	 * @param minDate
	 *            the new search the From Date
	 */
	public void setMinDate(Date minDate)
	{
		this.minDate = minDate;
	}

	/**
	 * Gets search To Date.
	 *
	 * @return search To Date
	 */
	public Date getMaxDate()
	{
		return maxDate;
	}

	/**
	 * Sets the to search the To Date.
	 *
	 * @param maxDate
	 *            the new to search the To Date
	 */
	public void setMaxDate(Date maxDate)
	{
		this.maxDate = maxDate;
	}

	/**
	 * Gets the Search the minimum Amount paid Customer.
	 *
	 * @return the Search the minimum Amount paid Customer
	 */
	public Long getMinAmount()
	{
		return minAmount;
	}

	/**
	 * Sets the Search the minimum Amount paid Customer.
	 *
	 * @param minAmount
	 *            the new Search the minimum Amount paid Customer
	 */
	public void setMinAmount(Long minAmount)
	{
		this.minAmount = minAmount;
	}

	/**
	 * Gets to Search maximum Amount paid Customer.
	 *
	 * @return the max amount
	 */
	public Long getMaxAmount()
	{
		return maxAmount;
	}

	/**
	 * Sets the max amount. To Search the maximum Amount paid Customer.
	 *
	 * @param maxAmount
	 *            the new max amount
	 */
	public void setMaxAmount(Long maxAmount)
	{
		this.maxAmount = maxAmount;
	}

	/**
	 * Gets the To Search Maximum Due Amount kept Customer.
	 *
	 * @return the To Search Maximum Due Amount kept Customer
	 */
	public Long getMaxDueAmount()
	{
		return maxDueAmount;
	}

	/**
	 * Sets the To Search Maximum Due Amount kept Customer.
	 *
	 * @param maxDueAmount
	 *            the new To Search Maximum Due Amount kept Customer
	 */
	public void setMaxDueAmount(Long maxDueAmount)
	{
		this.maxDueAmount = maxDueAmount;
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
}
