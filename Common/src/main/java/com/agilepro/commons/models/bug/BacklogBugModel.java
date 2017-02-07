package com.agilepro.commons.models.bug;

import com.yukthi.persistence.repository.annotations.Field;

/**
 * BacklogBug model for fetching the bug model which are not in a sprint.
 * 
 * @author Pritam.
 */
public class BacklogBugModel
{
	/**
	 * Id of the story record.
	 */
	@Field("id")
	private Long id;

	/**
	 * Title of the story.
	 */
	@Field("title")
	private String title;

	/**
	 * The story points.
	 **/
	@Field("storyPoints")
	private Integer storyPoints;

	/**
	 * Priority of the story.
	 */
	@Field("priority")
	private Integer priority;

	/**
	 * Indicates true for a bug model.
	 */
	private Boolean isBug = true;

	/**
	 * Gets isBug.
	 * 
	 * @return isBug.
	 */
	public Boolean getIsBug()
	{
		return isBug;
	}

	/**
	 * Sets isBug.
	 * 
	 * @param isBug
	 *            the new isBug.
	 */
	public void setIsBug(Boolean isBug)
	{
		this.isBug = isBug;
	}

	/**
	 * Gets the story id.
	 * 
	 * @return the story id.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the story id.
	 * 
	 * @param id
	 *            the new story id.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            the new title.
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Gets the story points.
	 * 
	 * @return the story points.
	 */
	public Integer getStoryPoints()
	{
		return storyPoints;
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
	 * Set the priority.
	 * 
	 * @param priority
	 *            the new priority.
	 */
	public void setPriority(Integer priority)
	{
		this.priority = priority;
	}

	/**
	 * Sets the story points.
	 * 
	 * @param storyPoints
	 *            the new story points.
	 */
	public void setStoryPoints(Integer storyPoints)
	{
		this.storyPoints = storyPoints;
	}
}
