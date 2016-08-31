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
	
	private String displayDate;
	
	private Boolean displayLeft = false;
	
	private Boolean displayRight = false;
	
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

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getTime()
	{
		return time;
	}


	public void setTime(String time)
	{
		this.time = time;
	}

	public String getDisplayDate()
	{
		return displayDate;
	}

	public void setDisplayDate(String displayDate)
	{
		this.displayDate = displayDate;
	}


	public Boolean getDisplayLeft()
	{
		return displayLeft;
	}

	public void setDisplayLeft(Boolean displayLeft)
	{
		this.displayLeft = displayLeft;
	}

	public Boolean getDisplayRight()
	{
		return displayRight;
	}

	public void setDisplayRight(Boolean displayRight)
	{
		this.displayRight = displayRight;
	}

	public Long getConversationTitleId()
	{
		return conversationTitleId;
	}

	public void setConversationTitleId(Long conversationTitleId)
	{
		this.conversationTitleId = conversationTitleId;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
}