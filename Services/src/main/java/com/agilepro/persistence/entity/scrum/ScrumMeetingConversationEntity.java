package com.agilepro.persistence.entity.scrum;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.project.ConversationMessageModel;
import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.UserEntity;
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
	@Column(name = "SCRUM_MEETING_ID", nullable = false)
	private ScrumMeetingEntity scrumMeeting;

	/**
	 * The user entity.
	 **/
	@Column(name = "USER_ID" , nullable = false)
	@ManyToOne
	@PropertyMapping(type = ScrumMeetingConversationModel.class, from = "userId", subproperty = "id")
	private UserEntity userEntity;
	
	/**
	 * The project member ids.
	 **/
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private List<Long> projectMemberIds;
	
	/**
	 * The message.
	 */
	@Column(name = "MESSAGE")
	private String message;

	/**
	 * The date.
	 **/
	@Column(name = "DATE")
	private Date date;

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
}
