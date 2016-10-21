package com.agilepro.commons.models.bug;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class BugSearchQuery.
 */
@Model
public class BugSearchQuery
{

	/**
	 * The name.
	 **/
	@Condition(value = "title", op = Operator.LIKE, ignoreCase = true)
	private String title;

	/**
	 * Instantiates a new bug search query.
	 */
	public BugSearchQuery()
	{}

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
