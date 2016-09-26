package com.agilepro.controller;

/**
 * Common constants of cloud biller.
 * 
 * @author akiran
 */
public interface IAgileProConstants
{
	/**
	 * Configuration key specifying expected url pattern of the cbiller.
	 */
	public String CONFIG_URL_PATTERN = "cbiller.url.pattern";
	
	/**
	 * Admin user space.
	 */
	public String ADMIN_USER_SPACE = "ADMIN";
	
	/**
	 * Customer user space prefix.
	 */
	public String CUSTOMER_USER_SPACE_PREFIX = "CUSTOMER-";
	
	/**
	 * Fetches customer user space for specified customer id.
	 * @param customerId Customer id for which space needs to be computed.
	 * @return Customer space identity
	 */
	public static String customerSpace(long customerId)
	{
		return CUSTOMER_USER_SPACE_PREFIX + customerId;
	}
}
