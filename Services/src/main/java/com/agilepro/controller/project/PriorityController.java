package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_PRIORITY;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.project.IPriorityController;
import com.agilepro.commons.models.project.PriorityModel;
import com.agilepro.persistence.entity.project.PriorityEntity;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.project.PriorityService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class PriorityController.
 */
@RestController
@ActionName(ACTION_PREFIX_PRIORITY)
@RequestMapping("/priority")
public class PriorityController extends BaseController implements IPriorityController
{
	/** 
	 * The priority service.
	 **/
	@Autowired
	private PriorityService priorityService;

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.PRIORITY_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid PriorityModel model)
	{

		PriorityEntity entity = priorityService.save(model);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PRIORITY_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<PriorityModel> read(@PathVariable(PARAM_ID) Long id)
	{
		PriorityModel priorityModel = priorityService.fetchFullModel(id, PriorityModel.class);

		return new BasicReadResponse<PriorityModel>(priorityModel);
	}

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.PRIORITY_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid PriorityModel model)
	{
		priorityService.update(model);

		return new BaseResponse();
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PRIORITY_DELETE, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		priorityService.deleteById(id);

		return new BaseResponse();
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@Authorization(roles = { UserRole.PRIORITY_DELETE_ALL, UserRole.CUSTOMER_SUPER_USER })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		priorityService.deleteAll();
		return new BaseResponse();
	}
}
