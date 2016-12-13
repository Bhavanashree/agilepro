package com.agilepro.commons;

import java.util.Map;

import com.yukthi.webutils.common.models.BasicReadResponse;

public class StoryResponse extends BasicReadResponse
{
	private Long newStoryId;
	
	private Map<Long, Integer> storyIdPriority;

	public StoryResponse(Long newStoryId, Map<Long, Integer> storyIdPriority)
	{
		super();
		this.newStoryId = newStoryId;
		this.storyIdPriority = storyIdPriority;
	}

	public Long getNewStoryId()
	{
		return newStoryId;
	}

	public void setNewStoryId(Long newStoryId)
	{
		this.newStoryId = newStoryId;
	}

	public Map<Long, Integer> getStoryIdPriority()
	{
		return storyIdPriority;
	}

	public void setStoryIdPriority(Map<Long, Integer> storyIdPriority)
	{
		this.storyIdPriority = storyIdPriority;
	}
}
