package com.agilepro.commons;

import com.yukthi.webutils.common.models.BaseResponse;

/**
 * The Class BasicVersionAndIdResponse.
 */
public class BasicVersionAndIdResponse extends BaseResponse
{
	/**
	 * The version.
	 **/
	private Integer version = 1;

	/**
	 * ID of the saved entity.
	 */
	private long id;

	/**
	 * Instantiates a new basic version response.
	 *
	 * @param version the version
	 */
	public BasicVersionAndIdResponse(Integer version)
	{
		this.version = version;
	}

	/**
	 * Instantiates a new basic version and id response.
	 *
	 * @param version the version
	 * @param id the id
	 */
	public BasicVersionAndIdResponse(Integer version, long id)
	{
		this.version = version;
		this.id = id;
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
