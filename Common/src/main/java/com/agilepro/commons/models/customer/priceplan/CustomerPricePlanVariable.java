package com.agilepro.commons.models.customer.priceplan;

import javax.validation.constraints.Pattern;

import com.yukthi.validation.annotations.NotEmpty;

/**
 * CustomerPricePlanVariable is responsible for maintaining Price plan Variable
 * attributes.
 */
public class CustomerPricePlanVariable implements Comparable<CustomerPricePlanVariable>
{

	/**
	 * Customer price plan variable name.
	 */
	@Pattern(regexp = "\\w+")
	private String name;

	/**
	 * Customer price plan variable label.
	 */
	@NotEmpty
	private String label;

	/**
	 * Customer price plan variable default value.
	 */
	private Double defaultValue;

	/**
	 * Instantiates a new customer price plan variable.
	 */
	public CustomerPricePlanVariable()
	{}

	/**
	 * Instantiates a new customer price plan variable.
	 *
	 * @param name
	 *            the name
	 * @param label
	 *            the label
	 * @param defaultValue
	 *            the default value
	 */
	public CustomerPricePlanVariable(String name, String label, Double defaultValue)
	{
		super();
		this.name = name;
		this.label = label;
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the Customer price plan variable name.
	 *
	 * @return the Customer price plan variable name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the Customer price plan variable name.
	 *
	 * @param name
	 *            the new Customer price plan variable name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the Customer price plan variable label.
	 *
	 * @return the Customer price plan variable label
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Sets the Customer price plan variable label.
	 *
	 * @param label
	 *            the new Customer price plan variable label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Gets the Customer price plan variable default value.
	 *
	 * @return the Customer price plan variable default value
	 */
	public Double getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * Sets the Customer price plan variable default value.
	 *
	 * @param defaultValue
	 *            the new Customer price plan variable default value
	 */
	public void setDefaultValue(Double defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	@Override
	public int compareTo(CustomerPricePlanVariable var)
	{
		return name.compareTo(var.name);
	}
}
