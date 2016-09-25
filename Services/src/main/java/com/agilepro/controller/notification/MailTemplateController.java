package com.agilepro.controller.notification;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_MAIL_TEMPLATE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.notification.IMailTemplateController;
import com.agilepro.commons.models.notification.MailTemplateModel;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.notification.MailNotificationService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class MailTemplateController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_MAIL_TEMPLATE)
@RequestMapping("/mailTemplate")
public class MailTemplateController extends BaseController implements IMailTemplateController
{
	/** 
	 * The mail notification service. 
	 **/
	@Autowired
	private MailNotificationService mailNotificationService; 
	
	/**
	 * Save.
	 *
	 * @param mailTemplateModel the mail template model
	 * @return the basic save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Authorization(roles = { UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid MailTemplateModel mailTemplateModel)
	{
		mailNotificationService.send(mailTemplateModel);
		
		return new BasicSaveResponse();
	}
}
