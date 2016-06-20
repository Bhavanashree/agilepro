package com.agilepro.commons.models.customer;

import javax.validation.constraints.Pattern;

import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class CustomerPocModel.
 * 
 * @author pritam
 */
@Model(name = "CustomerPocModel")
public class CustomerPocModel
{
	/**
	 * Customer Point of Contact table primary id.
	 */
	@NonDisplayable
	private Long id;

	/**
	 * Customer name.
	 */
	@Required
	private String name;

	/**
	 * Customer phone number.
	 */
	@Required
	@Pattern(regexp = IWebUtilsCommonConstants.PATTERN_PHONE, message = "Invalid phone no specified")
	@MinLen(10)
	@MaxLen(15)
	private String phoneNumber;

	/**
	 * Customer email id.
	 */
	@Required
	@Pattern(regexp = IWebUtilsCommonConstants.PATTERN_EMAIL, message = "Invalid email id specified")
	private String email;

	/**
	 * Customer address.
	 */
	@MultilineText
	private String address;

	/**
	 * Instantiates a new customer poc model.
	 */
	public CustomerPocModel()
	{}

	/**
	 * Instantiates a new customer poc model.
	 *
	 * @param name
	 *            the name
	 * @param phoneNumber
	 *            the phone number
	 * @param email
	 *            the email
	 * @param address
	 *            the address
	 */
	public CustomerPocModel(String name, String phoneNumber, String email, String address)
	{
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
	}

	/**
	 * Gets the customer Point of Contact table primary id.
	 *
	 * @return the customer Point of Contact table primary id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the customer Point of Contact table primary id.
	 *
	 * @param id
	 *            the new customer Point of Contact table primary id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the customer name.
	 *
	 * @return the customer name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the customer name.
	 *
	 * @param name
	 *            the new customer name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the customer phone number.
	 *
	 * @return the customer phone number
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * Sets the customer phone number.
	 *
	 * @param phoneNumber
	 *            the new customer phone number
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the customer email id.
	 *
	 * @return the customer email id
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Sets the customer email id.
	 *
	 * @param email
	 *            the new customer email id
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Gets the customer address.
	 *
	 * @return the customer address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * Sets the customer address.
	 *
	 * @param address
	 *            the new customer address
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashcode()
	 */
	@Override
	public int hashCode()
	{
		if(id == null)
		{
			return 0;
		}

		return id.intValue();
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

		builder.append("Id: ").append(id);
		builder.append(comma).append("Name: ").append(name);
		builder.append(comma).append("email: ").append(email);
		builder.append(comma).append("phoneNumber: ").append(phoneNumber);
		builder.append(comma).append("address: ").append(address);

		builder.append("]");
		return builder.toString();
	}
}
