package com.agilepro.commons.models.scrum;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ScrumMeetingModel.
 * 
 * @author Pritam
 */
@Model
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
	 * The sprint id. 
	 **/
	@Required
	private Long sprintId;

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

	public Long getSprintId()
	{
		return sprintId;
	}

	public void setSprintId(Long sprintId)
	{
		this.sprintId = sprintId;
	}
}
