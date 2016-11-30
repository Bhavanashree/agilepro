package com.agilepro.commons.models.sprint;

import java.util.Date;

import com.yukthi.persistence.repository.annotations.Field;

/**
 * SprintDropDown for ui drop down.
 * 
 * @author Pritam
 */
public class SprintDropDown
{
	/**
	 * sprint id.
	 **/
	@Field("id")
	private Long id;

	/**
	 * Sprint name.
	 */
	@Field("name")
	private String name;

	@Field("startDate")
	private Date startDate;
	
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

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
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
}
