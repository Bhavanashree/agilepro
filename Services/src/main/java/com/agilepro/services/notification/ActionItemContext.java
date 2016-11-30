package com.agilepro.services.notification;

import java.util.List;

import com.yukthi.webutils.mail.IMailCustomizer;
import com.yukthi.webutils.mail.MailMessage;

/**
 * The Class ActionItemContext, used in free marker template getting list of employees for sending
 * mail as action items from scrum meeting conversation.
 * 
 * @author Pritam
 */
public class ActionItemContext implements IMailCustomizer
{
	/**
	 * The email ids.
	 **/
	private List<String> emailIds;

	/**
	 * The user display name used in html file for sending mail.
	 **/
	private String userDisplayName;

	/**
	 * The new password.
	 **/
	private String newPassword;

	/**
	 * Instantiates a new action item context.
	 *
	 * @param emailIds
	 *            the email ids
	 * @param userDisplayName
	 *            the user display name
	 * @param newPassword
	 *            the new password
	 */
	public ActionItemContext(List<String> emailIds, String userDisplayName, String newPassword)
	{
		super();
		this.emailIds = emailIds;
		this.userDisplayName = userDisplayName;
		this.newPassword = newPassword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yukthi.webutils.mail.IMailCustomizer#customize(com.yukthi.webutils.
	 * mail.MailMessage, java.lang.Object)
	 */
	@Override
	public void customize(MailMessage mailMessage, Object templateCustomization)
	{
		mailMessage.setToList(emailIds);
	}

	public List<String> getEmailIds()
	{
		return emailIds;
	}

	public void setEmailIds(List<String> emailIds)
	{
		this.emailIds = emailIds;
	}

	public String getUserDisplayName()
	{
		return userDisplayName;
	}

	public void setUserDisplayName(String userDisplayName)
	{
		this.userDisplayName = userDisplayName;
	}

	public String getNewPassword()
	{
		return newPassword;
	}

	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}
}
