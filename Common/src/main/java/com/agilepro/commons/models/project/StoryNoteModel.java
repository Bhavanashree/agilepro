package com.agilepro.commons.models.project;

import java.util.Date;

import com.agilepro.commons.StoryNoteStatus;
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

	@Required
	private StoryNoteStatus storyNoteStatus;

	/**
	 * The story id.
	 **/
	@Required
	private Long storyId;

	@Required
	private String versionTitle;

	private String owner;

	private Date updatedOn;
	
	@Required
	private Boolean draftIsSelected;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	public StoryNoteModel()
	{
		super();
	}

	public StoryNoteModel(String content, StoryNoteStatus storyNoteStatus, Long storyId, String versionTitle, String owner)
	{
		this.content = content;
		this.storyNoteStatus = storyNoteStatus;
		this.storyId = storyId;
		this.versionTitle = versionTitle;
		this.owner = owner;
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
	 * Gets the story note status.
	 *
	 * @return the story note status
	 */
	public StoryNoteStatus getStoryNoteStatus()
	{
		return storyNoteStatus;
	}

	/**
	 * Sets the story note status.
	 *
	 * @param storyNoteStatus
	 *            the new story note status
	 */
	public void setStoryNoteStatus(StoryNoteStatus storyNoteStatus)
	{
		this.storyNoteStatus = storyNoteStatus;
	}

	/**
	 * Gets the version title.
	 *
	 * @return the version title
	 */
	public String getVersionTitle()
	{
		return versionTitle;
	}

	/**
	 * Sets the version title.
	 *
	 * @param versionTitle
	 *            the new version title
	 */
	public void setVersionTitle(String versionTitle)
	{
		this.versionTitle = versionTitle;
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
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public String getOwner()
	{
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner
	 *            the new owner
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public Date getUpdatedOn()
	{
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            the new updated on
	 */
	public void setUpdatedOn(Date updatedOn)
	{
		this.updatedOn = updatedOn;
	}

	public Boolean getDraftIsSelected()
	{
		return draftIsSelected;
	}

	public void setDraftIsSelected(Boolean draftIsSelected)
	{
		this.draftIsSelected = draftIsSelected;
	}
}
