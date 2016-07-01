package com.agilepro.commons.models.projects;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.AbstractExtendedSearchResult;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class BacklogSearchResult.
 */
@Model
public class BacklogSearchResult extends AbstractExtendedSearchResult
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
}
