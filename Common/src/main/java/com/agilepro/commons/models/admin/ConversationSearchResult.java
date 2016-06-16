package com.agilepro.commons.models.admin;

import java.util.Date;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ConversationSearchResult.
 */
@Model
public class ConversationSearchResult
{
	
	/**
	 *  The id.
	 *   */
	@NonDisplayable
	@Field(value = "id")
	private long id;
	
	/**
	 *  The message.
	 *   */
	@Field(value = "message")
	private String message;
	
	/**
	 *  The time. 
	 *  */
	@Field(value = "time")
	private Date time;
	
	/**
	 *  The created on.
	 *   */
	@Field("createdOn")
	private String createdOn;
	
	/** 
	 * The created by.
	 *  */
	@Field("createdBy.displayName")
	private String createdBy;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id)
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
	 * @param message the new message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public Date getTime()
	{
		return time;
	}
	
	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(Date time)
	{
		this.time = time;
	}
	
	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public String getCreatedOn()
	{
		return createdOn;
	}
	
	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(String createdOn)
	{
		this.createdOn = createdOn;
	}
	
	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
}
