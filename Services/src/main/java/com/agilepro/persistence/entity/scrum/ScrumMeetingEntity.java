package com.agilepro.persistence.entity.scrum;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
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
	@ManyToOne
	@PropertyMapping(type = ScrumMeetingModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity project;

	/**
	 * The sprint.
	 **/
	@Column(name = "SPRINT_ID")
	@ManyToOne
	@PropertyMapping(type = ScrumMeetingModel.class, from = "sprintId", subproperty = "id")
	private SprintEntity sprint;

	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	public ProjectEntity getProject()
	{
		return project;
	}

	/**
	 * Sets the project.
	 *
	 * @param project
	 *            the new project
	 */
	public void setProject(ProjectEntity project)
	{
		this.project = project;
	}

	/**
	 * Gets the sprint.
	 *
	 * @return the sprint
	 */
	public SprintEntity getSprint()
	{
		return sprint;
	}

	/**
	 * Sets the sprint.
	 *
	 * @param sprint
	 *            the new sprint
	 */
	public void setSprint(SprintEntity sprint)
	{
		this.sprint = sprint;
	}
}
