package com.agilepro.persistence.entity.scrum;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * ScrumMeetingConversationEntity holds the conversation message of scrum.
 * Records are inserted from the conversation message. 
 * 
 * @author Pritam
 */
@Table(name = "SCRUM_MEETING_CONVERSATION")
public class ScrumMeetingConversationEntity extends WebutilsEntity
{
	/**
	 * The scrum meeting.
	 **/
	@ManyToOne
	@PropertyMapping(type = ScrumMeetingConversationModel.class, from = "scrumMeetingId", subproperty = "id")
	@Column(name = "SCRUM_MEETING_ID", nullable = false)
	private ScrumMeetingEntity scrumMeeting;

	/**
	 * The story.
	 **/
	@ManyToOne
	@PropertyMapping(type = ScrumMeetingConversationModel.class, from = "storyId", subproperty = "id")
	@Column(name = "STORY_ID")
	private StoryEntity story;

	/**
	 * The user entity.
	 **/
	@Column(name = "USER_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = ScrumMeetingConversationModel.class, from = "userId", subproperty = "id")
	private UserEntity user;

	/**
	 * The provided by.
	 **/
	private String providedBy;

	/**
	 * The message.
	 */
	@Column(name = "MESSAGE")
	private String message;

	/**
	 * Gets the scrum meeting.
	 *
	 * @return the scrum meeting
	 */
	public ScrumMeetingEntity getScrumMeeting()
	{
		return scrumMeeting;
	}

	/**
	 * Sets the scrum meeting.
	 *
	 * @param scrumMeeting
	 *            the new scrum meeting
	 */
	public void setScrumMeeting(ScrumMeetingEntity scrumMeeting)
	{
		this.scrumMeeting = scrumMeeting;
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
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UserEntity getUser()
	{
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(UserEntity user)
	{
		this.user = user;
	}

	/**
	 * Gets the story.
	 *
	 * @return the story
	 */
	public StoryEntity getStory()
	{
		return story;
	}

	/**
	 * Sets the story.
	 *
	 * @param story
	 *            the new story
	 */
	public void setStory(StoryEntity story)
	{
		this.story = story;
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
}
