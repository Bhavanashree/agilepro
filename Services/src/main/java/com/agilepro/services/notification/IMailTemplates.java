package com.agilepro.services.notification;

/**
 * Mail template constants.
 * @author akiran
 */
public interface IMailTemplates
{
	/**
	 * Default owner type for mail templates.
	 */
	public static final String DEFAULT_OWNER_TYPE = "ADMIN";
	
	/**
	 * Mail template name for reseting the password.
	 */
	public static final String RESET_PASSWORD = "RESET_PASSWORD";

	/**
	 * Mail template name for replying to mails which resulted in processing error.
	 */
	public static final String MAIL_PROCESSING_ERROR = "MAIL_PROCESSING_ERROR";
}
