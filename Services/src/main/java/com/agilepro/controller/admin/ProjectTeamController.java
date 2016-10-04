package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_PROJECT_TEAM;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_BY_PROJECT_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.customer.ProjectTeamModel;
import com.agilepro.services.admin.ProjectTeamService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class ProjectTeamController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_PROJECT_TEAM)
@RequestMapping("/projectTeam")
public class ProjectTeamController extends BaseController
{
	/** 
	 * The project team service. 
	 **/
	@Autowired
	private ProjectTeamService projectTeamService;
	
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.PROJECT_TEAM_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid ProjectTeamModel projectTeamModel)
	{
		return new BasicSaveResponse(projectTeamService.save(projectTeamModel).getId());
	}
	
	@ActionName(ACTION_TYPE_READ_BY_PROJECT_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_TEAM_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readByProjectId", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<ProjectTeamModel>> fetchProjectMembers(@RequestParam(value = "projectId") Long projectId)
	{
		return new BasicReadResponse<List<ProjectTeamModel>>(projectTeamService.fetchAllTeamByProjectId(projectId));
	}
}
