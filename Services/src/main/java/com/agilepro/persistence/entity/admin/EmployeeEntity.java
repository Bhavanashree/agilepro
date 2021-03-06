package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.admin.EmployeeModel;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * Maintains Employee information.
 * 
 * @author Bhavana
 */

@ExtendableEntity(name = "Employee")
@Table(name = "EMPLOYEE")
public class EmployeeEntity extends WebutilsExtendableEntity
{

	/**
	 * setting message for duplicate employee.
	 */
	public static final String ERROR_MESSAGE_DUPLICATE_EMPLOYEE = "Specified  employee already  exists:";

	/**
	 * Employee name.
	 **/
	@UniqueConstraint(name = "NAME", message = ERROR_MESSAGE_DUPLICATE_EMPLOYEE)
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	/**
	 * Employee mail id.
	 **/
	@Column(name = "MAIL_ID", length = 50, nullable = false)
	private String mailId;

	/**
	 * Employee phone number.
	 **/
	@Column(name = "PHONE_NUMBER", length = 15, nullable = false)
	private String phoneNumber;

	/**
	 * Employee address.
	 **/
	@Column(name = "ADDRESS", length = 500)
	private String address;

	/**
	 * Employees designation.
	 */
	@ManyToOne
	@PropertyMapping(type = EmployeeModel.class, from = "designations", subproperty = "id")
	@Column(name = "DESIGNATION_ID", nullable = false)
	private DesignationEntity designation;

	/**
	 * Instantiates a new employee entity.
	 */
	public EmployeeEntity()
	{
	}

	/**
	 * Instantiates a new employee entity.
	 *
	 * @param name
	 *            the name
	 * @param mailId
	 *            the mail id
	 * @param phoneNumber
	 *            the phone number
	 * @param address
	 *            the address
	 */
	public EmployeeEntity(String name, String mailId, String phoneNumber, String address)
	{
		this.name = name;
		this.mailId = mailId;
		this.phoneNumber = phoneNumber;
		this.address = address;
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
	 * Gets the designation.
	 *
	 * @return the designation
	 */
	public DesignationEntity getDesignation()
	{
		return designation;
	}

	/**
	 * Sets the designation.
	 *
	 * @param designation
	 *            the new designation
	 */
	public void setDesignation(DesignationEntity designation)
	{
		this.designation = designation;
	}
}
