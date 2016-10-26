package com.agilepro.persistence.entity.scrum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.scrum.ScrumMeetingModel;
import com.agilepro.persistence.entity.admin.ProjectEntity;
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
	@Column(name = "PROJECT_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = ScrumMeetingModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity project;

	/**
	 * The date.
	 **/
	@Column(name = "DATE")
	private Date date;

	/**
	 * Instantiates a new scrum meeting entity.
	 */
	public ScrumMeetingEntity()
	{
		super();
	}

	/**
	 * Instantiates a new scrum meeting entity.
	 *
	 * @param project
	 *            the project
	 * @param date
	 *            the date
	 */
	public ScrumMeetingEntity(ProjectEntity project, Date date)
	{
		super();
		this.project = project;
		this.date = date;
	}

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
