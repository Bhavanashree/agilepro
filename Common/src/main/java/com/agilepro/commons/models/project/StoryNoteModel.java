package com.agilepro.commons.models.project;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

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
	
	@Required
	private String versionTitle;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;
	
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

	public String getVersionTitle() {
		return versionTitle;
	}

	public void setVersionTitle(String versionTitle) {
		this.versionTitle = versionTitle;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
