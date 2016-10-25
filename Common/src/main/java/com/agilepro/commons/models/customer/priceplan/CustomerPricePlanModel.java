package com.agilepro.commons.models.customer.priceplan;

import java.util.List;

import javax.validation.Valid;

import com.agilepro.commons.PaymentCycle;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * Represents customer price plan model.
 * 
 * @author Pritam
 */
@Model(name = "PricePlanModel")
public class CustomerPricePlanModel
{
	/**
	 * Customer price plan id.
	 */
	@NonDisplayable
	private long id;

	/** 
	 * The version. 
	 */
	@NonDisplayable
	private Integer version;

	/**
	 * Customer price plan name.
	 */
	@NotEmpty
	@MinLen(3)
	@MaxLen(50)
	private String name;

	/**
	 * Customer price plan description.
	 */
	@MultilineText
	@MaxLen(1000)
	private String description;

	/**
	 * List of customer price plan numeric variable.
	 */
	@IgnoreField
	@Valid
	private List<CustomerPricePlanVariable> numericVariables;

	/**
	 * List of customer price plan expressions.
	 */
	@IgnoreField
	@NotEmpty
	@Valid
	private List<CustomerPricePlanExpression> expressions;

	/**
	 * Customer price plan payment cycle.
	 */
	@Required
	private PaymentCycle paymentCycle;

	/**
	 * Gets the customer price plan id.
	 *
	 * @return the customer price plan id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the customer price plan id.
	 *
	 * @param id
	 *            the new customer price plan id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
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
	 * Gets the List of customer price plan numeric variable.
	 *
	 * @return the List of customer price plan numeric variable
	 */
	public List<CustomerPricePlanVariable> getNumericVariables()
	{
		return numericVariables;
	}

	/**
	 * Sets the List of customer price plan numeric variable.
	 *
	 * @param numericVariables
	 *            the new List of customer price plan numeric variable
	 */
	public void setNumericVariables(List<CustomerPricePlanVariable> numericVariables)
	{
		this.numericVariables = numericVariables;
	}

	/**
	 * Gets the List of customer price plan expressions.
	 *
	 * @return the List of customer price plan expressions
	 */
	public List<CustomerPricePlanExpression> getExpressions()
	{
		return expressions;
	}

	/**
	 * Sets the List of customer price plan expressions.
	 *
	 * @param expressions
	 *            the new List of customer price plan expressions
	 */
	public void setExpressions(List<CustomerPricePlanExpression> expressions)
	{
		this.expressions = expressions;
	}

	/**
	 * Gets the Customer price plan payment cycle.
	 *
	 * @return the Customer price plan payment cycle
	 */
	public PaymentCycle getPaymentCycle()
	{
		return paymentCycle;
	}

	/**
	 * Sets the Customer price plan payment cycle.
	 *
	 * @param paymentCycle
	 *            the new Customer price plan payment cycle
	 */
	public void setPaymentCycle(PaymentCycle paymentCycle)
	{
		this.paymentCycle = paymentCycle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null || !(obj instanceof CustomerPricePlanModel))
		{
			return false;
		}

		CustomerPricePlanModel model = (CustomerPricePlanModel) obj;
		return this.id == model.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return (int) id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.getName().trim();
	}
}
