package com.agilepro.commons.models.admin;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class DesignationSearchQuery.
 * 
 * @author bhavana
 */
@Model
public class DesignationSearchQuery
{

	/** 
	 * The Name of the Designation.
	 **/
	@Condition(value = "name", op = Operator.LIKE, ignoreCase = true)
	private String name;

	/**
	 * Instantiates a new customer search query.
	 */
	public DesignationSearchQuery()
	{
	}
	
	public DesignationSearchQuery(String name)
	{
         	this.name = name;
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
