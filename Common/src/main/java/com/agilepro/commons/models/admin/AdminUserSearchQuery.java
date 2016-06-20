package com.agilepro.commons.models.admin;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class AdminUserSearchQuery.
 */
@Model
public class AdminUserSearchQuery
{

	/**
	 * The user name.
	 */
	@Condition(value = "userName", op = Operator.LIKE, ignoreCase = true)
	private String userName;

	/**
	 * The password.
	 */
	@Condition(value = "password", op = Operator.LIKE, ignoreCase = true)
	private String password;

	/**
	 * The display name.
	 */
	@Condition(value = "displayName", op = Operator.LIKE, ignoreCase = true)
	private String displayName;

	/**
	 * The owner type.
	 */
	@Condition(value = "ownerType", op = Operator.LIKE, ignoreCase = true)
	private String ownerType;

	/**
	 * The deleted.
	 */
	@NonDisplayable
	private boolean deleted = false;

	/**
	 * Instantiates a new admin user search query.
	 */
	public AdminUserSearchQuery()
	{}

	/**
	 * Instantiates a new admin user search query.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param displayName
	 *            the display name
	 * @param deleted
	 *            the deleted
	 */
	public AdminUserSearchQuery(String userName, String password, String displayName, boolean deleted)
	{
		this.userName = userName;
		this.password = password;
		this.displayName = displayName;
		this.deleted = deleted;
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
	 * Checks if is deleted.
	 *
	 * @return true, if is deleted
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
}
