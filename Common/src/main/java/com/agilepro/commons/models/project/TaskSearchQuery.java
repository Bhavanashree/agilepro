package com.agilepro.commons.models.project;

import com.agilepro.commons.TaskStatus;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.AbstractExtendedSearchResult;
import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskSearchQuery.
 */
@Model
public class TaskSearchQuery extends AbstractExtendedSearchResult
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
	 * The status.
	 */
	@NonDisplayable
	@IgnoreField
	@Condition(value = "status")
	private TaskStatus status;

	/**
	 * Instantiates a new backlog search query.
	 */
	public TaskSearchQuery()
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
	public TaskSearchQuery(String title, String description, Integer estimate)
	{
		this.title = title;
		this.description = description;
		this.estimate = estimate;
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
	 * @param title the new title
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
	 * @param description the new description
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
	 * @param estimate the new estimate
	 */
	public void setEstimate(Integer estimate)
	{
		this.estimate = estimate;
	}

	public TaskStatus getStatus()
	{
		return status;
	}

	public void setStatus(TaskStatus status)
	{
		this.status = status;
	}
}
