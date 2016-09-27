package com.agilepro.commons.models.customer;

import java.util.Date;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

@Model
public class ReleaseSearchResult
{
	/**
	 * Id.
	 */
	@NonDisplayable
	@Field(value = "id")
	private long id;

	/**
	 * Name of the project.
	 */
	@Field(value = "name")
	private String name;

	/**
	 * The start date.
	 **/
	@Field(value = "startDate")
	private Date startDate;

	/**
	 * The end date.
	 **/
	@Field(value = "endDate")
	private Date endDate;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
}
