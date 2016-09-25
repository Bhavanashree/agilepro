package com.agilepro.exception;

/**
 * The Class NotificationException.
 * 
 * @author Pritam
 */
@SuppressWarnings("serial")
public class NotificationException extends RuntimeException
{
	/**
	 * Instantiates a new notification exception.
	 */
	public NotificationException()
	{
		super();
	}
	
	/**
	 * Instantiates a new notification exception.
	 *
	 * @param message the message
	 */
	public NotificationException(String message)
	{
		super(message);
	}
	
	/**
	 * Instantiates a new notification exception.
	 *
	 * @param throwable the throwable
	 */
	public NotificationException(Throwable throwable)
	{
		super(throwable);
	}
	
	/**
	 * Instantiates a new notification exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public NotificationException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
