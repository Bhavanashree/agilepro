package com.agilepro.commons.models.customer;

import com.yukthi.webutils.common.models.BaseResponse;

/**
 * The Class CustomerSaveResponse.
 * 
 * @author Pritam
 */
public class CustomerSaveResponse extends BaseResponse
{
	
	/**
	 * Id.
	 */
	private long id;
	
	/**
	 * Instantiates a new customer save response.
	 */
	public CustomerSaveResponse()
	{}
	
	/**
	 * Instantiates a new customer save response.
	 *
	 * @param id the id
	 */
	public CustomerSaveResponse(long id)
	{
		this.id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id)
	{
		this.id = id;
	}
}
