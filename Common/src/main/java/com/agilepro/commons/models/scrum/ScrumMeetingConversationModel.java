package com.agilepro.commons.models.scrum;

import java.util.Date;
import java.util.Set;

import com.yukthi.validation.annotations.Required;
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
	private Set<Long> projectMemberIds;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * The message.
	 **/
	@Required
	private String message;

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

	/**
	 * The provided by.
	 **/
	private String providedBy;

	/**
	 * The updated on.
	 **/
	private Date updatedOn;
	
	/** 
	 * The customer id. 
	 **/
	private Long customerId;

	/**
	 * Instantiates a new scrum meeting conversation model.
	 */
	public ScrumMeetingConversationModel()
	{
		super();
	}
	
	public ScrumMeetingConversationModel(Long scrumMeetingId, Long userId, String message, Long customerId)
	{
		super();
		this.scrumMeetingId = scrumMeetingId;
		this.userId = userId;
		this.message = message;
		this.customerId = customerId;
	}

	/**
	 * Gets the provided by.
	 *
	 * @return the provided by
	 */
	public String getProvidedBy()
	{
		return providedBy;
	}

	/**
	 * Sets the provided by.
	 *
	 * @param providedBy
	 *            the new provided by
	 */
	public void setProvidedBy(String providedBy)
	{
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

	public Set<Long> getProjectMemberIds()
	{
		return projectMemberIds;
	}

	public void setProjectMemberIds(Set<Long> projectMemberIds)
	{
		this.projectMemberIds = projectMemberIds;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public Date getUpdatedOn()
	{
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            the new updated on
	 */
	public void setUpdatedOn(Date updatedOn)
	{
		this.updatedOn = updatedOn;
	}

	public Long getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(Long customerId)
	{
		this.customerId = customerId;
	}
}
