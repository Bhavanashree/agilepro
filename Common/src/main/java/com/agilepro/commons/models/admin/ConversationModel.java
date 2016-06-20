package com.agilepro.commons.models.admin;

import java.util.Date;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ConversationModel.
 */
@Model
public class ConversationModel
{
	
	/** 
	 * The id.
	 *  */
	@NonDisplayable
	private Long id;
	
	/** 
	 * The message.
	 *  */
	@MinLen(3)
	@MaxLen(1000)
	private String message;
	
	/**
	 *  The owner entity type.
	 *   */
	@MinLen(3)
	@MaxLen(1000)
	private String ownerEntityType;
	
	/**
	 *  The owner entity id.
	 *   */
	private Long ownerEntityId;
	
	/**
	 *  The time.
	 *   */
	private Date time;
	
	/**
	 *  The version. 
	 *  */
	@NonDisplayable
	private Integer version;
	
	/**
	 * Instantiates a new conversation model.
	 */
	public ConversationModel()
	{
	}

	/**
	 * Instantiates a new conversation model.
	 *
	 * @param message the message
	 * @param ownerEntityType the owner entity type
	 * @param ownerEntityId the owner entity id
	 * @param time the time
	 */
	public ConversationModel(String message, String ownerEntityType, Long ownerEntityId, Date time)
	{
		this.message = message;
		this.ownerEntityType = ownerEntityType;
		this.ownerEntityId = ownerEntityId;
		this.time = time;
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
	 * @param id the new id
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
	 * @param message the new message
	 */
	public void setMessage(String message)
	{
		this.message = message;
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
	 * @param version the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}	
}
