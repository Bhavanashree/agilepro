package com.agilepro.controller.bug;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE_TASK_CHANGES;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.List;

import javax.validation.Valid;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_BUG_TASK;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_BY_BUG_ID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.bug.BugTaskModel;
import com.agilepro.commons.models.project.TaskChangesModel;
import com.agilepro.services.bug.BugTaskService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * Bug task controller.
 * 
 * @author Pritam.
 */
@RestController
@ActionName(ACTION_PREFIX_BUG_TASK)
@RequestMapping("/bugTask")
public class BugTaskController extends BaseController
{
	/**
	 * Bug task service.
	 */
	@Autowired
	private BugTaskService bugTaskService; 
	
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = {UserRole.BUG_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse saveBugTask(@RequestBody BugTaskModel bugTaskModel)
	{
		return new BasicSaveResponse(bugTaskService.save(bugTaskModel).getId());
	}
	
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.TASK_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<BugTaskModel> read(@PathVariable(PARAM_ID) Long id)
	{
		return new BasicReadResponse<BugTaskModel>(bugTaskService.fetchFullModel(id, BugTaskModel.class));
	}
	
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.TASK_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid BugTaskModel model)
	{
		bugTaskService.update(model);
		return new BaseResponse();
	}
	
	@ActionName(ACTION_TYPE_READ_BY_BUG_ID)
	@Authorization(roles = {UserRole.BUG_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readByBugId", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<BugTaskModel>> fetchBugTasksByBug(@RequestParam(value = "bugId") Long bugId)
	{
		return new BasicReadResponse<List<BugTaskModel>>(bugTaskService.fetchBugTaskByBugId(bugId));
	}
	
	@ActionName(ACTION_TYPE_UPDATE_TASK_CHANGES)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.TASK_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/updateTaskChanges", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse updateTaskChanges(@RequestBody @Valid TaskChangesModel taskChanges)
	{
		bugTaskService.updateTaskChanges(taskChanges);
		return new BaseResponse();
	}
	
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.TASK_DELETE, UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		bugTaskService.deleteById(id);

		return new BaseResponse();
	}
}
