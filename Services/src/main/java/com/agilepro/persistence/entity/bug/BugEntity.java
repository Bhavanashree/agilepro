package com.agilepro.persistence.entity.bug;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.BugStatus;
import com.agilepro.commons.PriorityStatus;
import com.agilepro.commons.models.bug.BugModel;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * The Class BugEntity.
 */

@ExtendableEntity(name = "Bug")
@Table(name = "BUG")
public class BugEntity extends WebutilsExtendableEntity
{
	/**
	 * The name.
	 **/
	@Column(name = "TITLE")
	private String title;

	/**
	 * The description.
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The reported by.
	 **/

	@ManyToOne
	@PropertyMapping(type = BugModel.class, from = "reportedBy", subproperty = "id")
	@Column(name = "BUG_REPORTED_BY")
	private EmployeeEntity reportedBy;

	/**
	 * The bug status.
	 **/
	@Column(name = "BUG_STATUS")
	@DataTypeMapping(type = DataType.STRING)
	private BugStatus bugStatus;

	/**
	 * The owner.
	 **/
	@ManyToOne
	@PropertyMapping(type = BugModel.class, from = "owner", subproperty = "id")
	@Column(name = "BUG_OWNER_ID")
	private EmployeeEntity owner;

	/**
	 * priority status.
	 **/
	@DataTypeMapping(type = DataType.STRING)
	private PriorityStatus priorityStatus;

	/**
	 * The project.
	 **/
	@ManyToOne
	@PropertyMapping(type = BugModel.class, from = "project", subproperty = "id")
	@Column(name = "BUG_PROJECT_ID")
	private ProjectEntity project;

	/**
	 * The story entity.
	 **/
	@Column(name = "BUG_STORY_ID")
	@ManyToOne
	@PropertyMapping(type = BugModel.class, from = "story", subproperty = "id")
	private StoryEntity story;

	/**
	 * to set sprint target.
	 **/
	@ManyToOne
	@PropertyMapping(type = BugModel.class, from = "sprint", subproperty = "id")
	@Column(name = "TARGET_SPRINT_ID")
	private SprintEntity targetSprint;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
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
	 * Gets the bug status.
	 *
	 * @return the bug status
	 */
	public BugStatus getBugStatus()
	{
		return bugStatus;
	}

	/**
	 * Sets the bug status.
	 *
	 * @param bugStatus
	 *            the new bug status
	 */
	public void setBugStatus(BugStatus bugStatus)
	{
		this.bugStatus = bugStatus;
	}

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public EmployeeEntity getOwner()
	{
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner
	 *            the new owner
	 */
	public void setOwner(EmployeeEntity owner)
	{
		this.owner = owner;
	}

	/**
	 * Gets the story.
	 *
	 * @return the story
	 */
	public StoryEntity getStory()
	{
		return story;
	}

	/**
	 * Sets the story.
	 *
	 * @param story
	 *            the new story
	 */
	public void setStory(StoryEntity story)
	{
		this.story = story;
	}

	/**
	 * Gets the target sprint.
	 *
	 * @return the target sprint
	 */
	public SprintEntity getTargetSprint()
	{
		return targetSprint;
	}

	/**
	 * Sets the target sprint.
	 *
	 * @param targetSprint
	 *            the new target sprint
	 */
	public void setTargetSprint(SprintEntity targetSprint)
	{
		this.targetSprint = targetSprint;
	}

	/**
	 * Gets the priority status.
	 *
	 * @return the priority status
	 */
	public PriorityStatus getPriorityStatus()
	{
		return priorityStatus;
	}

	/**
	 * Sets the priority status.
	 *
	 * @param priorityStatus
	 *            the new priority status
	 */
	public void setPriorityStatus(PriorityStatus priorityStatus)
	{
		this.priorityStatus = priorityStatus;
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
	 * Gets the reported by.
	 *
	 * @return the reported by
	 */
	public EmployeeEntity getReportedBy()
	{
		return reportedBy;
	}

	/**
	 * Sets the reported by.
	 *
	 * @param reportedBy
	 *            the new reported by
	 */
	public void setReportedBy(EmployeeEntity reportedBy)
	{
		this.reportedBy = reportedBy;
	}
}
