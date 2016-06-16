package com.agilepro.commons.models.admin;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class EmployeeSearchQuery.
 */
@Model
public class EmployeeSearchQuery
{

	/**
	 * The Name of the Employee.
	 **/
	@Condition(value = "name", op = Operator.LIKE, ignoreCase = true)
	private String name;

	/**
	 * Employee search mail_Id.
	 **/
	@Condition(value = "mailId", op = Operator.LIKE, ignoreCase = true)
	private String mailId;

	/**
	 * Employee search phone number.
	 **/
	@Condition(value = "phoneNumber", op = Operator.LIKE)
	private String phoneNumber;

	/**
	 * The address.
	 **/
	@Condition(value = "address", op = Operator.LIKE, ignoreCase = true)
	private String address;

	/**
	 * The designation id.
	 **/
	@LOV(name = "designationList")
	@Condition(value = "designation.id", op = Operator.EQ)
	private Long designations;

	/**
	 * Instantiates a new employee entity.
	 */
	public EmployeeSearchQuery()
	{}

	/**
	 * Instantiates a new employee entity.
	 *
	 * @param name
	 *            the name
	 * @param mailId
	 *            the mailId
	 * @param phoneNumber
	 *            the phoneNumber
	 * @param address
	 *            the address
	 * @param designations
	 *            the designations
	 */

	public EmployeeSearchQuery(String name, String mailId, String phoneNumber, String address, Long designations)
	{

		this.name = name;
		this.mailId = mailId;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.designations = designations;
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
	 * @param name
	 *            the new name
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
	 * @param mailId
	 *            the new mail id
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
	 * @param phoneNumber
	 *            the new phone number
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
	 * @param address
	 *            the new address
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * Gets the designations.
	 *
	 * @return the designations
	 */
	public Long getDesignations()
	{
		return designations;
	}

	/**
	 * Sets the designations.
	 *
	 * @param designations
	 *            the new designations
	 */
	public void setDesignations(Long designations)
	{
		this.designations = designations;
	}
}
