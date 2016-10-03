package com.agilepro.commons.controllers.notification;

import com.agilepro.commons.models.notification.MailTemplateModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IMailTemplateController.
 * 
 * @author Pritam
 */
@RemoteService
public interface IMailTemplateController
{
	
	/**
	 * Save.
	 *
	 * @param mailTemplateModel the mail template model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(MailTemplateModel mailTemplateModel);

	/**
	 * Save mail template.
	 *
	 * @param mailModel the mail model
	 * @return the basic save response
	 */
	public BasicSaveResponse saveMailTemplate(MailTemplateModel mailModel);
}
