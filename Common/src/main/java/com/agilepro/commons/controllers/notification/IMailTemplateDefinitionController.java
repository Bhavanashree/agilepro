package com.agilepro.commons.controllers.notification;

import com.agilepro.commons.models.notification.MailTemplateDefinitionModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

@RemoteService
public interface IMailTemplateDefinitionController
{

	public BasicSaveResponse save(MailTemplateDefinitionModel model);
	
	public BasicReadResponse<MailTemplateDefinitionModel> read(Long id);

	public BaseResponse update(MailTemplateDefinitionModel model);

	public BaseResponse delete(Long id);
}
