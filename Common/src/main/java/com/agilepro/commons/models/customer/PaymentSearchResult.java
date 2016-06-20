package com.agilepro.commons.models.customer;

import java.util.Date;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class PaymentSearchResults.
 */
@Model
public class PaymentSearchResult
{
	/**
	 * Id of the Payment.
	 */
	@NonDisplayable
	@Field(value = "id")
	private Long id;
	/**
	 * name of the Customer.
	 */

	@Field(value = "customer.name")
	private String customerName;

	/**
	 * Date of the Payment.
	 */
	@Field(value = "paymentDate")
	private Date paymentDate;

	/**
	 * Amount paid as part of this transaction.
	 **/
	@Field(value = "amount")
	private Long amount;

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
	 * Gets the id of the Payment.
	 *
	 * @return the id of the Payment
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id of the Payment.
	 *
	 * @param id
	 *            the new id of the Payment
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the name of the Customer.
	 *
	 * @return the name of the Customer
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * Sets the name of the Customer.
	 *
	 * @param customerName
	 *            the new name of the Customer
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * Gets the date of the Payment.
	 *
	 * @return the date of the Payment
	 */
	public Date getPaymentDate()
	{
		return paymentDate;
	}

	/**
	 * Sets the date of the Payment.
	 *
	 * @param paymentDate
	 *            the new date of the Payment
	 */
	public void setPaymentDate(Date paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	/**
	 * Gets the amount paid as part of this transaction.
	 *
	 * @return the amount paid as part of this transaction
	 */
	public Long getAmount()
	{
		return amount;
	}

	/**
	 * Sets the amount paid as part of this transaction.
	 *
	 * @param amount
	 *            the new amount paid as part of this transaction
	 */
	public void setAmount(Long amount)
	{
		this.amount = amount;
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
