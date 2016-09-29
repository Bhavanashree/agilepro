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
	private StoryEntity story;

	/**
	 * The link.
	 **/
	@Column(name = "LINK_URL")
	private String link;

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
	 * Gets the story.
	 *
	 * @return the story
	 */
	public StoryEntity getStory()
	{
		return story;
	}

	/**
	 * Sets the story.
	 *
	 * @param story
	 *            the new story
	 */
	public void setStory(StoryEntity story)
	{
		this.story = story;
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink()
	{
		return link;
	}

	/**
	 * Sets the link.
	 *
	 * @param link
	 *            the new link
	 */
	public void setLink(String link)
	{
		this.link = link;
	}
}
