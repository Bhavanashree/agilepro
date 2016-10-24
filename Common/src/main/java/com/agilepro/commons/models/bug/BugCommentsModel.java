package com.agilepro.commons.models.bug;

import java.util.Date;

import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class BugCommentModel.
 */
@ExtendableModel(name = "BugCommentModel")
@Model
public class BugCommentsModel extends AbstractExtendableModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;
	
	/** 
	 * The created on.
	 **/
	private Date createdOn;

	/**
	 * The version.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * The content.
	 **/
	private String content;

	/**
	 * The commented by.
	 **/
	private String commentedBy;

	/**
	 * The bug id.
	 **/
	private Long bugId;
	
	/**
	 * The comment status.
	 **/
	private String commentStatus;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.common.IExtendableModel#getId()
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
	 * Gets the commented by.
	 *
	 * @return the commented by
	 */
	public String getCommentedBy()
	{
		return commentedBy;
	}

	/**
	 * Sets the commented by.
	 *
	 * @param commentedBy
	 *            the new commented by
	 */
	public void setCommentedBy(String commentedBy)
	{
		this.commentedBy = commentedBy;
	}

	/**
	 * Gets the bug id.
	 *
	 * @return the bug id
	 */
	public Long getBugId()
	{
		return bugId;
	}

	/**
	 * Sets the bug id.
	 *
	 * @param bugId
	 *            the new bug id
	 */
	public void setBugId(Long bugId)
	{
		this.bugId = bugId;
	}

	/**
	 * Gets the comment status.
	 *
	 * @return the comment status
	 */
	public String getCommentStatus()
	{
		return commentStatus;
	}

	/**
	 * Sets the comment status.
	 *
	 * @param commentStatus
	 *            the new comment status
	 */
	public void setCommentStatus(String commentStatus)
	{
		this.commentStatus = commentStatus;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Date getCreatedOn()
	{
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn
	 *            the new created on
	 */
	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}
}
