package com.agilepro.commons.models.admin;

import java.util.Date;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.AbstractExtendedSearchResult;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class EmployeeSearchResult.
 */
@Model
public class EmployeeSearchResult extends AbstractExtendedSearchResult
{
	
	/**
	 *  Employee table primary id. 
	 **/
	@NonDisplayable
	@Field(value = "id")
	private Long id;

	/**
	 *  Employee name. 
	 **/
	@Field(value = "name")
	private String name;

	/**
	 *  Employee mail id. 
	 **/
	@Field(value = "mailId")
	private String mailId;

	/**
	 * Employee phone number. 
	 **/
	@Field(value = "phoneNumber")
	private String phoneNumber;

	/**
	 * Employee address. 
	 **/
	@Field(value = "address")
	private String address;

	/** 
	 * The designation name.
	 **/
	@Field(value = "designation.name")
	private String designationName;

	/** 
	 * Created by user.
	 **/
	@Field("createdBy.displayName")
	private String createdBy;

	/** 
	 * Created on date.
	 **/
	@Field("createdOn")
	private Date createdOn;

	/** 
	 *Updated by user. 
	 **/
	@Field("updatedBy.displayName")
	private String updatedBy;

	/** 
	 *Update on date.
	 **/
	@Field("updatedOn")
	private Date updatedOn;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the mail id.
	 *
	 * @return the mail id
	 */
	public String getMailId()
	{
		return mailId;
	}

	/**
	 * Sets the mail id.
	 *
	 * @param mailId the new mail id
	 */
	public void setMailId(String mailId)
	{
		this.mailId = mailId;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Date getCreatedOn()
	{
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public String getUpdatedBy()
	{
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public Date getUpdatedOn()
	{
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn the new updated on
	 */
	public void setUpdatedOn(Date updatedOn)
	{
		this.updatedOn = updatedOn;
	}

	/**
	 * Gets the designation name.
	 *
	 * @return the designation name
	 */
	public String getDesignationName()
	{
		return designationName;
	}

	/**
	 * Sets the designation name.
	 *
	 * @param designationName the new designation name
	 */
	public void setDesignationName(String designationName)
	{
		this.designationName = designationName;
	}
}
