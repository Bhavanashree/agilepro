package com.agilepro.persistence.entity.project;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.agilepro.commons.models.sprint.SprintModel;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * SprintEntity class maintains Sprints of project.
 */
@ExtendableEntity(name = "Sprint")
@Table(name = "SPRINT")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_PROJECT_TITLE", fields = { "spaceIdentity", "project", "name" })})
public class SprintEntity extends WebutilsExtendableEntity
{
	/**
	 * The name of Sprint.
	 */
	@Column(name = "NAME", length = 50)
	private String name;

	/**
	 * The sprint description.
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The start date of sprint.
	 */
	@Column(name = "START_DATE")
	private Date startDate;

	/**
	 * The end date of sprint.
	 */
	@Column(name = "END_DATE")
	private Date endDate;

	/**
	 * project id of sprint.
	 */
	@Column(name = "PROJECT_ID")
	@ManyToOne
	@PropertyMapping(type = SprintModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity project;

	/**
	 * The list of stories under this sprint.
	 **/
	@OneToMany(mappedBy = "sprint")
	private List<StoryEntity> stories;

	/**
	 * Instantiates a new sprint entity.
	 */
	public SprintEntity()
	{}

	/**
	 * Instantiates a new sprint entity.
	 *
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
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
	 * @param name
	 *            the new name
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
	 * @param description
	 *            the new description
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

	/**
	 * Gets the stories.
	 *
	 * @return the stories
	 */
	public List<StoryEntity> getStories()
	{
		return stories;
	}

	/**
	 * Sets the stories.
	 *
	 * @param stories
	 *            the new stories
	 */
	public void setStories(List<StoryEntity> stories)
	{
		this.stories = stories;
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
}