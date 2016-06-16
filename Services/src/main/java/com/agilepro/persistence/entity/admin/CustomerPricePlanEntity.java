package com.agilepro.persistence.entity.admin;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.agilepro.commons.PaymentCycle;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanVariable;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class Customer Price Plan Entity maintains price plan for customer.
 * 
 * @author Neha
 */
@Table(name = "CUSTOMER_PRICE_PLAN")
public class CustomerPricePlanEntity extends WebutilsEntity
{
	/**
	 * Customer price plan name.
	 */
	@UniqueConstraint(name = "NAME")
	@Column(name = "NAME", length = 50)
	private String name;

	/**
	 * Customer price plan description.
	 */
	@Column(name = "DESCRIPTION", length = 1000)
	private String description;

	/**
	 * List of customer price plan variable.
	 */
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	@Column(name = "VARIABLES", length = 1000)
	private List<CustomerPricePlanVariable> numericVariables;

	/**
	 * List of Customer price plan expressions.
	 */
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	@Column(name = "EXPRESSIONS", length = 1000)
	private List<CustomerPricePlanExpression> expressions;

	/**
	 * Customer price plan payment cycle.
	 */
	@DataTypeMapping(type = DataType.STRING)
	@Column(name = "PAYMENT_CYCLE", length = 20)
	private PaymentCycle paymentCycle;

	/**
	 * Instantiates a new customer price plan entity.
	 */
	public CustomerPricePlanEntity()
	{}

	/**
	 * Instantiates a new customer price plan entity.
	 *
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @param variables
	 *            the variables
	 * @param expressions
	 *            the expressions
	 * @param paymentCycle
	 *            the payment cycle
	 */
	public CustomerPricePlanEntity(Long id, String name, String description, List<CustomerPricePlanVariable> variables, List<CustomerPricePlanExpression> expressions, PaymentCycle paymentCycle)
	{
		super();
		this.name = name;
		this.description = description;
		this.numericVariables = variables;
		this.expressions = expressions;
		this.paymentCycle = paymentCycle;
	}

	/**
	 * Gets the customer price plan name.
	 *
	 * @return the customer price plan name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the customer price plan name.
	 *
	 * @param name
	 *            the new customer price plan name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the customer price plan description.
	 *
	 * @return the customer price plan description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the customer price plan description.
	 *
	 * @param description
	 *            the new customer price plan description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Gets the list of customer price plan variable.
	 *
	 * @return the list of customer price plan variable
	 */
	public List<CustomerPricePlanVariable> getNumericVariables()
	{
		return numericVariables;
	}

	/**
	 * Sets the list of customer price plan variable.
	 *
	 * @param variables
	 *            the new list of customer price plan variable
	 */
	public void setNumericVariables(List<CustomerPricePlanVariable> variables)
	{
		this.numericVariables = variables;
	}

	/**
	 * Gets the list of Customer price plan expressions.
	 *
	 * @return the list of Customer price plan expressions
	 */
	public List<CustomerPricePlanExpression> getExpressions()
	{
		return expressions;
	}

	/**
	 * Sets the list of Customer price plan expressions.
	 *
	 * @param expressions
	 *            the new list of Customer price plan expressions
	 */
	public void setExpressions(List<CustomerPricePlanExpression> expressions)
	{
		this.expressions = expressions;
	}

	/**
	 * Gets the customer price plan payment cycle.
	 *
	 * @return the customer price plan payment cycle
	 */
	public PaymentCycle getPaymentCycle()
	{
		return paymentCycle;
	}

	/**
	 * Sets the customer price plan payment cycle.
	 *
	 * @param paymentCycle
	 *            the new customer price plan payment cycle
	 */
	public void setPaymentCycle(PaymentCycle paymentCycle)
	{
		this.paymentCycle = paymentCycle;
	}
}
