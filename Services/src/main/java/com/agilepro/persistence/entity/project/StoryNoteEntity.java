package com.agilepro.persistence.entity.project;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.project.StoryNoteModel;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class StoryNoteEntity.
 */
@Table(name = "STORY_NOTE")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_NAME", fields = { "spaceIdentity", "story", "published", "versionTitle"}) })
public class StoryNoteEntity extends WebutilsEntity
{
	/**
	 * The story.
	 **/
	@Column(name = "STORY_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = StoryNoteModel.class, from = "storyId", subproperty = "id")
	private StoryEntity story;

	/**
	 * The note.
	 **/
	@Column(name = "CONTENT", length = 1000)
	private String content;

	/**
	 * The is published.
	 **/
	@Column(name = "PUBLISHED")
	private Boolean published;
	
	@Column(name = "VERSION_TITLE")
	private String versionTitle;
	
	@Column(name = "OWNER")
	private String owner;

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
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content
	 *            the new content
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * Gets the published.
	 *
	 * @return the published
	 */
	public Boolean getPublished()
	{
		return published;
	}

	/**
	 * Sets the published.
	 *
	 * @param published
	 *            the new published
	 */
	public void setPublished(Boolean published)
	{
		this.published = published;
	}

	public String getVersionTitle() {
		return versionTitle;
	}

	public void setVersionTitle(String versionTitle) {
		this.versionTitle = versionTitle;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
