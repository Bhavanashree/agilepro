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
	 * Project member id.
	 */
	private Long projectMemberId;

	/**
	 * Project member name.
	 */
	private String projectMemberName;

	/**
	 * Active user id.
	 */
	@Required
	private Long activeUserId;

	/**
	 * Project id.
	 */
	private Long projectId;

	/**
	 * The time.
	 * 
	 */
	private Date date;

	/**
	 * Display date in ui.
	 */
	private String displayDate;

	/**
	 * Time.
	 */
	private String time;

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

	/**
	 * Gets the project member id.
	 * 
	 * @return the project member id.
	 */
	public Long getProjectMemberId()
	{
		return projectMemberId;
	}

	/**
	 * Set the project member id.
	 * 
	 * @param projectMemberId
	 *            the new project member id.
	 */
	public void setProjectMemberId(Long projectMemberId)
	{
		this.projectMemberId = projectMemberId;
	}

	/**
	 * Gets the active user id.
	 * 
	 * @return the active user id.
	 */
	public Long getActiveUserId()
	{
		return activeUserId;
	}

	/**
	 * Set the active user id.
	 * 
	 * @param activeUserId
	 *            the active user id.
	 */
	public void setActiveUserId(Long activeUserId)
	{
		this.activeUserId = activeUserId;
	}

	/**
	 * Gets the project id.
	 * 
	 * @return the project id.
	 */
	public Long getProjectId()
	{
		return projectId;
	}

	/**
	 * Set the project id.
	 * 
	 * @param projectId
	 *            the project id.
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Gets the display date.
	 * 
	 * @return the display date.
	 */
	public String getDisplayDate()
	{
		return displayDate;
	}

	/**
	 * Set the display date.
	 * 
	 * @param displayDate
	 *            the new display date.
	 */
	public void setDisplayDate(String displayDate)
	{
		this.displayDate = displayDate;
	}

	/**
	 * Gets the time.
	 * 
	 * @return the time.
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * Sets the time.
	 * 
	 * @param time
	 *            the new time.
	 */
	public void setTime(String time)
	{
		this.time = time;
	}

	/**
	 * Gets the project member name.
	 * 
	 * @return the project member name.
	 */
	public String getProjectMemberName()
	{
		return projectMemberName;
	}

	/**
	 * Sets the project member name.
	 * 
	 * @param projectMemberName
	 *            the new project member name.
	 */
	public void setProjectMemberName(String projectMemberName)
	{
		this.projectMemberName = projectMemberName;
	}
}
