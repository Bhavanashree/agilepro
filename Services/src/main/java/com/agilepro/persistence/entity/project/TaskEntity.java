package com.agilepro.persistence.entity.project;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.agilepro.commons.TaskStatus;
import com.agilepro.commons.models.project.StoryModel;
import com.agilepro.commons.models.project.TaskModel;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * Stories are sub-divided to task.
 * 
 * @author Pritam.
 */
@ExtendableEntity(name = "Task")
@Table(name = "TASK")
public class TaskEntity extends WebutilsExtendableEntity
{
	/**
	 * The task title.
	 **/
	@Column(name = "TITLE", length = 50, nullable = false)
	private String title;

	/**
	 * The task description.
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The estimation of time.
	 **/
	@Column(name = "ESTIMATE_TIME")
	private Integer estimateTime;
	
	/**
	 * The project id.
	 */
	@Column(name = "PROJECT_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = TaskModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity projectId;

	/**
	 * The status.
	 **/
	@Column(name = "STATUS")
	@DataTypeMapping(type = DataType.STRING)
	private TaskStatus status;

	/**
	 * The actualTime.
	 **/
	@Column(name = "ACTUAL_TIME_TAKEN")
	private Integer actualTimeTaken;

	/**
	 * list of stories.
	 */
	@Column(name = "STORY_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = TaskModel.class, from = "storyId", subproperty = "id")
	private StoryEntity story;

	/**
	 * Instantiates a new task entity.
	 */
	public TaskEntity()
	{}

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
	 * Gets the project id.
	 *
	 * @return the project id
	 */
	public ProjectEntity getProjectId()
	{
		return projectId;
	}

	/**
	 * Sets the project id.
	 *
	 * @param projectId
	 *            the new project id
	 */
	public void setProjectId(ProjectEntity projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public TaskStatus getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(TaskStatus status)
	{
		this.status = status;
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
	 * Get estimate time.
	 * 
	 * @return the estimate time.
	 */
	public Integer getEstimateTime()
	{
		return estimateTime;
	}

	/**
	 * Set estimate time.
	 * 
	 * @param estimateTime
	 *            the new estimate time.
	 */
	public void setEstimateTime(Integer estimateTime)
	{
		this.estimateTime = estimateTime;
	}

	/**
	 * Gets the actual time taken.
	 * 
	 * @return the actual time taken.
	 */
	public Integer getActualTimeTaken()
	{
		return actualTimeTaken;
	}

	/**
	 * Sets the actual time taken.
	 * 
	 * @param actualTimeTaken
	 *            the new actual time taken.
	 */
	public void setActualTimeTaken(Integer actualTimeTaken)
	{
		this.actualTimeTaken = actualTimeTaken;
	}
}
