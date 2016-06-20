package com.agilepro.persistence.entity.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ConversationEntity.
 * @author akanksha
 */
@Table(name = "CONVERSATION_TABLE")
public class ConversationEntity extends WebutilsEntity
{
	/**
	 * The message.
	 */
	@Column(name = "MESSAGE", length = 2000)
	private String message;

	/**
	 * The parent conversation id.
	 */
	@Column(name = "PARENT_CONVERSATION_ID")
	private Long parentConversationId;

	/**
	 * The owner entity type.
	 */
	@Column(name = "OWNER_ENTITY_TYPE", length = 200)
	private String ownerEntityType;

	/**
	 * The owner entity id.
	 */
	@Column(name = "OWNER_ENTITY_ID", nullable = false)
	private Long ownerEntityId = 0L;

	/**
	 * The time.
	 */
	@Column(name = "TIME")
	@DataTypeMapping(type = DataType.DATE_TIME)
	private Date time;

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
	 * @param parentConversationId
	 *            the new parent conversation id
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
	 * @param ownerEntityType
	 *            the new owner entity type
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
	 * @param ownerEntityId
	 *            the new owner entity id
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
	 * @param time
	 *            the new time
	 */
	public void setTime(Date time)
	{
		this.time = time;
	}
}
