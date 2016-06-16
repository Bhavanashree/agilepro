package com.agilepro.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yukthi.webutils.security.UserDetails;

/**
 * User details that will be maintained across the session in the encrypted format.
 * @author akiran
 */
public class CbillerUserDetails extends UserDetails
{
	/**
	 * Customer id under which current user is registered. For admin user
	 * this value will be zero
	 */
	private long customerId;
	
	/**
	 * Instantiates a new cbiller user details.
	 */
	public CbillerUserDetails()
	{}

	/**
	 * Instantiates a new cbiller user details.
	 *
	 * @param customerId the customer id
	 */
	public CbillerUserDetails(long userId, long customerId)
	{
		super.setUserId(userId);
		this.customerId = customerId;
	}

	/**
	 * Gets the customer id under which current user is registered. For admin user this value will be zero.
	 *
	 * @return the customer id under which current user is registered
	 */
	@JsonProperty("cid")
	public long getCustomerId()
	{
		return customerId;
	}

	/**
	 * Sets the customer id under which current user is registered. For admin user this value will be zero.
	 *
	 * @param customerId the new customer id under which current user is registered
	 */
	public void setCustomerId(long customerId)
	{
		this.customerId = customerId;
	}
}
