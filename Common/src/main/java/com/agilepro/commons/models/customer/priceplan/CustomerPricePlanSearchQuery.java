package com.agilepro.commons.models.customer.priceplan;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.Model;

/**
 * Customer price plan search query.
 */

@Model
public class CustomerPricePlanSearchQuery
{
	/** 
	 * Price Plan search name pattern. 
	 */
	@Condition(value = "name", op = Operator.LIKE, ignoreCase = true)
	private String name;

	/** 
	 * Price plan search description pattern. 
	 */
	@Condition(value = "description", op = Operator.LIKE, ignoreCase = true)
	private String description;

	/**
	 * Instantiates a new price plan search query.
	 */
	public CustomerPricePlanSearchQuery()
	{}

	/**
	 * Instantiates a new price plan search query.
	 *
	 * @param name
	 *            Customer price plan name
	 */
	public CustomerPricePlanSearchQuery(String name)
	{
		this.name = name;
	}

	/**
	 * Instantiates a new customer price plan search query.
	 *
	 * @param name
	 *            customer price plan name
	 * @param description
	 *            Customer price plan description
	 */
	public CustomerPricePlanSearchQuery(String name, String description)
	{
		this.name = name;
		this.description = description;
	}

	/**
	 * Gets the price plan search name pattern.
	 *
	 * @return the price plan search name pattern
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the price plan search name pattern.
	 *
	 * @param name
	 *            the new price plan search name pattern
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the price plan description.
	 *
	 * @return the customer price plan description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new price plan search description pattern
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
