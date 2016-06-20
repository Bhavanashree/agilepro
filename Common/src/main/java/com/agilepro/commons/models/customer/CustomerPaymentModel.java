package com.agilepro.commons.models.customer;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;

import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.FileInfo;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class CustomerPaymentModel.
 * 
 */
@Model
public class CustomerPaymentModel
{
	/**
	 * Id for the Payment table.
	 */
	@NonDisplayable
	private Long id;

	/**
	 * Customer Id.
	 */
	@LOV(name = "customerList")
	@Required
	private Long customerId;

	/**
	 * version.
	 */
	@NonDisplayable
	private Integer version;

	/**
	 * Date of Payment.
	 **/
	@Required
	private Date paymentDate;

	/**
	 * Amount paid as part of this transaction.
	 **/
	@Min(value = 1)
	@Required
	private Double amount;

	/**
	 * user description for payment.
	 **/
	@MultilineText
	@MaxLen(1000)
	private String paymentDescription;

	/**
	 * Payment related documents.
	 **/
	private List<FileInfo> attachments;

	/**
	 * Instantiates a new customer payment model.
	 */
	public CustomerPaymentModel()
	{}

	/**
	 * CustomerPayment.
	 * 
	 * @param id
	 *            value to Id
	 * @param customerId
	 *            value to customerId of the Payment
	 * @param paymentDate
	 *            value to date of the Payment
	 * @param amount
	 *            value to Amount of the Payment
	 */
	public CustomerPaymentModel(Long id, Long customerId, Date paymentDate, Double amount)
	{
		this.id = id;
		this.customerId = customerId;
		this.paymentDate = paymentDate;
		this.amount = amount;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.common.IExtendableModel#getId()
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id for the Payment table.
	 *
	 * @param id
	 *            the new id for the Payment table
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the customer.
	 *
	 * @return the customer
	 */
	public Long getCustomerId()
	{
		return customerId;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customer the new customer id
	 */
	public void setCustomerId(Long customer)
	{
		this.customerId = customer;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getPaymentDate()
	{
		return paymentDate;
	}

	/**
	 * Sets the payment date.
	 *
	 * @param paymentDate the new payment date
	 */
	public void setPaymentDate(Date paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount()
	{
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount
	 *            the new amount
	 */
	public void setAmount(Double amount)
	{
		this.amount = amount;
	}

	/**
	 * Gets the payment description.
	 *
	 * @return the payment description
	 */
	public String getPaymentDescription()
	{
		return paymentDescription;
	}

	/**
	 * Sets the payment description.
	 *
	 * @param paymentDescription
	 *            the new payment description
	 */
	public void setPaymentDescription(String paymentDescription)
	{
		this.paymentDescription = paymentDescription;
	}

	/**
	 * Gets the attachment.
	 *
	 * @return the attachment
	 */
	public List<FileInfo> getAttachments()
	{
		return attachments;
	}

	/**
	 * Sets the attachments.
	 *
	 * @param attachments the new attachments
	 */
	public void setAttachments(List<FileInfo> attachments)
	{
		this.attachments = attachments;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String comma = ",";
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append("[");

		builder.append(comma).append("CustomerId: ").append(customerId);
		builder.append(comma).append("Id: ").append(id);
		builder.append(comma).append("PaymentDate: ").append(paymentDate);
		builder.append(comma).append("Amount: ").append(amount);
		builder.append("Attachments: ").append(attachments);

		builder.append("]");
		return builder.toString();
	}
}
