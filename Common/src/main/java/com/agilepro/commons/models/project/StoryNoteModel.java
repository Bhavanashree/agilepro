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
	 * The note(description) for the note.
	 **/
	@Required
	private String content;

	/**
	 * Story Note status.
	 */
	@Required
	private StoryNoteStatus storyNoteStatus;

	/**
	 * The story id.
	 **/
	@Required
	private Long storyId;

	/**
	 * Version title for the note.
	 */
	@Required
	private String versionTitle;

	/**
	 * Update on.
	 */
	private Date updatedOn;
	
	/**
	 * Employee id for mapping.
	 */
	private Long employeeId;
	
	/**
	 * Owner of the story note.
	 */
	private String owner;
	
	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	public StoryNoteModel()
	{
		super();
	}

	/**
	 * Creates new story note model object with provided values.
	 * 
	 * @param content new content.
	 * @param storyNoteStatus new storyNoteStatus.
	 * @param storyId new storyId.
	 * @param versionTitle new versionTitle.
	 * @param employeeId new employeeId.
	 */
	public StoryNoteModel(String content, StoryNoteStatus storyNoteStatus, Long storyId, String versionTitle, Long employeeId)
	{
		this.content = content;
		this.storyNoteStatus = storyNoteStatus;
		this.storyId = storyId;
		this.versionTitle = versionTitle;
		this.employeeId = employeeId;
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
	 * Gets the employee id.
	 * 
	 * @return the employee id.
	 */
	public Long getEmployeeId()
	{
		return employeeId;
	}

	/**
	 * Sets the employee id.
	 * 
	 * @param employeeId the new employee id.
	 */
	public void setEmployeeId(Long employeeId)
	{
		this.employeeId = employeeId;
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

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}
}
