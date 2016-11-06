package com.agilepro.exception;

import com.yukthi.utils.exceptions.UtilsException;

/**
 * Notification exception to be thrown when an error occurs while sending notifications.
 * 
 * @author Pritam
 */
public class NotificationException extends UtilsException
{
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new notification exception.
	 *
	 * @param message the message
	 * @param args the args
	 */
	public NotificationException(String message, Object... args)
	{
		super(message, args);
	}

	/**
	 * Instantiates a new notification exception.
	 *
	 * @param cause the cause
	 * @param message the message
	 * @param args the args
	 */
	public NotificationException(Throwable cause, String message, Object... args)
	{
		super(cause, message, args);
	}
}
