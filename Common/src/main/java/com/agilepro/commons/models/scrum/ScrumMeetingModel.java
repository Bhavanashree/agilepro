package com.agilepro.commons.models.scrum;

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
	 */
	public ScrumMeetingModel(Long projectId)
	{
		super();
		this.projectId = projectId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}

	public Long getProjectId()
	{
		return projectId;
	}

	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}
}
