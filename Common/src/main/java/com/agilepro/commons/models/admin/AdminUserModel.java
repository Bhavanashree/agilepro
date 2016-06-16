package com.agilepro.commons.models.admin;

import javax.validation.constraints.Pattern;

import com.yukthi.validation.annotations.MatchWith;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;
import com.yukthi.webutils.common.annotations.Password;

/**
 * The Class UserAdminModel.
 */
@Model
public class AdminUserModel
{

	/**
	 * The id.
	 */
	@NonDisplayable
	private Long id;

	/**
	 * The version.
	 */
	@NonDisplayable
	private Integer version;

	/**
	 * The user name.
	 */
	@Pattern(regexp = IWebUtilsCommonConstants.PATTERN_EMAIL, message = "Invalid username specified")
	@NotEmpty
	@MinLen(3)
	@MaxLen(50)
	private String userName;

	/**
	 * The password.
	 */
	@Password
	@Required
	private String password;

	/**
	 * The confirm password.
	 */
	@Password
	@Required
	@MatchWith(field = "password", message = "Confirm password dosent match with password")
	private String confirmPassword;

	/**
	 * The display name.
	 */
	@Required
	private String displayName;

	/**
	 * The deleted.
	 */
	@NonDisplayable
	private boolean deleted = false;

	/**
	 * Instantiates a new user admin model.
	 */
	public AdminUserModel()
	{}

	/**
	 * Instantiates a new admin user model.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param confirmPassword
	 *            the confirm password
	 * @param displayName
	 *            the display name
	 * @param ownerType
	 *            the owner type
	 * @param deleted
	 *            the deleted
	 */
	public AdminUserModel(String userName, String password, String confirmPassword, String displayName, String ownerType, boolean deleted)
	{
		this.userName = userName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.displayName = displayName;
	}

	/**
	 * Checks if is deleted.
	 *
	 * @return the deleted
	 */
	public boolean isDeleted()
	{
		return deleted;
	}

	/**
	 * Sets the deleted.
	 *
	 * @param deleted
	 *            the new deleted
	 */
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
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
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
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
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @param displayName
	 *            the new display name
	 */
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	/**
	 * Gets the owner type.
	 *
	 * @return the owner type
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
}
