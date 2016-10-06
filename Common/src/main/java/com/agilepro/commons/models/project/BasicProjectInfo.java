package com.agilepro.commons.models.project;

import com.yukthi.persistence.repository.annotations.Field;

/**
 * The Class BasicProjectInfo.
 * 
 * @author Pritam
 */
public class BasicProjectInfo
{
	/**
	 * The id.
	 **/
	@Field("project.id")
	private Long id;

	/**
	 * The name.
	 **/
	@Field("project.name")
	private String name;

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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
