package com.agilepro.commons.models.scrum;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ScrumMeetingConversationModel.
 * 
 * @author Pritam
 */
@Model(name = "ScrumMeetingConversation")
public class ScrumMeetingConversationModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The scrum meeting id.
	 **/
	@Required
	private Long scrumMeetingId;

	/**
	 * The user id. *
	 */
	@Required
	private Long userId;

	/**
	 * The story id.
	 **/
	private Long storyId;

	/**
	 * The project member ids.
	 **/
	private List<Long> projectMemberIds;

	@IgnoreField
	private Map<Long, String> projectMembers;
	
	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * The message.
	 **/
	private String message;

	/**
	 * The date.
	 **/
	private Date date;

	/**
	 * The display name.
	 **/
	private String displayName;

	/**
	 * The display sprint.
	 **/
	private String displayStory;

	/**
	 * The time.
	 **/
	private String time;

	/**
	 * The display date.
	 **/
	private String displayDate;

	private String providedBy;
	
	public String getProvidedBy() {
		return providedBy;
	}

	public void setProvidedBy(String providedBy) {
		this.providedBy = providedBy;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the scrum meeting id.
	 *
	 * @return the scrum meeting id
	 */
	public Long getScrumMeetingId()
	{
		return scrumMeetingId;
	}

	/**
	 * Sets the scrum meeting id.
	 *
	 * @param scrumMeetingId
	 *            the new scrum meeting id
	 */
	public void setScrumMeetingId(Long scrumMeetingId)
	{
		this.scrumMeetingId = scrumMeetingId;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date
	 *            the new date
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId
	 *            the new user id
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @param displayName
	 *            the new display name
	 */
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * Sets the time.
	 *
	 * @param time
	 *            the new time
	 */
	public void setTime(String time)
	{
		this.time = time;
	}

	/**
	 * Gets the display date.
	 *
	 * @return the display date
	 */
	public String getDisplayDate()
	{
		return displayDate;
	}

	/**
	 * Sets the display date.
	 *
	 * @param displayDate
	 *            the new display date
	 */
	public void setDisplayDate(String displayDate)
	{
		this.displayDate = displayDate;
	}

	/**
	 * Gets the display story.
	 *
	 * @return the display story
	 */
	public String getDisplayStory()
	{
		return displayStory;
	}

	/**
	 * Sets the display story.
	 *
	 * @param displayStory
	 *            the new display story
	 */
	public void setDisplayStory(String displayStory)
	{
		this.displayStory = displayStory;
	}

	/**
	 * Gets the story id.
	 *
	 * @return the story id
	 */
	public Long getStoryId()
	{
		return storyId;
	}

	/**
	 * Sets the story id.
	 *
	 * @param storyId
	 *            the new story id
	 */
	public void setStoryId(Long storyId)
	{
		this.storyId = storyId;
	}

	/**
	 * Gets the project member ids.
	 *
	 * @return the project member ids
	 */
	public List<Long> getProjectMemberIds()
	{
		return projectMemberIds;
	}

	/**
	 * Sets the project member ids.
	 *
	 * @param projectMemberIds
	 *            the new project member ids
	 */
	public void setProjectMemberIds(List<Long> projectMemberIds)
	{
		this.projectMemberIds = projectMemberIds;
	}

	public Map<Long, String> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(Map<Long, String> projectMembers) {
		this.projectMembers = projectMembers;
	}
}
