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
	 *  The name.
	 **/
	@Condition(value = "name", op = Operator.LIKE, ignoreCase = true)
	private String name;

	/**
	 * Instantiates a new bug search query.
	 */
	public BugSearchQuery()
	{}

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
