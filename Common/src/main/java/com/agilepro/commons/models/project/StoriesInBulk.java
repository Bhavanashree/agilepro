package com.agilepro.commons.models.project;

import java.util.List;

import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class StoriesInBulk.
 */
@Model
public class StoriesInBulk
{

	/** 
	 * The project id.
	 **/
	private Long projectId;
	
	/**
	 * The story bulk models.
	 **/
	@IgnoreField
	private List<StoryBulkModel> stories;

	/**
	 * Instantiates a new list of stories with sub stories.
	 */
	public StoriesInBulk()
	{}

	/**
	 * Gets the stories.
	 *
	 * @return the stories
	 */
	public List<StoryBulkModel> getStories()
	{
		return stories;
	}

	/**
	 * Sets the stories.
	 *
	 * @param stories the new stories
	 */
	public void setStories(List<StoryBulkModel> stories)
	{
		this.stories = stories;
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
	 * @param projectId the new project id
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}
}
