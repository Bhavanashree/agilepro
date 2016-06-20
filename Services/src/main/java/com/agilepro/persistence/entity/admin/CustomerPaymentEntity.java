package com.agilepro.persistence.entity.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.customer.CustomerPaymentModel;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * CustomerPaymentEntity contains Payment details.
 *
 * @author Bhavana
 */

@Table(name = "CUSTOMER_PAYMENT")
public class CustomerPaymentEntity extends WebutilsEntity
{
	/**
	 * customer id for which payment is received.
	 **/
	@ManyToOne
	@PropertyMapping(type = CustomerPaymentModel.class, from = "customerId", subproperty = "id")
	@Column(name = "CUSTOMER_ID", nullable = false)
	private CustomerEntity customer;

	/**
	 * Date of Payment.
	 **/
	@Column(name = "PAYMENT_DATE", nullable = false)
	private Date paymentDate;

	/**
	 * Amount paid as part of this transaction.
	 **/
	@Column(name = "AMOUNT", nullable = false)
	private Double amount;

	/**
	 * user description for payment.
	 **/
	@Column(name = "PAYMENT_DESCRIPTION", length = 1000)
	private String paymentDescription;

	/**
	 * Instantiates a new customer payment entity.
	 */
	public CustomerPaymentEntity()
	{}

	/**
	 * Gets the customer id for which payment is received.
	 *
	 * @return the customer id for which payment is received
	 */
	public CustomerEntity getCustomer()
	{
		return customer;
	}

	/**
	 * Sets the customer id for which payment is received.
	 *
	 * @param customer
	 *            the new customer id for which payment is received
	 */
	public void setCustomer(CustomerEntity customer)
	{
		this.customer = customer;
	}

	/**
	 * Gets the Date of Payment.
	 *
	 * @return the Date of Payment
	 */
	public Date getPaymentDate()
	{
		return paymentDate;
	}

	/**
	 * Sets the Date of Payment.
	 *
	 * @param paymentDate
	 *            the new Date of Payment
	 */
	public void setPaymentDate(Date paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	/**
	 * CustomerEntity Gets the Amount paid as part of this transaction.
	 *
	 * @return the Amount paid as part of this transaction
	 */

	public Double getAmount()
	{
		return amount;
	}

	/**
	 * Sets the Amount paid as part of customer payment.
	 * 
	 * @param amount
	 *            new amount public void setAmount(Double amount)aid as part of
	 *            this transaction
	 */
	public void setAmount(Double amount)
	{
		this.amount = amount;
	}

	/**
	 * Gets the user description for payment.
	 *
	 * @return the user descrsetCustomeiption for payment
	 */
	public String getPaymentDescription()
	{
		return paymentDescription;
	}

	/**
	 * Sets the user description for payment.
	 *
	 * @param paymentDescription
	 *            the new user description for payment
	 */
	public void setPaymentDescription(String paymentDescription)
	{
		this.paymentDescription = paymentDescription;
	}
}
