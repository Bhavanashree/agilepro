package com.agilepro.commons.models.customer;

import com.yukthi.webutils.common.models.BaseResponse;

/**
 * The Class PaymentSaveResponse.
 */
public class CustomerPaymentResponse extends BaseResponse
{

	/** 
	 * The id. 
	 **/
	private Long id;
	
	/**
	 * Customer model.
	 */
	private CustomerPaymentModel model;

	/**
	 * Instantiates a new customer save response.
	 */
	public CustomerPaymentResponse()
	{}

	/**
	 * Instantiates a new customerPayment save response.
	 *
	 * @param customerpayment
	 *            the customerPayment id
	 */

	public CustomerPaymentResponse(Long id)
	{
		this.id = id;
	}

	/**
	 * Instantiates a new customer save response.
	 *
	 * @param customerModel
	 *            the customer model
	 */
	public CustomerPaymentResponse(CustomerPaymentModel model)
	{
		this.model = model;
	}

	/**
	 * Gets the customerPayment model.
	 *
	 * @return the customer model
	 */
	public CustomerPaymentModel getmodel()
	{
		return model;
	}

	/**
	 * Sets the customerPayment model.
	 *
	 * @param customerModel
	 *            the new customer model
	 */

	public void setmodel(CustomerPaymentModel model)
	{
		this.model = model;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
}
