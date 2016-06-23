package com.agilepro.commons.models.customer;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class TagsSearchResult.
 * 
 * @author Pritam
 */
@Model
public class TagsSearchResult
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	@Field(value = "id")
	private Long id;

	/**
	 * The name.
	 **/
	@Field(value = "name")
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
