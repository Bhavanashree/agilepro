package com.agilepro.commons.models.project;

import com.yukthi.persistence.repository.annotations.Field;

/**
 * BackLogPriorityModel is used for fetching the updated stories id and
 * priority after child save.
 * 
 * @author Pritam.
 */
public class BackLogPriorityModel
{
	/**
	 * Id of the story record.
	 */
	@Field("id")
	private Long id;

	/**
	 * Priority of the story.
	 */
	@Field("priority")
	private Integer priority;

	/**
	 * New BackLogPriorityModel with null default.
	 */
	public BackLogPriorityModel()
	{
		super();
	}

	/**
	 * New BacLogPriorityModel with new values.
	 * 
	 * @param id
	 *            new id.
	 * @param priority
	 *            new priority.
	 */
	public BackLogPriorityModel(Long id, Integer priority)
	{
		super();
		this.id = id;
		this.priority = priority;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the priority.
	 * 
	 * @return the priority.
	 */
	public Integer getPriority()
	{
		return priority;
	}

	/**
	 * Sets the priority.
	 * 
	 * @param priority
	 *            the new priority.
	 */
	public void setPriority(Integer priority)
	{
		this.priority = priority;
	}
}
