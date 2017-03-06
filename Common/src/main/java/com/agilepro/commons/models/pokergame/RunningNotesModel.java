package com.agilepro.commons.models.pokergame;

import java.util.Date;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;

/**
 * Running notes model for ui.
 * 
 * @author Pritam.
 */
@Model
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
	 * Running note.
	 */
	@Required
	private String runningNote;

	/**
	 * The time.
	 * 
	 */
	private Date date;

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

	/**
	 * Gets the date.
	 * 
	 * @return the date.
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Sets the date.
	 * 
	 * @param date
	 *            the new date.
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	/**
	 * Gets the running note.
	 * 
	 * @return the running note.
	 */
	public String getRunningNote()
	{
		return runningNote;
	}

	/**
	 * Set the running note.
	 * 
	 * @param runningNote
	 *            the new running note.
	 */
	public void setRunningNote(String runningNote)
	{
		this.runningNote = runningNote;
	}
}
