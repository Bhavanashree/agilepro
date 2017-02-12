package com.agilepro.controller.bug;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_BUG_TASK;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.bug.BugTaskModel;
import com.agilepro.services.bug.BugTaskService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
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
	private BugTaskService bugTaskService; 
	
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = {UserRole.BUG_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse saveBugTask(@RequestBody BugTaskModel bugTaskModel)
	{
		return new BasicSaveResponse(bugTaskService.save(bugTaskModel).getId());
	}
}
