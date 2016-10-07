package com.agilepro.commons.models.project;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class StoryNoteModel.
 * 
 * @author Pritam
 */
@Model(name = "StoryNote")
public class StoryNoteModel
{
	/**
	 * The id.
	 */
	private Long id;

	/**
	 * The note.
	 **/
	@Required
	private String content;

	/**
	 * The published.
	 **/
	private Boolean published;

	/**
	 * The story id.
	 **/
	@Required
	private Long storyId;

	/**
	 * The version.
	 **/
	private Long version;

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
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Long getVersion()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Long version)
	{
		this.version = version;
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
	 * Gets the story id.
	 *
	 * @return the story id
	 */
	public Long getStoryId()
	{
		return storyId;
	}

	/**
	 * Sets the story id.
	 *
	 * @param storyId
	 *            the new story id
	 */
	public void setStoryId(Long storyId)
	{
		this.storyId = storyId;
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
}
