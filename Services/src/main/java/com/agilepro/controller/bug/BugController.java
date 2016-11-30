package com.agilepro.controller.bug;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_BUG;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_BUG_BY_SPRINT_ID;

import static com.agilepro.commons.IAgileproActions.PARAM_ID;

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

import com.agilepro.commons.BasicVersionResponse;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.bug.IBugController;
import com.agilepro.commons.models.bug.BugModel;
import com.agilepro.services.bug.BugService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class BugController.
 */
@RestController
@ActionName(ACTION_PREFIX_BUG)
@RequestMapping("/bug")
public class BugController extends BaseController implements IBugController
{

	/**
	 * The bug service.
	 **/
	@Autowired
	private BugService bugService;

	/**
	 * Save the BugModel.
	 *
	 * @param model
	 *            BugModel
	 * @return the BugModel save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = {UserRole.BUG_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid BugModel model)
	{
		bugService.save(model);
		return new BasicSaveResponse();
	}

	/**
	 * Read the Bug.
	 *
	 * @param id
	 *            id of BugModel
	 * 
	 * @return the BugModel read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = {UserRole.BUG_VIEW, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<BugModel> read(@PathVariable(PARAM_ID) Long id)
	{
		BugModel model = bugService.fetchFullModel(id, BugModel.class);

		return new BasicReadResponse<BugModel>(model);	
	}

	/**
	 * Update the bug.
	 *
	 * @param model
	 *            id of BugModel
	 * 
	 * @return the BugModel response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = {UserRole.BUG_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BasicVersionResponse update(@RequestBody @Valid BugModel model)
	{
		
		if(model.getId() == null || model.getId() <= 0)
		{
			throw new InvalidRequestParameterException("Invalid id specified for update: " + model.getId());
		}

		Integer updatedVersion = bugService.updateBug(model);

		return new BasicVersionResponse(updatedVersion);
	}

	/**
	 * Delete the bug.
	 *
	 * @param id
	 *            the id of bug
	 * 
	 * @return the bug base response
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = {UserRole.BUG_DELETE, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		bugService.deleteById(id);

		return new BaseResponse();
	}
	
	@ActionName(ACTION_TYPE_READ_BUG_BY_SPRINT_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = {UserRole.BUG_DELETE, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/fetchBugBySprintId", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<BugModel>> fetchBugs(@RequestParam(value = "projectId", required = true) Long projectId, @RequestParam(value = "sprintId", required = true)Long sprintId)
	{
		return new BasicReadResponse<List<BugModel>>(bugService.fetchBugsBySprint(projectId, sprintId));
	}
}
