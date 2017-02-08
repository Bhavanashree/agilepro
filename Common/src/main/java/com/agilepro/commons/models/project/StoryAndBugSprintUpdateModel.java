package com.agilepro.commons.models.project;

import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;

/**
 * Story sprint update model.
 * 
 * @author Pritam.
 */
@Model
public class StoryAndBugSprintUpdateModel
{
	/**
	 * Story ids for updating with the sprint id.
	 */
	@IgnoreField
	private Long[] multipleStoryIds;

	/**
	 * Multiple bug ids.
	 */
	@IgnoreField
	private Long[] multipleBugIds;

	/**
	 * Sprint id to be update for the story ids.
	 */
	private Long sprintId;

	/**
	 * Gets the multiple story ids.
	 * 
	 * @return the multiple story ids.
	 */
	public Long[] getMultipleStoryIds()
	{
		return multipleStoryIds;
	}

	/**
	 * Set the multiple story ids.
	 * 
	 * @param multipleStoryIds
	 *            new multiple story ids.
	 */
	public void setMultipleStoryIds(Long[] multipleStoryIds)
	{
		this.multipleStoryIds = multipleStoryIds;
	}

	/**
	 * Gets multiple bug ids.
	 * 
	 * @return the multiple bug ids.
	 */
	public Long[] getMultipleBugIds()
	{
		return multipleBugIds;
	}

	/**
	 * Set the multiple bug ids.
	 * 
	 * @param multipleBugIds
	 *            new multiple bug ids.
	 */
	public void setMultipleBugIds(Long[] multipleBugIds)
	{
		this.multipleBugIds = multipleBugIds;
	}

	/**
	 * Gets the sprint id.
	 * 
	 * @return the sprint id.
	 */
	public Long getSprintId()
	{
		return sprintId;
	}

	/**
	 * Set the sprint id.
	 * 
	 * @param sprintId
	 *            the new sprint id.
	 */
	public void setSprintId(Long sprintId)
	{
		this.sprintId = sprintId;
	}
}
