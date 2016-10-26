package com.agilepro.commons.models.scrum;

import java.util.Date;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ScrumMeetingModel.
 * 
 * @author Pritam
 */
@Model(name = "ScrumMeeting")
public class ScrumMeetingModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * The project id.
	 **/
	@Required
	private Long projectId;

	/**
	 * The date.
	 **/
	@Required
	private Date date;

	/**
	 * Instantiates a new scrum meeting model.
	 */
	public ScrumMeetingModel()
	{
		super();
	}

	/**
	 * Instantiates a new scrum meeting model.
	 *
	 * @param projectId
	 *            the project id
	 * @param date
	 *            the date
	 */
	public ScrumMeetingModel(Long projectId, Date date)
	{
		super();
		this.projectId = projectId;
		this.date = date;
	}

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
	 * Gets the project id.
	 *
	 * @return the project id
	 */
	public Long getProjectId()
	{
		return projectId;
	}

	/**
	 * Sets the project id.
	 *
	 * @param projectId
	 *            the new project id
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date
	 *            the new date
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}
}
