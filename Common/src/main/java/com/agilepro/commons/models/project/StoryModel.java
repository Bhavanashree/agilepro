package com.agilepro.commons.models.project;

import java.util.Date;
import java.util.List;

import com.agilepro.commons.StoryStatus;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.ImageInfo;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class StoryModel.
 * 
 * @author Bhavana.
 */

@ExtendableModel(name = "Story")
@Model
public class StoryModel extends AbstractExtendableModel
{

	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The version.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * The name.
	 **/
	@NotEmpty
	@MinLen(3)
	@MaxLen(50)
	private String title;

	/**
	 * The description.
	 **/
	@MaxLen(200)
	@MultilineText
	private String description;

	/**
	 * The story points.
	 **/
	private Integer storyPoints;

	/**
	 * The parent story id.
	 **/
	@LOV(name = "parentStoryId")
	private Long parentStoryId;

	/**
	 * The owner id.
	 **/
	@LOV(name = "projectMembers")
	private Long ownerId;

	/**
	 * The status.
	 **/
	private StoryStatus status;

	/**
	 * The priority.
	 */
	@LOV(name = "priorityLov")
	private Long priority;

	/**
	 * The sprint id.
	 **/
	@NonDisplayable
	private Long sprint;

	/**
	 * the project id.
	 */
	@NonDisplayable
	@Required
	private Long projectId;

	/**
	 * The tasks.
	 */
	@NonDisplayable
	@IgnoreField
	private List<Long> tasks;

	/**
	 * The photo.
	 */
	@NonDisplayable
	private ImageInfo photo;

	/**
	 * The time taken for story.
	 **/
	private Date timeTakenForStory;

	/**
	 * Instantiates a new back log model.
	 */
	public StoryModel()
	{}

	/**
	 * Instantiates a new story model.
	 *
	 * @param title
	 *            the title
	 * @param storyPoints
	 *            the story points
	 * @param description
	 *            the description
	 * @param parentStoryId
	 *            the parent story id
	 * @param priority
	 *            the priority
	 */
	public StoryModel(String title, Integer storyPoints, String description, Long parentStoryId, Long priority)
	{
		this.title = title;
		this.storyPoints = storyPoints;
		this.description = description;
		this.parentStoryId = parentStoryId;
		this.priority = priority;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.common.IExtendableModel#getId()
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
	 * Gets the parent story id.
	 *
	 * @return the parent story id
	 */
	public Long getParentStoryId()
	{
		return parentStoryId;
	}

	/**
	 * Sets the parent story id.
	 *
	 * @param parentStoryId
	 *            the new parent story id
	 */
	public void setParentStoryId(Long parentStoryId)
	{
		this.parentStoryId = parentStoryId;
	}

	/**
	 * Gets the owner id.
	 *
	 * @return the owner id
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * Sets the owner id.
	 *
	 * @param ownerId
	 *            the new owner id
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
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

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public Long getPriority()
	{
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority
	 *            the new priority
	 */
	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	/**
	 * Gets the sprint.
	 *
	 * @return the sprint
	 */
	public Long getSprint()
	{
		return sprint;
	}

	/**
	 * Sets the sprint.
	 *
	 * @param sprint
	 *            the new sprint
	 */
	public void setSprint(Long sprint)
	{
		this.sprint = sprint;
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
	 * Gets the tasks.
	 *
	 * @return the tasks
	 */
	public List<Long> getTasks()
	{
		return tasks;
	}

	/**
	 * Sets the tasks.
	 *
	 * @param tasks
	 *            the new tasks
	 */
	public void setTasks(List<Long> tasks)
	{
		this.tasks = tasks;
	}

	/**
	 * Gets the photo.
	 *
	 * @return the photo
	 */
	public ImageInfo getPhoto()
	{
		return photo;
	}

	/**
	 * Sets the photo.
	 *
	 * @param photo
	 *            the new photo
	 */
	public void setPhoto(ImageInfo photo)
	{
		this.photo = photo;
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
	 * @param timeTakenForStory the new time taken for story
	 */
	public void setTimeTakenForStory(Date timeTakenForStory)
	{
		this.timeTakenForStory = timeTakenForStory;
	}
}
