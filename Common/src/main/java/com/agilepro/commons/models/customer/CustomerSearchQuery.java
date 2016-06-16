package com.agilepro.commons.models.customer;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class CustomerSearchQuery.
 * 
 * @author Pritam
 */
@Model
public class CustomerSearchQuery
{
	/**
	 * Customer search name pattern.
	 */
	@Condition(value = "name", op = Operator.LIKE, ignoreCase = true)
	private String name;

	/**
	 * Customer search work type pattern.
	 */
	@Condition(value = "workType", op = Operator.LIKE, ignoreCase = true)
	private String workType;

	/**
	 * Customer search email pattern.
	 */
	@Condition(value = "email", op = Operator.LIKE, ignoreCase = true)
	private String email;

	/**
	 * Customer search phone number pattern.
	 */
	@Condition(value = "phoneNumber", op = Operator.LIKE)
	private String phoneNumber;

	/**
	 * Minimum due amount.
	 */
	@Condition(value = "dueAmount", op = Operator.GE)
	private Double dueAmount;

	/**
	 * Instantiates a new customer search query.
	 */
	public CustomerSearchQuery()
	{}

	/**
	 * Instantiates a new customer search query.
	 *
	 * @param name the name
	 * @param email the email
	 * @param phoneNumber the phone number
	 * @param workType the work type
	 * @param dueAmount the due amount
	 */
	public CustomerSearchQuery(String name, String email, String phoneNumber, String workType, Double dueAmount)
	{
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.workType = workType;
		this.dueAmount = dueAmount;
	}

	/**
	 * Gets the minimum due amount.
	 *
	 * @return the minimum due amount
	 */
	public Double getDueAmount()
	{
		return dueAmount;
	}

	/**
	 * Sets the minimum due amount.
	 *
	 * @param dueAmount
	 *            the new minimum due amount
	 */
	public void setDueAmount(Double dueAmount)
	{
		this.dueAmount = dueAmount;
	}

	/**
	 * Gets the customer search work type pattern.
	 *
	 * @return the customer search work type pattern
	 */
	public String getWorkType()
	{
		return workType;
	}

	/**
	 * Sets the customer search work type pattern.
	 *
	 * @param workType
	 *            the new customer search work type pattern
	 */
	public void setWorkType(String workType)
	{
		this.workType = workType;
	}

	/**
	 * Gets the customer search email pattern.
	 *
	 * @return the customer search email pattern
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Sets the customer search email pattern.
	 *
	 * @param email
	 *            the new customer search email pattern
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Gets the customer search phone number pattern.
	 *
	 * @return the customer search phone number pattern
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * Sets the customer search phone number pattern.
	 *
	 * @param phoneNumber
	 *            the new customer search phone number pattern
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the customer search name pattern.
	 *
	 * @return the customer search name pattern
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the customer search name pattern.
	 *
	 * @param name
	 *            the new customer search name pattern
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}