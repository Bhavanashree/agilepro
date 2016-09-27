package com.agilepro.commons.models.customer;

import java.util.Date;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.Model;

@Model
public class ReleaseSearchQuery
{
	/**
	 * The name.
	 **/
	@Condition(value = "name", op = Operator.LIKE, ignoreCase = true)
	private String name;
	
	/**
	 * The start date.
	 **/
	@Condition(value = "startDate", op = Operator.LIKE)
	private Date startDate;

	/**
	 * The end date.
	 **/
	@Condition(value = "endDate", op = Operator.LIKE)
	private Date endDate;

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
