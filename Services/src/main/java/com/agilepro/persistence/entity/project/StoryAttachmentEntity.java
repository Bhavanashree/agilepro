package com.agilepro.persistence.entity.project;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.project.StoryAttachmentModel;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class StoryAttachmentEntity.
 * 
 * @author Pritam
 */
@Table(name = "STORY_ATTACHMENT")
public class StoryAttachmentEntity extends WebutilsEntity
{
	/**
	 * The title.
	 **/
	@Column(name = "TITLE")
	private String title;

	/**
	 * The description.
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The story entity.
	 **/
	@Column(name = "STORY_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = StoryAttachmentModel.class, from = "storyId", subproperty = "id")
	private StoryEntity storyEntity;

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description)
	{
		this.description = description;
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
}
