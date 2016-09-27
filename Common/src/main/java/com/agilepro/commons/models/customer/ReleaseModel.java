package com.agilepro.commons.models.customer;

import java.util.Date;

import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;

@Model(name = "Release")
public class ReleaseModel 
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	@Required
	@MinLen(5)
	@MaxLen(20)
	private String name;
	
	/**
	 * The start date.
	 **/
	private Date startDate;

	/**
	 * The end date.
	 **/
	private Date endDate;

	/**
	 * The description.
	 **/
	@MultilineText
	private String description;
	
	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}
}
