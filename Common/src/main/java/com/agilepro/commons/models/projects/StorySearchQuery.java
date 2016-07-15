package com.agilepro.commons.models.projects;

import com.agilepro.commons.StoryStatus;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class StorySearchQuery.
 */
@Model
public class StorySearchQuery
{
	
	/** 
	 * The title. 
	 **/
	@Condition(value = "title", op = Operator.LIKE, ignoreCase = true)
	private String title;

	/**
	 * backlog description.
	 */
	@NonDisplayable
	@Condition(value = "description", op = Operator.LIKE, ignoreCase = true)
	private String description;

	/**
	 * The estimate.
	 **/
	@NonDisplayable
	@Condition(value = "estimate", op = Operator.LIKE, ignoreCase = true)
	private Integer estimate;

	/** 
	 * The parent story id.
	 **/
	@NonDisplayable
	@Condition(value = "parentStoryId", op = Operator.EQ)
	@LOV(name = "parentStoryId")
	private Long parentStoryId;

	/** 
	 * The status. 
	 */
	@NonDisplayable
	@Condition(value = "estimate")
	private StoryStatus status;
	
	/**
	 * Instantiates a new backlog search query.
	 */
	public StorySearchQuery()
	{}

	/**
	 * Instantiates a new backlog search query.
	 *
	 * @param title
	 *            the title
	 * @param description
	 *            the description
	 * @param estimate
	 *            the estimate
	 */
	public StorySearchQuery(String title, String description, Integer estimate)
	{
		this.title = title;
		this.description = description;
		this.estimate = estimate;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Sets the name.
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
