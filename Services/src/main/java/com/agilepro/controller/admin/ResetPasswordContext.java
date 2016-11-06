package com.agilepro.controller.admin;

import java.util.Arrays;

import com.yukthi.webutils.mail.IMailCustomizer;
import com.yukthi.webutils.mail.MailMessage;
import com.yukthi.webutils.mail.template.MailConfigField;
import com.yukthi.webutils.mail.template.MailTemplateConfig;

/**
 * Mail context for reset password template.
 * @author akiran
 */
@MailTemplateConfig(name = "Reset Password", description = "Used when user requests to reset his/her password.")
public class ResetPasswordContext implements IMailCustomizer
{
	/**
	 * Mail id for which password being reset.
	 */
	private String mailId;
	
	/**
	 * New password to set.
	 */
	@MailConfigField(description = "Newly generated password", sampleValue = "Password@1234")
	private String newPassword;
	
	/**
	 * Display name of the user.
	 */
	@MailConfigField(description = "Display name of the user", sampleValue = "Test User")
	private String userDisplayName;
	
	/**
	 * Instantiates a new reset password context.
	 *
	 * @param mailId the mail id
	 * @param password the password
	 * @param displayName the display name
	 */
	public ResetPasswordContext(String mailId, String password, String displayName)
	{
		this.mailId = mailId;
		this.newPassword = password;
		this.userDisplayName = displayName;
	}
	
	/**
	 * Gets the new password to set.
	 *
	 * @return the new password to set
	 */
	public String getNewPassword()
	{
		return newPassword;
	}

	/* (non-Javadoc)
	 * @see com.yukthi.webutils.mail.IMailCustomizer#customize(com.yukthi.webutils.mail.MailMessage, java.lang.Object)
	 */
	@Override
	public void customize(MailMessage mailMessage, Object templateCustomization)
	{
		mailMessage.setToList(Arrays.asList(mailId));
	}
	
	/**
	 * Gets the display name of the user.
	 *
	 * @return the display name of the user
	 */
	public String getUserDisplayName()
	{
		return userDisplayName;
	}
}
