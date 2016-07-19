package com.agilepro.commons.models.projects;

import com.agilepro.commons.StoryStatus;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.AbstractExtendedSearchResult;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class BacklogSearchResult.
 */
@Model
public class StorySearchResult extends AbstractExtendedSearchResult
{
	/**
	 * Id.
	 */
	@NonDisplayable
	@Field(value = "id")
	private long id;

	/**
	 * Name of the customer.
	 */
	@Field(value = "title")
	private String title;

	/**
	 * Email id of the customer.
	 */
	@Field(value = "description")
	private String description;

	/**
	 * Phone number of the customer.
	 */
	@Field(value = "estimate")
	private Integer estimate;

	/**
	 * The parent story id.
	 */
	@Field(value = "parentStoryId")
	private Long parentStoryId;

	/**
	 * The story owner.
	 */
	@NonDisplayable
	@Field(value = "owner.name")
	private String storyOwner;

	/**
	 * The indent.
	 */
	private int indent = 0;

	/**
	 *  The status. 
	 */
	@Field(value = " status")
	private StoryStatus status;

	/**
	 * Gets the story owner.
	 *
	 * @return the story owner
	 */
	public String getStoryOwner()
	{
		return storyOwner;
	}

	/**
	 * Sets the story owner.
	 *
	 * @param storyOwner
	 *            the new story owner
	 */
	public void setStoryOwner(String storyOwner)
	{
		this.storyOwner = storyOwner;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(long id)
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
	 * Sets the name.
	 *
	 * @param title
	 *            the new name
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
	 * Gets the estimate.
	 *
	 * @return the estimate
	 */
	public Integer getEstimate()
	{
		return estimate;
	}

	/**
	 * Sets the estimate.
	 *
	 * @param estimate
	 *            the new estimate
	 */
	public void setEstimate(Integer estimate)
	{
		this.estimate = estimate;
	}

	/**
	 * Gets the parent story id.
	 *
	 * @return the parent story id
	 */
	public Long getParentStoryId()
	{
		return parentStoryId;
	}

	/**
	 * Sets the parent story id.
	 *
	 * @param parentStoryId
	 *            the new parent story id
	 */
	public void setParentStoryId(Long parentStoryId)
	{
		this.parentStoryId = parentStoryId;
	}

	/**
	 * Gets the indent.
	 *
	 * @return the indent
	 */
	public int getIndent()
	{
		return indent;
	}

	/**
	 * Sets the indent.
	 *
	 * @param indent
	 *            the new indent
	 */
	public void setIndent(int indent)
	{
		this.indent = indent;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public StoryStatus getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(StoryStatus status)
	{
		this.status = status;
	}
}
