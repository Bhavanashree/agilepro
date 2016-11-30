package com.agilepro.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.controllers.notification.AgileProMailTemplateModel;
import com.agilepro.commons.controllers.notification.RecipientInfoList;
import com.agilepro.controller.AgileProUserDetails;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.yukthi.webutils.mail.template.MailTemplateEntity;
import com.yukthi.webutils.mail.template.MailTemplateService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.utils.WebUtils;

/**
 * The Class AgileProMailTemplateService.
 */
@Service
public class AgileProMailTemplateService
{

	/**
	 * The mail template service.
	 **/
	@Autowired
	private MailTemplateService mailTemplateService;

	/**
	 * The current user service.
	 **/
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * Save mail template.
	 *
	 * @param model
	 *            the model
	 * @return the mail template entity
	 */
	public MailTemplateEntity saveMailTemplate(AgileProMailTemplateModel model)
	{
		AgileProUserDetails userDetails = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		MailTemplateEntity enity = mailTemplateService.fetchByOwner(model.getTemplateName(), CustomerEntity.class.getName(), userDetails.getCustomerId());

		if(enity == null)
		{
			enity = WebUtils.convertBean(model, MailTemplateEntity.class);
			enity.setOwnerEntityType(CustomerEntity.class.getName());
			enity.setOwnerEntityId(userDetails.getCustomerId());

			enity.setCustomization(new RecipientInfoList(model.getToRecipientInfo(), model.getCcRecipientInfo()));

			enity = mailTemplateService.save(enity);

			return enity;
		}
		else
		{
			enity = WebUtils.convertBean(model, MailTemplateEntity.class);

			enity.setOwnerEntityType(CustomerEntity.class.getName());
			enity.setOwnerEntityId(userDetails.getCustomerId());

			enity.setCustomization(new RecipientInfoList(model.getToRecipientInfo(), model.getCcRecipientInfo()));

			mailTemplateService.update(enity);
			return enity;
		}
	}
}
