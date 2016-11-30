package com.agilepro.notification;

import com.yukthi.webutils.common.models.mails.MailTemplateModel;

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
