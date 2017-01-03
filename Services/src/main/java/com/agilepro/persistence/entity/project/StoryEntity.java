package com.agilepro.persistence.entity.project;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.agilepro.commons.StoryStatus;
import com.agilepro.commons.models.project.StoryBulkModel;
import com.agilepro.commons.models.project.StoryModel;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.entity.admin.ProjectTeamEntity;
import com.agilepro.persistence.entity.admin.TagEntity;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.DeleteWithParent;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.utils.annotations.PropertyMappings;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * Maintains the Stories.
 * 
 * @author Bhavana.
 */
@ExtendableEntity(name = "Story")
@Table(name = "STORY")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_PROJECT_TITLE", fields = { "spaceIdentity", "project", "title" }),
					 @UniqueConstraint(name = "SPACE_PROJECT_PRIORITY", fields = { "spaceIdentity", "project", "priority" })})
public class StoryEntity extends WebutilsExtendableEntity
{
	/**
	 * The Story title.
	 **/
	@Column(name = "TITLE", length = 50, nullable = false)
	private String title;

	/**
	 * The story description.
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The estimation of time.
	 **/
	@Column(name = "STORY_POINTS")
	private Integer storyPoints;

	/**
	 * The parent story id.
	 **/
	@ManyToOne
	@PropertyMapping(type = StoryModel.class, from = "parentStoryId", subproperty = "id")
	@Column(name = "PARENT_STORY_ID")
	@DeleteWithParent
	private StoryEntity parentStory;

	/**
	 * The owner.
	 **/
	@ManyToOne
	@PropertyMapping(type = StoryModel.class, from = "ownerId", subproperty = "id")
	@Column(name = "STORY_OWNER_ID")
	private EmployeeEntity owner;

	/**
	 * The status.
	 **/
	@Column(name = "STATUS")
	@DataTypeMapping(type = DataType.STRING)
	private StoryStatus status;

	/**
	 * The priority.
	 */
	@Column(name = "PRIORITY", nullable = false)
	private Integer priority;

	/**
	 * sprintId of the story.
	 */
	@Column(name = "SPRINT_ID")
	@ManyToOne
	@PropertyMapping(type = StoryModel.class, from = "sprintId", subproperty = "id")
	private SprintEntity sprint;

	/**
	 * The project id.
	 */
	@Column(name = "PROJECT_ID", nullable = false)
	@ManyToOne
	@PropertyMappings({ @PropertyMapping(type = StoryModel.class, from = "projectId", subproperty = "id"), @PropertyMapping(type = StoryBulkModel.class, from = "projectId", subproperty = "id") })
	private ProjectEntity project;

	/**
	 * The list of stories under this sprint.
	 **/
	@OneToMany(mappedBy = "story")
	private List<TaskEntity> tasks;

	/**
	 * The time taken for story.
	 */
	@Column(name = "TIME_TAKEN_FOR_A_STORY")
	private Date timeTakenForStory;

	/**
	 * The tags.
	 **/
	@ManyToOne
	@PropertyMapping(type = StoryModel.class, from = "tagId", subproperty = "id")
	@Column(name = "TAG_ID")
	private TagEntity tag;

	/**
	 * To assign the stories to a team.
	 */
	@ManyToOne
	@PropertyMapping(type = StoryModel.class, from = "teamId", subproperty = "id")
	@Column(name = "TEAM_ID")
	private ProjectTeamEntity team;
	
	/**
	 * Story dependencies.
	 */
	@OneToMany(mappedBy = "dependencyStory")
	private List<StoryDependencyEntity> storyDependencies;
	
	/**
	 * Instantiates a new back log entity.
	 */
	public StoryEntity()
	{}

	/**
	 * Instantiates a new back log entity.
	 *
	 * @param title
	 *            the title
	 * @param storyPoints
	 *            the story points
	 * @param description
	 *            the description
	 * @param status
	 *            the status
	 */
	public StoryEntity(String title, Integer storyPoints, String description, StoryStatus status)
	{
		this.title = title;
		this.description = description;
		this.storyPoints = storyPoints;
		this.status = status;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
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
	 * Gets the story points.
	 *
	 * @return the story points
	 */
	public Integer getStoryPoints()
	{
		return storyPoints;
	}

	/**
	 * Sets the story points.
	 *
	 * @param storyPoints
	 *            the new story points
	 */
	public void setStoryPoints(Integer storyPoints)
	{
		this.storyPoints = storyPoints;
	}

	/**
	 * Gets the parent story.
	 *
	 * @return the parent story
	 */
	public StoryEntity getParentStory()
	{
		return parentStory;
	}

	/**
	 * Sets the parent story.
	 *
	 * @param parentStory
	 *            the new parent story
	 */
	public void setParentStory(StoryEntity parentStory)
	{
		this.parentStory = parentStory;
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
	 * Gets the status.
	 *
	 * @return the status
	 */
	public StoryStatus getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(StoryStatus status)
	{
		this.status = status;
	}

	public Integer getPriority()
	{
		return priority;
	}

	public void setPriority(Integer priority)
	{
		this.priority = priority;
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
	 * Gets the tasks.
	 *
	 * @return the tasks
	 */
	public List<TaskEntity> getTasks()
	{
		return tasks;
	}

	/**
	 * Sets the tasks.
	 *
	 * @param tasks
	 *            the new tasks
	 */
	public void setTasks(List<TaskEntity> tasks)
	{
		this.tasks = tasks;
	}

	/**
	 * Gets the time taken for story.
	 *
	 * @return the time taken for story
	 */
	public Date getTimeTakenForStory()
	{
		return timeTakenForStory;
	}

	/**
	 * Sets the time taken for story.
	 *
	 * @param timeTakenForStory
	 *            the new time taken for story
	 */
	public void setTimeTakenForStory(Date timeTakenForStory)
	{
		this.timeTakenForStory = timeTakenForStory;
	}

	/**
	 * Gets the tag.
	 *
	 * @return the tag
	 */
	public TagEntity getTag()
	{
		return tag;
	}

	/**
	 * Sets the tag.
	 *
	 * @param tag
	 *            the new tag
	 */
	public void setTag(TagEntity tag)
	{
		this.tag = tag;
	}

	/**
	 * Gets the team.
	 *
	 * @return the team
	 */
	public ProjectTeamEntity getTeam()
	{
		return team;
	}

	/**
	 * Sets the team.
	 *
	 * @param team
	 *            the new team
	 */
	public void setTeam(ProjectTeamEntity team)
	{
		this.team = team;
	}

	/**
	 * Gets story dependencies.
	 * 
	 * @return story dependencies.
	 */
	public List<StoryDependencyEntity> getStoryDependencies()
	{
		return storyDependencies;
	}

	/**
	 * Sets story dependencies.
	 * 
	 * @param storyDependencies the new dependencies.
	 */
	public void setStoryDependencies(List<StoryDependencyEntity> storyDependencies)
	{
		this.storyDependencies = storyDependencies;
	}
}
