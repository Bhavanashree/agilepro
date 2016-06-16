package com.agilepro.commons.models.admin;

import java.util.Date;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class ConversationSearchQuery.
 */
@Model
public class ConversationSearchQuery
{
	
	/**
	 *  The message. 
	 *  */
	@Condition(value = "message", op = Operator.LIKE, ignoreCase = true)
	private String message;
	
	/** 
	 * The parent conversation id. */
	@Condition(value = "parentConversationId")
	private Long parentConversationId;
	
	/**
	 *  The owner entity type.
	 *   */
	@Condition(value = "ownerEntityType", op = Operator.LIKE, ignoreCase = true)
	private String ownerEntityType;
	
	/**
	 *  The owner entity id.
	 *   */
	@Condition(value = "ownerEntityId")
	private Long ownerEntityId = 0L;
	
	/**
	 *  The time.
	 *   */
	@Condition(value = "time")
	private Date time;
	
	/**
	 * Instantiates a new conversation search query.
	 */
	public ConversationSearchQuery()
	{
	}
	
	/**
	 * Instantiates a new conversation search query.
	 *
	 * @param message the message
	 * @param ownerEntityType the owner entity type
	 * @param ownerEntityId the owner entity id
	 * @param time the time
	 */
	public ConversationSearchQuery(String message, String ownerEntityType, Long ownerEntityId, Date time)
	{
		this.message = message;
		this.ownerEntityType = ownerEntityType;
		this.ownerEntityId = ownerEntityId;
		this.time = time;
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
	 * Gets the parent conversation id.
	 *
	 * @return the parent conversation id
	 */
	public Long getParentConversationId()
	{
		return parentConversationId;
	}

	/**
	 * Sets the parent conversation id.
	 *
	 * @param parentConversationId the new parent conversation id
	 */
	public void setParentConversationId(Long parentConversationId)
	{
		this.parentConversationId = parentConversationId;
	}

	/**
	 * Gets the owner entity type.
	 *
	 * @return the owner entity type
	 */
	public String getOwnerEntityType()
	{
		return ownerEntityType;
	}

	/**
	 * Sets the owner entity type.
	 *
	 * @param ownerEntityType the new owner entity type
	 */
	public void setOwnerEntityType(String ownerEntityType)
	{
		this.ownerEntityType = ownerEntityType;
	}

	/**
	 * Gets the owner entity id.
	 *
	 * @return the owner entity id
	 */
	public Long getOwnerEntityId()
	{
		return ownerEntityId;
	}

	/**
	 * Sets the owner entity id.
	 *
	 * @param ownerEntityId the new owner entity id
	 */
	public void setOwnerEntityId(Long ownerEntityId)
	{
		this.ownerEntityId = ownerEntityId;
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
}
