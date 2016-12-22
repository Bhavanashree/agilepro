package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_STORY_DEPENDENCY;
import static com.agilepro.commons.IAgileproActions.UPDATE_DEPENDENCY_TYPE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.StoryDependencyType;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.project.StoryDependencyModel;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.project.StoryDependencyService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.annotations.RequestParam;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * Story dependency controller is responsible for receiving the request from the client and sending back the response.
 * 
 * @author Pritam.
 */
@RestController
@ActionName(ACTION_PREFIX_STORY_DEPENDENCY)
@RequestMapping("/storyDependency")
public class StoryDependencyController extends BaseController
{
	/**
	 * StoryDependencyService. 
	 */
	@Autowired
	private StoryDependencyService storyDependencyService;
	
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@Authorization(roles = { UserRole.BACKLOG_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	public BasicSaveResponse save(@RequestBody @Valid StoryDependencyModel storyDependencyModel)
	{
		return new BasicSaveResponse(storyDependencyService.save(storyDependencyModel).getId());
	}
	
	@ActionName(UPDATE_DEPENDENCY_TYPE)
	@RequestMapping(value = "/updateDependencyType", method = RequestMethod.GET)
	@ResponseBody
	@Authorization(roles = { UserRole.BACKLOG_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	public BaseResponse updateDependencyType(@RequestParam(value = "id") Long id, 
											 @RequestParam(value = "storyDependencyType") StoryDependencyType storyDependencyType)
	{
		storyDependencyService.updateDependencyType(id, storyDependencyType);
		return new BaseResponse();
	}
}
