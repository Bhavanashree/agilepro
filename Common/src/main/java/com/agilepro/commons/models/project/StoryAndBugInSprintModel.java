package com.agilepro.commons.models.project;

import java.util.List;

import com.agilepro.commons.models.bug.BugModel;

/**
 * Story and Bug model which are in sprint.
 * 
 * @author Pritam.
 */
public class StoryAndBugInSprintModel
{
	/**
	 * Story models.
	 */
	private List<StoryModel> storyModels;

	/**
	 * Bug Models.
	 */
	private List<BugModel> bugModels;

	/**
	 * Instantiate new StoryAndBugInSprintModel with provided values.
	 * 
	 * @param storyModels
	 *            new story models.
	 * @param bugModels
	 *            new bug models.
	 */
	public StoryAndBugInSprintModel(List<StoryModel> storyModels, List<BugModel> bugModels)
	{
		super();
		this.storyModels = storyModels;
		this.bugModels = bugModels;
	}

	/**
	 * Gets the story models.
	 * 
	 * @return the story models.
	 */
	public List<StoryModel> getStoryModels()
	{
		return storyModels;
	}

	/**
	 * Set the story models.
	 * 
	 * @param storyModels
	 *            the new story models.
	 */
	public void setStoryModels(List<StoryModel> storyModels)
	{
		this.storyModels = storyModels;
	}

	/**
	 * Gets the bug models.
	 * 
	 * @return the bug models.
	 */
	public List<BugModel> getBugModels()
	{
		return bugModels;
	}

	/**
	 * Set the bug models.
	 * 
	 * @param bugModels
	 *            the new bug models.
	 */
	public void setBugModels(List<BugModel> bugModels)
	{
		this.bugModels = bugModels;
	}
}
