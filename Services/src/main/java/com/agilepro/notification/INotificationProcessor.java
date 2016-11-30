package com.agilepro.notification;

import com.agilepro.services.notification.EmailProcessingException;
import com.yukthi.webutils.mail.MailProcessingException;
import com.yukthi.webutils.mail.ReceivedMailMessage;
import com.yukthi.webutils.repository.UserEntity;

/**
 * The Interface INotificationProcessor.
 * 
 * @author Pritam
 */
public interface INotificationProcessor
{
	/**
	 * Expected to process specified mail from specified user.
	 *
	 * @param userEntity the user entity
	 * @param mailMessage Mail message to process.
	 * @return true if successfully processed.
	 */
	public boolean process(UserEntity userEntity, ReceivedMailMessage mailMessage) throws MailProcessingException, EmailProcessingException;
}
