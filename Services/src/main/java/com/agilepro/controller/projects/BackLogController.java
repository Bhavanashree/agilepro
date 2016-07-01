package com.agilepro.controller.projects;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_BACKLOG;
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

import com.agilepro.commons.IBackLogController;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.projects.BackLogModel;
import com.agilepro.persistence.entity.projects.BackLogEntity;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.projects.BackLogService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class BackLogController.
 */
@RestController
@ActionName(ACTION_PREFIX_BACKLOG)
@RequestMapping("/backlog")
public class BackLogController extends BaseController implements IBackLogController
{
	/**
	 * service to fetch Backlog.
	 */
	@Autowired
	private BackLogService backLogService;

	/**
	 * Save Backlog.
	 *
	 * @param model
	 *            the model
	 * @return the Backlog save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.BACKLOG_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid BackLogModel model)
	{
		BackLogEntity entity = backLogService.save(model);

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
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.BACKLOG_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<BackLogModel> read(@PathVariable(PARAM_ID) Long id)
	{
		BackLogModel backLogModel = backLogService.fetchFullModel(id, BackLogModel.class);

		return new BasicReadResponse<BackLogModel>(backLogModel);
	}

	/**
	 * Update Backlog.
	 *
	 * @param model
	 *            the model
	 * @return the Backlog update response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.BACKLOG_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid BackLogModel model)
	{
		backLogService.update(model);

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
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.BACKLOG_DELETE, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		backLogService.deleteById(id);

		return new BaseResponse();
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@Authorization(roles = { UserRole.BACKLOG_DELETE_ALL, UserRole.CUSTOMER_SUPER_USER })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		backLogService.deleteAll();
		return new BaseResponse();
	}
}
