package com.agilepro.commons.models.customer.priceplan;

// TODO: Auto-generated Javadoc
/**
 * The Class Functions.
 */
public class Functions
{

	/**
	 * The name of function.
	 */
	private String name;

	/**
	 * Syntax.
	 */
	private String syntax;

	/**
	 * The description of function.
	 */
	private String description;

	/**
	 * Instantiates a new functions.
	 */
	public Functions()
	{}

	/**
	 * Instantiates a new functions.
	 *
	 * @param name
	 *            the name of function
	 * @param description
	 *            the description of function
	 */
	public Functions(String name, String description)
	{
		super();
		this.name = name;
		this.description = description;
	}

	/**
	 * Instantiates a new functions.
	 *
	 * @param name the name
	 * @param syntax the syntax
	 * @param description the description
	 */
	public Functions(String name, String syntax, String description)
	{
		super();
		this.name = name;
		this.syntax = syntax;
		this.description = description;
	}

	/**
	 * Gets the syntax.
	 *
	 * @return the {@link #syntax syntax}
	 */
	public String getSyntax()
	{
		return syntax;
	}

	/**
	 * Sets the syntax.
	 *
	 * @param syntax            the {@link #syntax syntax} to set
	 */
	public void setSyntax(String syntax)
	{
		this.syntax = syntax;
	}

	/**
	 * Gets the name of function.
	 *
	 * @return the name of function
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of function.
	 *
	 * @param name
	 *            the new name of function
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the description of function.
	 *
	 * @return the description of function
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description of function.
	 *
	 * @param description
	 *            the new description of function
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
