package com.agilepro.services.notification;

import java.util.Arrays;

import com.yukthi.webutils.mail.IMailCustomizer;
import com.yukthi.webutils.mail.MailMessage;
import com.yukthi.webutils.mail.template.MailConfigField;
import com.yukthi.webutils.mail.template.MailTemplateConfig;

/**
 * Mail context used when there is an error while processing the mail.
 * @author akiran
 */
@MailTemplateConfig(name = "MAIL_PROCESSING_ERROR", description = "Used when unable to process incoming mail.")
public class MailProcessingErrorContext implements IMailCustomizer
{
	/**
	 * Indicates no user match is found for input mail from id.
	 */
	public static final String ERR_CODE_NO_USER = "ERR_CODE_NO_USER";
	
	/**
	 * Indicates no processor is able to process the input mail.
	 */
	public static final String ERR_CODE_PROCESSING_FAILED = "ERR_CODE_PROCESSING_FAILED";
	
	/**
	 * Indicates processing of input mail resulted in error and needs user action.
	 */
	public static final String ERR_CODE_PROCESSING_ERRORED = "ERR_CODE_PROCESSING_ERRORED";

	/**
	 * Indicates processing of input mail resulted in unknown error.
	 */
	public static final String ERR_CODE_SERVER_ERROR = "ERR_CODE_SERVER_ERROR";
	
	/**
	 * Indicates non html content mail is received.
	 */
	public static final String ERR_CODE_NON_HTML = "ERR_CODE_NON_HTML";

	/**
	 * Mail id from which mail is received.
	 */
	@MailConfigField(description = "Mail id from which mail is received.", sampleValue = "test@test.com")
	private String mailId;
	
	/**
	 * Name of the customer under which this mail is received.
	 */
	@MailConfigField(description = "Name of the customer under which this mail is received.", sampleValue = "Yukthi Tech")
	private String customerName;
	
	/**
	 * Error code representing the error occurred.
	 */
	@MailConfigField(description = "Error code representing the error occurred. Error code can be one of the following: <BR/>"
			+ ERR_CODE_NO_USER + " - Indicates no user match is found for input mail from id.<BR/>"
			+ ERR_CODE_PROCESSING_FAILED + " - Indicates no processor is able to process the input mail."
			+ ERR_CODE_PROCESSING_ERRORED + " - Indicates processing of input mail resulted in error and needs user action."
			+ ERR_CODE_SERVER_ERROR + " - Indicates processing of input mail resulted in unknown error.",
		sampleValue = ERR_CODE_PROCESSING_FAILED)
	private String errorCode;
	
	/**
	 * Error message provided by mail processor, in case of ERR_CODE_PROCESSING_FAILED.
	 */
	@MailConfigField(description = "Error message provided by mail processor, in case of error-code: " + ERR_CODE_PROCESSING_ERRORED, 
		sampleValue = "Sample error message from processor!")
	private String errorMessage;
	
	/**
	 * Corresponding user name.
	 */
	@MailConfigField(description = "Corresponding user name. Available only when error code is not - " + ERR_CODE_NO_USER, sampleValue = "Test User")
	private String userName;
	
	/**
	 * Instantiates a new mail processing error context.
	 *
	 * @param mailId the mail id
	 * @param customerName the customer name
	 * @param errorCode the error code
	 * @param errorMessage the error message
	 * @param userName the user name
	 */
	public MailProcessingErrorContext(String mailId, String customerName, String errorCode, String errorMessage, String userName)
	{
		this.mailId = mailId;
		this.customerName = customerName;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.userName = userName;
	}

	/**
	 * Gets the mail id from which mail is received.
	 *
	 * @return the mail id from which mail is received
	 */
	public String getMailId()
	{
		return mailId;
	}

	/**
	 * Gets the name of the customer under which this mail is received.
	 *
	 * @return the name of the customer under which this mail is received
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * Gets the error code representing the error occurred.
	 *
	 * @return the error code representing the error occurred
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * Gets the error message provided by mail processor, in case of ERR_CODE_PROCESSING_FAILED.
	 *
	 * @return the error message provided by mail processor, in case of ERR_CODE_PROCESSING_FAILED
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * Gets the corresponding user name.
	 *
	 * @return the corresponding user name
	 */
	public String getUserName()
	{
		return userName;
	}

	/* (non-Javadoc)
	 * @see com.yukthi.webutils.mail.IMailCustomizer#customize(com.yukthi.webutils.mail.MailMessage, java.lang.Object)
	 */
	@Override
	public void customize(MailMessage mailMessage, Object templateCustomization)
	{
		mailMessage.setToList(Arrays.asList(mailId));
	}
}
