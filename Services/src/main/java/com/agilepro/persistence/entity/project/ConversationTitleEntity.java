package com.agilepro.persistence.entity.project;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.project.ConversationTitleModel;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ConversationTitleEntity.
 * 
 * @author Pritam
 */
@Table(name = "CONVERSATION_TITLE")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_NAME", fields = { "spaceIdentity", "name" }) })
public class ConversationTitleEntity extends WebutilsEntity
{
	/**
	 * The title.
	 **/
	@Column(name = "NAME", length = 50)
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

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name)
	{
		this.name = name;
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

	/**
	 * Gets the user entity.
	 *
	 * @return the user entity
	 */
	public UserEntity getUserEntity()
	{
		return userEntity;
	}

	/**
	 * Sets the user entity.
	 *
	 * @param userEntity
	 *            the new user entity
	 */
	public void setUserEntity(UserEntity userEntity)
	{
		this.userEntity = userEntity;
	}
}
