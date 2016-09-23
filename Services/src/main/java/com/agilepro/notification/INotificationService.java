package com.agilepro.notification;

import com.agilepro.commons.models.notification.MailTemplateModel;

/**
 * The Interface INotificationService.
 * 
 * @author Pritam
 */
public interface INotificationService 
{
	public void send(MailTemplateModel mailTemplateModel);
	
	public void register(INotificationProcessor inotificationProcessor);// IProcess
}
