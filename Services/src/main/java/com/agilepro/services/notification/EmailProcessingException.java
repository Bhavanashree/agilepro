package com.agilepro.services.notification;

import com.yukthi.utils.exceptions.UtilsException;

/**
 * Exception to be thrown when email processing fails.
 * @author akiran
 */
public class EmailProcessingException extends UtilsException
{
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new email processing exception.
	 *
	 * @param message the message
	 * @param args the args
	 */
	public EmailProcessingException(String message, Object... args)
	{
		super(message, args);
	}

	/**
	 * Instantiates a new email processing exception.
	 *
	 * @param cause the cause
	 * @param message the message
	 * @param args the args
	 */
	public EmailProcessingException(Throwable cause, String message, Object... args)
	{
		super(cause, message, args);
	}
}
