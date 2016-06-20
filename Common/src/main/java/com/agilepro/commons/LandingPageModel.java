package com.agilepro.commons;

/**
 * Represents configuration required by the landing pages (customized at
 * customer level).
 *
 * @author akiran
 */
public class LandingPageModel
{

	/**
	 * Name of the customer using the landing page.
	 */
	private String customerName;

	/**
	 * Customer id based on login url.
	 */
	private long customerId = -1;

	/**
	 * Gets the name of the customer using the landing page.
	 *
	 * @return the name of the customer using the landing page
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * Sets the name of the customer using the landing page.
	 *
	 * @param customerName
	 *            the new name of the customer using the landing page
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public long getCustomerId()
	{
		return customerId;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customerId
	 *            the new customer id
	 */
	public void setCustomerId(long customerId)
	{
		this.customerId = customerId;
	}
}
