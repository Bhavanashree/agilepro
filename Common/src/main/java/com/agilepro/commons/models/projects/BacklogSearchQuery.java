package com.agilepro.commons.models.projects;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class BacklogSearchQuery.
 */
@Model
public class BacklogSearchQuery
{
	/**
	 * Backlog search title.
	 */
	@Condition(value = "title", op = Operator.LIKE, ignoreCase = true)
	private String title;

	/**
	 * backlog description.
	 */
	@Condition(value = "description", op = Operator.LIKE, ignoreCase = true)
	private String description;

	/**
	 *  The estimate. 
	 **/
	@Condition(value = "estimate", op = Operator.LIKE, ignoreCase = true)
	private Integer estimate;

	/**
	 * Instantiates a new backlog search query.
	 */
	public BacklogSearchQuery()
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
	public BacklogSearchQuery(String title, String description, Integer estimate)
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
	 * @param name
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
}
