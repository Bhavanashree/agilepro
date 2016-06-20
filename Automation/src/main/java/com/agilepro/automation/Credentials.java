package com.agilepro.automation;

/**
 * Represents credentials.
 * @author akiran
 */
public class Credentials
{
	/**
	 * Email id.
	 */
	private String email;
	
	/**
	 * password.
	 */
	private String password;

	/**
	 * Gets the email id.
	 *
	 * @return the email id
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Sets the email id.
	 *
	 * @param email the new email id
	 */
	public void setEmail(String email)
	{
		this.email = email;
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
	 * @param password the new password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
}
