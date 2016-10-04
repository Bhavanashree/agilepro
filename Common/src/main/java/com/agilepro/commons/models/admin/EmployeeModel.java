package com.agilepro.commons.models.admin;

import javax.validation.constraints.Pattern;

import com.agilepro.commons.EmployeeGender;
import com.yukthi.validation.annotations.MatchWith;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.ImageInfo;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;
import com.yukthi.webutils.common.annotations.Password;

/**
 * employee.
 *
 * @author Bhavana.
 */
@ExtendableModel(name = "Employee")
@Model
public class EmployeeModel extends AbstractExtendableModel
{

	/**
	 * Employee table primary id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * Employee name.
	 **/
	@NotEmpty
	@MinLen(3)
	@MaxLen(20)
	private String name;

	/**
	 * Employee mail id.
	 **/
	@Pattern(regexp = IWebUtilsCommonConstants.PATTERN_EMAIL, message = "Invalid mail id specified")
	@MinLen(5)
	@MaxLen(30)
	@NotEmpty
	private String mailId;

	/**
	 * Employee phone number.
	 **/
	@NotEmpty
	@MinLen(10)
	@MaxLen(15)
	@Pattern(regexp = IWebUtilsCommonConstants.PATTERN_PHONE, message = "Invalid phone no specified")
	private String phoneNumber;

	/**
	 * Employee address.
	 **/
	@MultilineText
	private String address;

	/**
	 * The password.
	 **/
	@MinLen(5)
	@MaxLen(15)
	@Password
	@Required
	private String password;

	/**
	 * The confirm password.
	 **/

	@Password
	@MatchWith(field = "password", message = "Confirm password dosent match with password")
	@Required
	private String confirmPassword;

	/**
	 * The employee gender.
	 **/
	@Required
	private EmployeeGender gender;

	/**
	 * The designations.
	 **/
	@LOV(name = "designationList")
	@Required
	private Long designationId;

	/**
	 * The photo.
	 **/
	private ImageInfo photo;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * Instantiates a new employee entity.
	 */
	public EmployeeModel()
	{}

	/**
	 * Instantiates a new employee model.
	 *
	 * @param name
	 *            the name
	 * @param mailId
	 *            the mail id
	 * @param phoneNumber
	 *            the phone number
	 * @param password
	 *            the password
	 * @param confirmPassword
	 *            the confirm password
	 * @param designationId
	 *            the designation id
	 */
	public EmployeeModel(String name, String mailId, String phoneNumber, String password, String confirmPassword, Long designationId)
	{
		this.name = name;
		this.mailId = mailId;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.designationId = designationId;
	}

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
	 * @param id
	 *            the new id
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Gets the confirm password.
	 *
	 * @return the confirm password
	 */
	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	/**
	 * Sets the confirm password.
	 *
	 * @param confirmPassword
	 *            the new confirm password
	 */
	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
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
	 * Gets the designation id.
	 *
	 * @return the designation id
	 */
	public Long getDesignationId()
	{
		return designationId;
	}

	/**
	 * Sets the designation id.
	 *
	 * @param designationId
	 *            the new designation id
	 */
	public void setDesignationId(Long designationId)
	{
		this.designationId = designationId;
	}

	/**
	 * Gets the photo.
	 *
	 * @return the photo
	 */
	public ImageInfo getPhoto()
	{
		return photo;
	}

	/**
	 * Sets the photo.
	 *
	 * @param photo
	 *            the new photo
	 */
	public void setPhoto(ImageInfo photo)
	{
		this.photo = photo;
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
}
