package com.agilepro.commons.models.customer.priceplan;

/**
 * The Class RuntimeVariables for Customer price plan.
 */
public class RuntimeVariables
{

	/**
	 * The name of runtime variable.
	 */
	private String name;

	/**
	 * The description of runtime variable.
	 */
	private String description;

	/**
	 * Instantiates a new runtime variables.
	 */
	public RuntimeVariables()
	{
	}

	/**
	 * Instantiates a new runtime variables.
	 *
	 * @param name
	 *            the name of runtime variable
	 * @param description
	 *            the description of runtime variable
	 */
	public RuntimeVariables(String name, String description)
	{
		super();
		this.name = name;
		this.description = description;
	}

	/**
	 * Gets the name of runtime variable.
	 *
	 * @return the name of runtime variable
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of runtime variable.
	 *
	 * @param name
	 *            the new name of runtime variable
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the The description of runtime variable.
	 *
	 * @return the The description of runtime variable
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the The description of runtime variable.
	 *
	 * @param description
	 *            the new The description of runtime variable
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
