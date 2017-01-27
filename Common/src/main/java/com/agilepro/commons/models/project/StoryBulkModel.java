package com.agilepro.commons.models.project;

import java.util.List;

import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class StoryBulkModel.
 */
@Model
public class StoryBulkModel
{

	/**
	 * The id.
	 **/
	private Long id;

	/**
	 * The title.
	 **/
	private String title;

	/**
	 * The description.
	 **/
	private String description;

	/**
	 * The story points.
	 **/
	private Integer storyPoints;

	/**
	 * The parent id.
	 **/
	private Long parentStoryId;

	/**
	 * The child stroies.
	 **/
	@IgnoreField
	private List<StoryBulkModel> substories;

	/**
	 * The project id.
	 **/
	private Long projectId;

	/**
	 * Priority.
	 */
	private Integer priority;

	/**
	 * Is management story, true if it has child stories or else by default
	 * false.
	 */
	private Boolean isManagementStory;

	/**
	 * Instantiates a new story bulk model.
	 */
	public StoryBulkModel()
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
	 * Gets the substories.
	 *
	 * @return the substories
	 */
	public List<StoryBulkModel> getSubstories()
	{
		return substories;
	}

	/**
	 * Sets the substories.
	 *
	 * @param substories
	 *            the new substories
	 */
	public void setSubstories(List<StoryBulkModel> substories)
	{
		this.substories = substories;
	}

	public Long getParentStoryId()
	{
		return parentStoryId;
	}

	public void setParentStoryId(Long parentStoryId)
	{
		this.parentStoryId = parentStoryId;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
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
	 * Gets the priority.
	 * 
	 * @return the priority.
	 */
	public Integer getPriority()
	{
		return priority;
	}

	/**
	 * Sets the priority.
	 * 
	 * @param priority
	 *            the new priority.
	 */
	public void setPriority(Integer priority)
	{
		this.priority = priority;
	}

	/**
	 * Gets is management story.
	 * 
	 * @return the management story.
	 */
	public Boolean getIsManagementStory()
	{
		return isManagementStory;
	}

	/**
	 * Set the management story.
	 * 
	 * @param isManagementStory
	 *            the new management story.
	 */
	public void setIsManagementStory(Boolean isManagementStory)
	{
		this.isManagementStory = isManagementStory;
	}
}
