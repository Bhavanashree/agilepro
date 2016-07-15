package com.agilepro.controller.projects;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_SPRINT;
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

import com.agilepro.commons.ISprintController;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.projects.SprintModel;
import com.agilepro.persistence.entity.projects.SprintEntity;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.projects.SprintService;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class SprintController.
 */
@RestController
@ActionName(ACTION_PREFIX_SPRINT)
@RequestMapping("/sprint")
public class SprintController extends BaseController implements ISprintController
{

	/**
	 *  The sprint service. 
	 */
	@Autowired
	private SprintService sprintService;

	/**
	 * Save Sprint.
	 *
	 * @param model
	 *            the model
	 * @return the Sprint save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.SPRINT_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid SprintModel model)
	{
		SprintEntity entity = sprintService.save(model);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read Backlog.
	 *
	 * @param id
	 *            the id
	 * @return the Backlog read response
	 */
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.SPRINT_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<SprintModel> read(@PathVariable(PARAM_ID) Long id)
	{
		SprintModel sprintModel = sprintService.fetchFullModel(id, SprintModel.class);

		return new BasicReadResponse<SprintModel>(sprintModel);
	}

	/**
	 * Update Backlog.
	 *
	 * @param model
	 *            the model
	 * @return the Backlog update response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.SPRINT_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid SprintModel model)
	{
		if(model.getId() == null || model.getId() <= 0)
		{
			throw new InvalidRequestParameterException("Invalid id specified for update: " + model.getId());
		}

		sprintService.update(model);

		return new BaseResponse();
	}

	/**
	 * Delete Backlog.
	 *
	 * @param id
	 *            the id
	 * @return the Backlog delete response
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.SPRINT_DELETE, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		sprintService.deleteById(id);

		return new BaseResponse();
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@Authorization(roles = { UserRole.SPRINT_DELETE_ALL, UserRole.CUSTOMER_SUPER_USER })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		sprintService.deleteAll();
		return new BaseResponse();
	}
}
