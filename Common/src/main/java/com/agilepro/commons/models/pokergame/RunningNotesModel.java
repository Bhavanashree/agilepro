package com.agilepro.commons.models.pokergame;

/**
 * Running notes model for ui.
 * 
 * @author Pritam.
 */
public class RunningNotesModel
{
	/**
	 * Story id for mapping with bug.
	 */
	private Long bugId;

	/**
	 * Bug id for mapping with story.
	 */
	private Long storyId;

	/**
	 * Running notes.
	 */
	private String runningNotes;

	/**
	 * Gets Running notes.
	 * 
	 * @return the new running notes.
	 */
	public String getRunningNotes()
	{
		return runningNotes;
	}

	/**
	 * Sets the running notes.
	 * 
	 * @param runningNotes
	 *            the new running notes.
	 */
	public void setRunningNotes(String runningNotes)
	{
		this.runningNotes = runningNotes;
	}

	/**
	 * Gets the bug id.
	 * 
	 * @return the bug id.
	 */
	public Long getBugId()
	{
		return bugId;
	}

	/**
	 * Sets the bug id.
	 * 
	 * @param bugId
	 *            the new bug id.
	 */
	public void setBugId(Long bugId)
	{
		this.bugId = bugId;
	}

	/**
	 * Gets the story id.
	 * 
	 * @return the story id.
	 */
	public Long getStoryId()
	{
		return storyId;
	}

	/**
	 * Sets the story id.
	 * 
	 * @param storyId
	 *            the new story id.
	 */
	public void setStoryId(Long storyId)
	{
		this.storyId = storyId;
	}
}
