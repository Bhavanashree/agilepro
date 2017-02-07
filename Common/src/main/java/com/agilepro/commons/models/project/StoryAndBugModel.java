package com.agilepro.commons.models.project;

import java.util.List;

import com.agilepro.commons.models.bug.BacklogBugModel;

/**
 * List of stories and list of bugs.
 * 
 * @author Pritam.
 */
public class StoryAndBugModel
{
	/**
	 * Backlog story models.
	 */
	private List<BacklogStoryModel> backlogStoryModels;

	/**
	 * Backlog bug models.
	 */
	private List<BacklogBugModel> backlogBugModels;

	/**
	 * Instantiate new story and bug model with provided argument.
	 * 
	 * @param backLogStoryModels
	 *            provided new backLogStoryModels.
	 * @param backlogBugModels
	 *            provided new backlogBugModels.
	 */
	public StoryAndBugModel(List<BacklogStoryModel> backLogStoryModels, List<BacklogBugModel> backlogBugModels)
	{
		super();
		this.backlogStoryModels = backLogStoryModels;
		this.backlogBugModels = backlogBugModels;
	}

	/**
	 * Gets the backlog story models.
	 * 
	 * @return the backlog story models.
	 */
	public List<BacklogStoryModel> getBacklogStoryModels()
	{
		return backlogStoryModels;
	}

	/**
	 * Sets the backlog story models.
	 * 
	 * @param backlogStoryModels
	 *            the new backlog story models.
	 */
	public void setBacklogStoryModels(List<BacklogStoryModel> backlogStoryModels)
	{
		this.backlogStoryModels = backlogStoryModels;
	}

	/**
	 * Gets the backlog bug models.
	 * 
	 * @return the backlog bug models.
	 */
	public List<BacklogBugModel> getBacklogBugModels()
	{
		return backlogBugModels;
	}

	/**
	 * Sets the backlog bug models.
	 * 
	 * @param backlogBugModels
	 *            the new backlogs bug models.
	 */
	public void setBacklogBugModels(List<BacklogBugModel> backlogBugModels)
	{
		this.backlogBugModels = backlogBugModels;
	}
}
