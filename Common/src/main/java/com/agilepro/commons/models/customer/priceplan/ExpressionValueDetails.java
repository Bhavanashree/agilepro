package com.agilepro.commons.models.customer.priceplan;

/**
 * Expression and value details used in invoice.
 * 
 * @author akiran
 */
public class ExpressionValueDetails
{
	/**
	 * Name of the expression.
	 */
	private String name;

	/**
	 * Label of the expression.
	 */
	private String label;

	/**
	 * Expression value used in invoice.
	 */
	private Double value;

	/**
	 * Instantiates a new variable value details.
	 */
	public ExpressionValueDetails()
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
	public ExpressionValueDetails(String name, String label, Double value)
	{
		this.name = name;
		this.label = label;
		this.value = value;
	}

	/**
	 * Gets the name of the expression.
	 *
	 * @return the name of the expression
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the expression.
	 *
	 * @param name
	 *            the new name of the expression
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the label of the expression.
	 *
	 * @return the label of the expression
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Sets the label of the expression.
	 *
	 * @param label
	 *            the new label of the expression
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Gets the expression value used in invoice.
	 *
	 * @return the expression value used in invoice
	 */
	public Double getValue()
	{
		return value;
	}

	/**
	 * Sets the expression value used in invoice.
	 *
	 * @param value
	 *            the new expression value used in invoice
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
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append("[");

		builder.append("Name: ").append(name);
		builder.append(" ").append("Label: ").append(label);
		builder.append(",").append("Value: ").append(value);

		builder.append("]");
		return builder.toString();
	}
}
