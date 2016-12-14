package com.agilepro.persistence.entity.admin;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.agilepro.commons.EmployeeGender;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.conversion.impl.JsonConverter;
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
	private static final String ERROR_MESSAGE_DUPLICATE_EMPLOYEE = "Specified  employee already  exists:";

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
	 * The gender.
	 **/
	@Column(name = "GENDER")
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private EmployeeGender gender;

	/**
	 * Employees designation.
	 */
	@ManyToMany
	@JoinTable(name = "EMP_DESIGNATION_MAP", joinColumns = { @JoinColumn(name = "EMPLOYEE_ID") }, inverseJoinColumns = { @JoinColumn(name = "DESIGNATION_ID") })
	private List<DesignationEntity> designations;

	/**
	 * Instantiates a new employee entity.
	 */
	public EmployeeEntity()
	{}

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
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public EmployeeGender getGender()
	{
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender
	 *            the new gender
	 */
	public void setGender(EmployeeGender gender)
	{
		this.gender = gender;
	}

	public List<DesignationEntity> getDesignations()
	{
		return designations;
	}

	public void setDesignations(List<DesignationEntity> designations)
	{
		this.designations = designations;
	}
}
