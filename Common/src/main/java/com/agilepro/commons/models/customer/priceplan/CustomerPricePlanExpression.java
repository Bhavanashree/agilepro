package com.agilepro.commons.models.customer.priceplan;

import javax.validation.constraints.Pattern;

import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.webutils.common.annotations.Model;

/**
 * CustomerPricePlanExpression is responsible for maintaining Price plan
 * Expression attributes.
 * 
 */
@Model(name = "PricePlanExpression")
public class CustomerPricePlanExpression
{

	/**
	 * The name of Customer Price Plan Expression.
	 **/
	@Pattern(regexp = "\\w+")
	private String name;

	/**
	 * The label of Customer Price Plan Expression.
	 */
	@NotEmpty
	private String label;

	/**
	 * The Customer Price Plan expression.
	 */
	private String expression;

	/**
	 * Instantiates a new customer price plan expression.
	 */
	public CustomerPricePlanExpression()
	{}

	/**
	 * Instantiates a new customer price plan expression.
	 *
	 * @param name
	 *            the name
	 * @param label
	 *            the label
	 * @param expression
	 *            the expression
	 */
	public CustomerPricePlanExpression(String name, String label, String expression)
	{
		this.name = name;
		this.label = label;
		this.expression = expression;
	}

	/**
	 * Gets the The name of Customer Price Plan Expression.
	 *
	 * @return the The name of Customer Price Plan Expression
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the The name of Customer Price Plan Expression.
	 *
	 * @param name
	 *            the new The name of Customer Price Plan Expression
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the The label of Customer Price Plan Expression.
	 *
	 * @return the The label of Customer Price Plan Expression
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Sets the The label of Customer Price Plan Expression.
	 *
	 * @param label
	 *            the new The label of Customer Price Plan Expression
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Gets the Customer Price Plan expression.
	 *
	 * @return the Customer Price Plan expression
	 */
	public String getExpression()
	{
		return expression;
	}

	/**
	 * Sets the Customer Price Plan expression.
	 *
	 * @param expression
	 *            the new Customer Price Plan expression
	 */
	public void setExpression(String expression)
	{
		this.expression = expression;
	}
}
