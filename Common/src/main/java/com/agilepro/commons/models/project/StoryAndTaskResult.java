package com.agilepro.commons.models.project;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.AbstractExtendedSearchResult;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class StoryAndTaskResult.
 */
@Model
public class StoryAndTaskResult extends AbstractExtendedSearchResult
{

	/**
	 * The id.
	 **/
	@Field(value = "id")
	private long id;

	/**
	 * The title.
	 **/
	@Field(value = "title")
	private String title;

	/**
	 * Instantiates a new story and task result.
	 */
	public StoryAndTaskResult()
	{}

	/**
	 * Instantiates a new story and task result.
	 *
	 * @param title
	 *            the title
	 * @param id
	 *            the id
	 */
	public StoryAndTaskResult(String title, Long id)
	{
		this.title = title;
		this.id = id;
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
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
}
