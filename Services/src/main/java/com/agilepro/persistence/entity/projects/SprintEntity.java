package com.agilepro.persistence.entity.projects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * SprintEntity class maintains Sprints of project.
 */
@ExtendableEntity(name = "Sprint")
@Table(name = "SPRINT")
public class SprintEntity extends WebutilsExtendableEntity
{
	
	/** 
	 * The name. 
	 */
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	/**
	 *  The description. 
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 *  The start date.
	 */
	@Column(name = "STARTDATE")
	private Date startDate;

	/**
	 * The end date. 
	 */
	@Column(name = "ENDDATE")
	private Date endDate;

	/**
	 * Instantiates a new sprint entity.
	 */
	public SprintEntity()
	{}

	/**
	 * Instantiates a new sprint entity.
	 *
	 * @param name the name
	 * @param description the description
	 * @param startDate the start date
	 * @param endDate the end date
	 */
	public SprintEntity(String name, String description, Date startDate, Date endDate)
	{
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
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
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
}
