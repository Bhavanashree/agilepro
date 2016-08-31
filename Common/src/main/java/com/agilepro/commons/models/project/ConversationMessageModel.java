package com.agilepro.commons.models.project;

import java.util.Date;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class ConversationMessageModel.
 * 
 * @author Pritam
 */
@Model
public class ConversationMessageModel
{
	/**
	 * The id.
	 */
	private Long id;

	/**
	 * The message.
	 */
	@MinLen(1)
	@MaxLen(1000)
	private String message;

	/**
	 * The time.
	 */
	private Date date;

	/**
	 * The conversation title id.
	 **/
	@Required
	private Long conversationTitleId;

	/**
	 * The display name.
	 **/
	private String displayName;

	/**
	 * The time.
	 **/
	private String time;

	/**
	 * The display date.
	 **/
	private String displayDate;

	/**
	 * The display left.
	 **/
	private Boolean displayLeft = false;

	/**
	 * The display right.
	 **/
	private Boolean displayRight = false;

	/**
	 * The user id.
	 **/
	@Required
	private Long userId;

	/**
	 * The version.
	 */
	private Integer version;

	/**
	 * Instantiates a new conversation model.
	 */
	public ConversationMessageModel()
	{}

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
	 * Gets the display left.
	 *
	 * @return the display left
	 */
	public Boolean getDisplayLeft()
	{
		return displayLeft;
	}

	/**
	 * Sets the display left.
	 *
	 * @param displayLeft
	 *            the new display left
	 */
	public void setDisplayLeft(Boolean displayLeft)
	{
		this.displayLeft = displayLeft;
	}

	/**
	 * Gets the display right.
	 *
	 * @return the display right
	 */
	public Boolean getDisplayRight()
	{
		return displayRight;
	}

	/**
	 * Sets the display right.
	 *
	 * @param displayRight
	 *            the new display right
	 */
	public void setDisplayRight(Boolean displayRight)
	{
		this.displayRight = displayRight;
	}

	/**
	 * Gets the conversation title id.
	 *
	 * @return the conversation title id
	 */
	public Long getConversationTitleId()
	{
		return conversationTitleId;
	}

	/**
	 * Sets the conversation title id.
	 *
	 * @param conversationTitleId
	 *            the new conversation title id
	 */
	public void setConversationTitleId(Long conversationTitleId)
	{
		this.conversationTitleId = conversationTitleId;
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
}