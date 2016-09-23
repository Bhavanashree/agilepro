package com.agilepro.commons.models.customer;

import javax.validation.constraints.Pattern;

import com.yukthi.validation.annotations.MatchWith;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.Password;

/**
 * The Class NotificationMailDetails. Has details about the mail
 * 
 * @author Pritam
 */
@Model
public class NotificationMailDetails
{
	// mail server, port, secured or not, mail id, password

	/**
	 * The mail id.
	 **/
	@Required
	@Pattern(regexp = IWebUtilsCommonConstants.PATTERN_EMAIL, message = "Invalid mail id specified")
	@MinLen(5)
	@MaxLen(50)
	private String mailId;

	/**
	 * The password.
	 **/
	@Password
	@MinLen(5)
	@MaxLen(10)
	@Required
	private String password;

	/**
	 * The confirm password.
	 **/
	@Password
	@Required
	@MatchWith(field = "password", message = "Confirm password dosent match with password")
	@MinLen(5)
	@MaxLen(10)
	private String confirmPassword;

	/**
	 * The mail smtp host.
	 **/
	@Required
	@MinLen(5)
	@MaxLen(10)
	private String mailSmtpHost;

	/**
	 * The mail smtp port.
	 **/
	@Required
	@MinLen(2)
	@MaxLen(10)
	private String mailSmtpPort;

	/**
	 * The secured or not.
	 **/
	@Required
	private boolean securedOrNot;

	/**
	 * Instantiates a new notification mail details.
	 */
	public NotificationMailDetails()
	{
		super();
	}

	/**
	 * Instantiates a new notification mail details.
	 *
	 * @param mailId
	 *            the mail id
	 * @param password
	 *            the password
	 * @param mailSmtpHost
	 *            the mail smtp host
	 * @param mailSmtpPort
	 *            the mail smtp port
	 * @param securedOrNot
	 *            the secured or not
	 */
	public NotificationMailDetails(String mailId, String password, String mailSmtpHost, String mailSmtpPort, boolean securedOrNot)
	{
		super();
		this.mailId = mailId;
		this.password = password;
		this.mailSmtpHost = mailSmtpHost;
		this.mailSmtpPort = mailSmtpPort;
		this.securedOrNot = securedOrNot;
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
	 * Gets the mail smtp host.
	 *
	 * @return the mail smtp host
	 */
	public String getMailSmtpHost()
	{
		return mailSmtpHost;
	}

	/**
	 * Sets the mail smtp host.
	 *
	 * @param mailSmtpHost
	 *            the new mail smtp host
	 */
	public void setMailSmtpHost(String mailSmtpHost)
	{
		this.mailSmtpHost = mailSmtpHost;
	}

	/**
	 * Gets the mail smtp port.
	 *
	 * @return the mail smtp port
	 */
	public String getMailSmtpPort()
	{
		return mailSmtpPort;
	}

	/**
	 * Sets the mail smtp port.
	 *
	 * @param mailSmtpPort
	 *            the new mail smtp port
	 */
	public void setMailSmtpPort(String mailSmtpPort)
	{
		this.mailSmtpPort = mailSmtpPort;
	}

	/**
	 * Checks if is secured or not.
	 *
	 * @return true, if is secured or not
	 */
	public boolean isSecuredOrNot()
	{
		return securedOrNot;
	}

	/**
	 * Sets the secured or not.
	 *
	 * @param securedOrNot
	 *            the new secured or not
	 */
	public void setSecuredOrNot(boolean securedOrNot)
	{
		this.securedOrNot = securedOrNot;
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
	 * @param confirmPassword the new confirm password
	 */
	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}
}
