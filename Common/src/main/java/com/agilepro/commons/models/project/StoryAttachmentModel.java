package com.agilepro.commons.models.project;

import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;
import com.yukthi.webutils.common.FileInfo;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;

import javax.validation.constraints.Pattern;

import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.Required;

/**
 * The Class StoryAttachmentModel.
 * 
 * @author Pritam
 */
@Model
public class StoryAttachmentModel
{
	/**
	 * The id.
	 */
	@NonDisplayable
	private Long id;

	/**
	 * The title.
	 **/
	@Required
	@MinLen(5)
	@MaxLen(20)
	private String title;

	/**
	 * The file.
	 **/
	private FileInfo file;

	/**
	 * The link.
	 **/
	@MaxLen(1000)
	@Pattern(regexp = IWebUtilsCommonConstants.PATTERN_URL_LINK, message = "Invalid url specified")
	private String link;
	
	/** 
	 * The link for display. 
	 **/
	@NonDisplayable
	private String linkForDisplay;

	/**
	 * The description.
	 **/
	@MultilineText
	private String description;

	/**
	 * The story id.
	 **/
	@NonDisplayable
	@Required
	private Long storyId;

	/**
	 * The version.
	 */
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
	 * Gets the file.
	 *
	 * @return the file
	 */
	public FileInfo getFile()
	{
		return file;
	}

	/**
	 * Sets the file.
	 *
	 * @param file
	 *            the new file
	 */
	public void setFile(FileInfo file)
	{
		this.file = file;
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
	 * Gets the link for display.
	 *
	 * @return the link for display
	 */
	public String getLinkForDisplay()
	{
		return linkForDisplay;
	}

	/**
	 * Sets the link for display.
	 *
	 * @param linkForDisplay the new link for display
	 */
	public void setLinkForDisplay(String linkForDisplay)
	{
		this.linkForDisplay = linkForDisplay;
	}
}
