package com.agilepro.controller.admin;

import javax.validation.Valid;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_MAIL_TEMPLATE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_BY_OWNER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.BasicVersionAndIdResponse;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.notification.AgileProMailTemplateModel;
import com.agilepro.commons.controllers.notification.RecipientInfoList;
import com.agilepro.services.admin.AgileProMailTemplateService;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.notification.EmailNotificationService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.controllers.BaseController;
import com.yukthi.webutils.mail.template.MailTemplateEntity;
import com.yukthi.webutils.mail.template.MailTemplateService;
import com.yukthi.webutils.utils.WebUtils;

/**
 * The Class MailTemplateController.
 */
@RestController
@ActionName(ACTION_PREFIX_MAIL_TEMPLATE)
@RequestMapping("/mailTemplate")
public class MailTemplateController extends BaseController
{
	/**
	 * Service to fetch template configurations.
	 */
	@Autowired
	private MailTemplateService mailTemplateService;

	/**
	 * Service to fetch mailTemplate .
	 */
	@Autowired
	private AgileProMailTemplateService agileMailTemplateService;

	/**
	 * The email notification service.
	 **/
	@Autowired
	private EmailNotificationService emailNotificationService;

	/**
	 * Fetch by owner.
	 *
	 * @param templateName
	 *            the template name
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ_BY_OWNER)
	@RequestMapping(value = "/readByOwner", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<AgileProMailTemplateModel> fetchByOwner(@RequestParam(value = "templateName", required = true) String templateName)
	{
		MailTemplateEntity mailTemplateEntity = emailNotificationService.getMailTemplate(templateName);
		AgileProMailTemplateModel model = WebUtils.convertBean(mailTemplateEntity, AgileProMailTemplateModel.class);

		RecipientInfoList recipientInfoLst = (RecipientInfoList) mailTemplateEntity.getCustomization();

		if(recipientInfoLst != null)
		{
			model.setToRecipientInfo(recipientInfoLst.getTo());
			model.setCcRecipientInfo(recipientInfoLst.getCc());
		}

		return new BasicReadResponse<AgileProMailTemplateModel>(model);
	}

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicVersionAndIdResponse save(@RequestBody @Valid AgileProMailTemplateModel model)
	{
		MailTemplateEntity enity = agileMailTemplateService.saveMailTemplate(model);

		if(enity == null)
		{
			return new BasicVersionAndIdResponse(enity.getVersion(), enity.getId());
		}
		else
		{
			return new BasicVersionAndIdResponse(enity.getVersion());
		}
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{

		mailTemplateService.deleteById(id);

		return new BaseResponse();
	}
}
