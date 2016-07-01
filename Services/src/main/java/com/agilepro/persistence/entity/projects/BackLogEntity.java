package com.agilepro.persistence.entity.projects;

import javax.persistence.Column;
import javax.persistence.Table;

import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * Backlog entity.
 */
@ExtendableEntity(name = "Backlog")
@Table(name = "BACKLOG")
public class BackLogEntity extends WebutilsExtendableEntity
{
	/**
	 * Backlog Name.
	 */
	@Column(name = "TITLE", length = 50, nullable = false)
	private String title;

	/**
	 * Backlog description.
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The estimate.
	 **/
	@Column(name = "ESTIMATE")
	private Integer estimate;

	/**
	 * The parent story id. 
	 **/
	@Column(name = "PARENTSTORYID")
	private Long parentStoryId;

	/**
	 * Instantiates a new back log entity.
	 */
	public BackLogEntity()
	{}

	/**
	 * Instantiates a new back log entity.
	 *
	 * @param title
	 *            the title
	 * @param estimate
	 *            the estimate
	 * @param description
	 *            the description
	 */
	public BackLogEntity(String title, Integer estimate, String description)
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

	public Long getParentStoryId()
	{
		return parentStoryId;
	}

	public void setParentStoryId(Long parentStoryId)
	{
		this.parentStoryId = parentStoryId;
	}
	
}
