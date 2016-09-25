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
	public BasicSaveResponse save(MailTemplateModel mailTemplateModel);
}
