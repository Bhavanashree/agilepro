package com.agilepro.commons.models.scrum;

import java.util.Date;

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
}
