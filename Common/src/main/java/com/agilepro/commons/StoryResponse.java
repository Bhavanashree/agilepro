package com.agilepro.commons;

import java.util.Map;
import com.yukthi.webutils.common.models.BasicReadResponse;

/**
 * Story response, sending back the success response after save of story. Having
 * id and priority got update after child svae.
 * 
 * @author Pritam.
 */
@SuppressWarnings("rawtypes")
public class StoryResponse extends BasicReadResponse
{
	/**
	 * Newly saved story id.
	 */
	private Long newStoryId;

	/**
	 * Map of records got updated for sub story save.
	 */
	private Map<Long, Integer> storyIdPriority;

	/**
	 * New Story response with default values.
	 */
	public StoryResponse()
	{
		super();
	}

	/**
	 * New story response.
	 * 
	 * @param newStoryId
	 *            new storyId.
	 * @param storyIdPriority
	 *            new storyIdPriority.
	 */
	public StoryResponse(Long newStoryId, Map<Long, Integer> storyIdPriority)
	{
		super();
		this.newStoryId = newStoryId;
		this.storyIdPriority = storyIdPriority;
	}

	/**
	 * Gets the story id.
	 * 
	 * @return the story id.
	 */
	public Long getNewStoryId()
	{
		return newStoryId;
	}

	/**
	 * Sets the story id.
	 * 
	 * @param newStoryId
	 *            the new story id.
	 */
	public void setNewStoryId(Long newStoryId)
	{
		this.newStoryId = newStoryId;
	}

	/**
	 * Gets the story id and priority.
	 * 
	 * @return the story id and priority.
	 */
	public Map<Long, Integer> getStoryIdPriority()
	{
		return storyIdPriority;
	}

	/**
	 * Sets the story id and priority.
	 * 
	 * @param storyIdPriority
	 *            the new story id priority.
	 */
	public void setStoryIdPriority(Map<Long, Integer> storyIdPriority)
	{
		this.storyIdPriority = storyIdPriority;
	}
}
