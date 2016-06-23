package com.agilepro.persistence.entity.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ProjectEntity.
 * 
 * @author Pritam
 */
@Table(name = "PROJECTS")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_NAME", fields = { "spaceIdentity", "name" }) })
public class ProjectsEntity extends WebutilsEntity
{
	/**
	 * The name.
	 **/
	@Column(name = "NAME", length = 50, nullable = false)
	@UniqueConstraint(name = "name", message = "Please provide a different name, provided name is already present")
	private String name;

	/**
	 * The start date.
	 **/
	@Column(name = "START_DATE")
	private Date startDate;

	/**
	 * The end date.
	 **/
	@Column(name = "END_DATE")
	private Date endDate;
	
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
	 * @param startDate
	 *            the new start date
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
	 * @param endDate
	 *            the new end date
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
}
