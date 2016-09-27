package com.agilepro.persistence.entity.admin;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Table;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.webutils.repository.WebutilsEntity;

@Table(name = "REALSE")
 @UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_NAME", fields = { "spaceIdentity", "name" }) })
public class ReleaseEntity extends WebutilsEntity
{
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
	 * The description.
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

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
}
