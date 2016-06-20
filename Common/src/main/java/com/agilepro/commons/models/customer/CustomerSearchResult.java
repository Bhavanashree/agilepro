package com.agilepro.commons.models.customer;

import java.util.Date;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.AbstractExtendedSearchResult;
import com.yukthi.webutils.common.annotations.Label;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class CustomerSearchResult.
 * 
 * @author pritam
 */
@Model
public class CustomerSearchResult extends AbstractExtendedSearchResult
{
	/**
	 * Id.
	 */
	@NonDisplayable
	@Field(value = "id")
	private long id;

	/**
	 * Name of the customer.
	 */
	@Field(value = "name")
	private String name;

	/**
	 * Email id of the customer.
	 */
	@Field(value = "email")
	private String email;

	/**
	 * Phone number of the customer.
	 */
	@Field(value = "phoneNumber")
	private String phoneNumber;

	/**
	 * Domain of the customer.
	 */
	@Field(value = "workType")
	private String workType;

	/**
	 * Due amount need to pay.
	 */
	@Field(value = "dueAmount")
	private double dueAmount;

	/**
	 * Name of the Plan.
	 */
	@Label("Price Plan Name")
	@Field(value = "customerPricePlan.name")
	private String planName;

	/**
	 * Date of registration.
	 */
	@Label("Registration Date")
	@Field("registrationDate")
	private Date registrationDate;

	/**
	 * Created by user.
	 */
	@Field("createdBy.displayName")
	private String createdBy;

	/**
	 * Created on date.
	 */
	@Field("createdOn")
	private Date createdOn;

	/**
	 * Updated by user.
	 */
	@Field("updatedBy.displayName")
	private String updatedBy;

	/**
	 * Update on date.
	 */
	@Field("updatedOn")
	private Date updatedOn;

	/**
	 * Gets the date of registration.
	 *
	 * @return the date of registration
	 */
	public Date getRegistrationDate()
	{
		return registrationDate;
	}

	/**
	 * Sets the date of registration.
	 *
	 * @param registrationDate
	 *            the new date of registration
	 */
	public void setRegistrationDate(Date registrationDate)
	{
		this.registrationDate = registrationDate;
	}

	/**
	 * Gets the name of the Plan.
	 *
	 * @return the name of the Plan
	 */
	public String getPlanName()
	{
		return planName;
	}

	/**
	 * Sets the name of the Plan.
	 *
	 * @param planName
	 *            the new name of the Plan
	 */
	public void setPlanName(String planName)
	{
		this.planName = planName;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * Gets the due amount.
	 *
	 * @return the due amount
	 */
	public double getDueAmount()
	{
		return dueAmount;
	}

	/**
	 * Sets the due amount need to pay.
	 *
	 * @param dueAmount
	 *            the new due amount need to pay
	 */
	public void setDueAmount(double dueAmount)
	{
		this.dueAmount = dueAmount;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Sets the email id of the customer.
	 *
	 * @param email
	 *            the new email id of the customer
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Gets the phone number of the customer.
	 *
	 * @return the phone number of the customer
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * Sets the phone number of the customer.
	 *
	 * @param phoneNumber
	 *            the new phone number of the customer
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the domain of the customer.
	 *
	 * @return the domain of the customer
	 */
	public String getWorkType()
	{
		return workType;
	}

	/**
	 * Sets the domain of the customer.
	 *
	 * @param workType
	 *            the new domain of the customer
	 */
	public void setWorkType(String workType)
	{
		this.workType = workType;
	}

	/**
	 * Gets the name of the customer.
	 *
	 * @return the name of the customer
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the customer.
	 *
	 * @param name
	 *            the new name of the customer
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the created by user.
	 *
	 * @return the created by user
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}

	/**
	 * Sets the created by user.
	 *
	 * @param createdBy
	 *            the new created by user
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created on date.
	 *
	 * @return the created on date
	 */
	public Date getCreatedOn()
	{
		return createdOn;
	}

	/**
	 * Sets the created on date.
	 *
	 * @param createdOn
	 *            the new created on date
	 */
	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}

	/**
	 * Gets the updated by user.
	 *
	 * @return the updated by user
	 */
	public String getUpdatedBy()
	{
		return updatedBy;
	}

	/**
	 * Sets the updated by user.
	 *
	 * @param updatedBy
	 *            the new updated by user
	 */
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the update on date.
	 *
	 * @return the update on date
	 */
	public Date getUpdatedOn()
	{
		return updatedOn;
	}

	/**
	 * Sets the update on date.
	 *
	 * @param updatedOn
	 *            the new update on date
	 */
	public void setUpdatedOn(Date updatedOn)
	{
		this.updatedOn = updatedOn;
	}
}