package com.agilepro.commons;

import com.yukthi.persistence.repository.annotations.Field;

/**
 * Backlog model class.
 * 
 * @author Pritam.
 */
public class BacklogModel
{
	/**
	 * Id of the story record.
	 */
	@Field("id")
	private Long id;

	/**
	 * Gets the story id.
	 * 
	 * @return the story id.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the story id.
	 * 
	 * @param id
	 *            the new story id.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
}
