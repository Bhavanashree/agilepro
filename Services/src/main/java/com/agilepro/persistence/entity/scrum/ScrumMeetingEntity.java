package com.agilepro.persistence.entity.scrum;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.agilepro.commons.models.scrum.ScrumMeetingModel;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ScrumMeetingEntity.
 * 
 * @author Pritam
 */
@Table(name = "SCRUM_MEETING")
public class ScrumMeetingEntity extends WebutilsEntity
{
	/**
	 * The project.
	 **/
	@Column(name = "PROJECT_ID")
	@ManyToMany
	@PropertyMapping(type = ScrumMeetingModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity project;

	/**
	 * The data.
	 **/
	@Column(name = "DATA")
	private String data;

	/**
	 * The sprint.
	 **/
	@Column(name = "SPRINT")
	@PropertyMapping(type = ScrumMeetingModel.class, from = "sprintId", subproperty = "id")
	private SprintEntity sprint;

	public ProjectEntity getProject()
	{
		return project;
	}

	public void setProject(ProjectEntity project)
	{
		this.project = project;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public SprintEntity getSprint()
	{
		return sprint;
	}

	public void setSprint(SprintEntity sprint)
	{
		this.sprint = sprint;
	}
}
