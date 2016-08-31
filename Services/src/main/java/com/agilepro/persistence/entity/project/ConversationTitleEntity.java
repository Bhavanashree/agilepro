package com.agilepro.persistence.entity.project;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.project.ConversationTitleModel;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ConversationTitleEntity.
 * 
 * @author Pritam
 */
@Table(name = "CONVERSATION_TITLE")
public class ConversationTitleEntity extends WebutilsEntity
{
	/** 
	 * The title. 
	 **/
	@Column(name = "NAME", length = 2000)
	private String name;

	/**
	 * The story entity.
	 **/
	@Column(name = "STORY_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = ConversationTitleModel.class, from = "storyId", subproperty = "id")
	private StoryEntity storyEntity;
	
	/** 
	 * The user entity. 
	 **/
	@Column(name = "OWNER_ID")
	@ManyToOne
	@PropertyMapping(type = ConversationTitleModel.class, from = "ownerId", subproperty = "id")
	private UserEntity userEntity;
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public StoryEntity getStoryEntity()
	{
		return storyEntity;
	}

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
}
