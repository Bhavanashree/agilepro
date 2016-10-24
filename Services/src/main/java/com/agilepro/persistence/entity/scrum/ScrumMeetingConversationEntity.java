package com.agilepro.persistence.entity.scrum;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ScrumMeetingConversationEntity.
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
	@Column(name = "SCRUM_MEETING_ID")
	private ScrumMeetingEntity scrumMeeting;

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
}
