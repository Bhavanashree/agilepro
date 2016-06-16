package com.restate.commons.models.customer;

import javax.validation.constraints.Pattern;

import com.yukthi.validation.annotations.NotEmpty;

/**
 * The Class ClientPricePlanVariable is responsible for maintaining Price plan
 * Variable attributes..
 */
public class ClientPricePlanVariable
{

	/**
	 * The Client Price plan variable name.
	 */
	@Pattern(regexp = "\\w+", message = "Should be only apha numeric")
	private String name;

	/**
	 * The Client price plan variable label.
	 */
	@NotEmpty
	private String label;

	/**
	 * The default value of the price plan variable.
	 */
	private Double defaultValue;

	/**
	 * The member Flag indicating if this field value can be overridden at
	 * member level.
	 */
	private boolean memberFlag = false;

	/**
	 * Instantiates a new client price plan variable.
	 */
	public ClientPricePlanVariable()
	{}

	/**
	 * Instantiates a new client price plan variable.
	 *
	 * @param name
	 *            the name
	 * @param label
	 *            the label
	 * @param defaultValue
	 *            the default value
	 * @param memberFlag
	 *            the member Flag
	 */
	public ClientPricePlanVariable(String name, String label, Double defaultValue, boolean memberFlag)
	{
		super();
		this.name = name;
		this.label = label;
		this.defaultValue = defaultValue;
		this.memberFlag = memberFlag;
	}

	/**
	 * Gets the Client Price plan variable name.
	 *
	 * @return the Client Price plan variable name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the Client Price plan variable name.
	 *
	 * @param name
	 *            the new Client Price plan variable name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the Client price plan variable label.
	 *
	 * @return the Client price plan variable label
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Sets the Client price plan variable label.
	 *
	 * @param label
	 *            the new Client price plan variable label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Gets the default value of the price plan variable.
	 *
	 * @return the default value of the price plan variable
	 */
	public Double getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * Sets the default value of the price plan variable.
	 *
	 * @param defaultValue
	 *            the new default value of the price plan variable
	 */
	public void setDefaultValue(Double defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	/**
	 * Checks if is member Flag indicating if this field value can be overridden
	 * at member level.
	 *
	 * @return the member Flag indicating if this field value can be overridden
	 *         at member level
	 */
	public boolean getMemberFlag()
	{
		return memberFlag;
	}

	/**
	 * Sets the member Flag.
	 *
	 * @param memberFlag
	 *            the new member Flag indicating if this field value can be
	 *            overridden at member level
	 */
	public void setMemberFlag(boolean memberFlag)
	{
		this.memberFlag = memberFlag;
	}
}
