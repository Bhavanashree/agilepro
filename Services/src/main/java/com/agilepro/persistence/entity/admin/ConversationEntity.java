package com.agilepro.persistence.entity.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.admin.ConversationModel;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ConversationEntity. Maintains and tracks the conversation between
 * the members.
 * 
 * @author Pritam
 */
@Table(name = "CONVERSATION")
public class ConversationEntity extends WebutilsEntity
{
	/**
	 * The story entity.
	 **/
	@Column(name = "STORY_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = ConversationModel.class, from = "storyId", subproperty = "id")
	private StoryEntity storyEntity;

	/**
	 * The message.
	 */
	@Column(name = "MESSAGE", length = 2000)
	private String message;

	/** 
	 * The user entity. 
	 **/
	@Column(name = "USER_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = ConversationModel.class, from = "userId", subproperty = "id")
	private UserEntity userEntity;
	
	/** 
	 * The time. 
	 **/
	@Column(name = "DATE", nullable = false)
	private Date date;
	
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
	 * Gets the story entity.
	 *
	 * @return the story entity
	 */
	public StoryEntity getStoryEntity()
	{
		return storyEntity;
	}

	/**
	 * Sets the story entity.
	 *
	 * @param storyEntity
	 *            the new story entity
	 */
	public void setStoryEntity(StoryEntity storyEntity)
	{
		this.storyEntity = storyEntity;
	}

	public UserEntity getUserEntity()
	{
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity)
	{
		this.userEntity = userEntity;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
}
