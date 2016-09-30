package com.agilepro.controller.notification;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_MAIL_TEMPLATE_DEFINITION;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_SPRINT_PROJECT_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.notification.IMailTemplateDefinitionController;
import com.agilepro.commons.models.notification.MailTemplateDefinitionModel;
import com.agilepro.commons.models.project.SprintModel;
import com.agilepro.persistence.entity.notification.MailTemplateDefinitionEntity;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.notification.MailTemplateDefinitionService;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class MailTemplateDefinitionController.
 */
@RestController
@ActionName(ACTION_PREFIX_MAIL_TEMPLATE_DEFINITION)
@RequestMapping("/mailTemplteDefinition")
public class MailTemplateDefinitionController extends BaseController implements IMailTemplateDefinitionController
{

	/**
	 * The maildefinition service.
	 **/
	@Autowired
	private MailTemplateDefinitionService maildefinitionService;

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid MailTemplateDefinitionModel model)
	{
		MailTemplateDefinitionEntity mailEntity = maildefinitionService.save(model);

		return new BasicSaveResponse(mailEntity.getId());
	}

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<MailTemplateDefinitionModel> read(@PathVariable(PARAM_ID) Long id)
	{

		MailTemplateDefinitionModel mailModel = maildefinitionService.fetchFullModel(id, MailTemplateDefinitionModel.class);

		return new BasicReadResponse<MailTemplateDefinitionModel>(mailModel);
	}

	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.SPRINT_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<MailTemplateDefinitionModel>> fetchAllMailDefinitions()
	{
		return new BasicReadResponse<List<MailTemplateDefinitionModel>>(maildefinitionService.fetchAllMailDefinitionTemplate());
	}

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid MailTemplateDefinitionModel model)
	{
		if(model.getId() == null || model.getId() <= 0)
		{
			throw new InvalidRequestParameterException("Invalid id specified for update: " + model.getId());
		}

		maildefinitionService.update(model);

		return new BaseResponse();
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		maildefinitionService.deleteById(id);

		return new BaseResponse();
	}
}
