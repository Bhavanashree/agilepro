package com.agilepro.commons.models.customer.priceplan;

/**
 * Variable and value details used in invoice.
 * 
 * @author akiran
 */
public class VariableValueDetails
{
	/**
	 * Name of the variable.
	 */
	private String name;

	/**
	 * Label of the variable.
	 */
	private String label;

	/**
	 * Variable value used in invoice.
	 */
	private Double value;

	/**
	 * Instantiates a new variable value details.
	 */
	public VariableValueDetails()
	{}

	/**
	 * Instantiates a new variable value details.
	 *
	 * @param name
	 *            the name
	 * @param label
	 *            the label
	 * @param value
	 *            the value
	 */
	public VariableValueDetails(String name, String label, Double value)
	{
		this.name = name;
		this.label = label;
		this.value = value;
	}

	/**
	 * Gets the name of the variable.
	 *
	 * @return the name of the variable
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the variable.
	 *
	 * @param name
	 *            the new name of the variable
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the label of the variable.
	 *
	 * @return the label of the variable
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Sets the label of the variable.
	 *
	 * @param label
	 *            the new label of the variable
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Gets the variable value used in invoice.
	 *
	 * @return the variable value used in invoice
	 */
	public Double getValue()
	{
		return value;
	}

	/**
	 * Sets the variable value used in invoice.
	 *
	 * @param value
	 *            the new variable value used in invoice
	 */
	public void setValue(Double value)
	{
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String comma = ",";

		StringBuilder builder = new StringBuilder(super.toString());
		builder.append("[");

		builder.append("Name: ").append(name);
		builder.append(comma).append("Label: ").append(label);
		builder.append(comma).append("Value: ").append(value);

		builder.append("]");
		return builder.toString();
	}
}
