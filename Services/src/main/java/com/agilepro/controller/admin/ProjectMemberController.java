package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_PROJECT_MEMBER;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_BY_EMPLOYEE_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ADMIN_MANAGERS_BY_PROEJCT_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_MEMBERS_BY_PROEJCT_ID;

import javax.validation.Valid;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.admin.IProjectMemberController;
import com.agilepro.commons.models.customer.ProjectMemberModel;
import com.agilepro.controller.response.ProjectMemberReadResponse;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.agilepro.services.admin.ProjectMemberService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class ProjectMemberController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_PROJECT_MEMBER)
@RequestMapping("/projectMember")
public class ProjectMemberController extends BaseController implements IProjectMemberController
{
	/**
	 * The project members service.
	 **/
	@Autowired
	private ProjectMemberService projectMemberService;

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.PROJECT_MEMBER_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid ProjectMemberModel model)
	{
		ProjectMemberEntity projectMembersEntity = projectMemberService.save(model);

		return new BasicSaveResponse(projectMembersEntity.getId());
	}

	@Override
	@ActionName(ACTION_TYPE_READ_ADMIN_MANAGERS_BY_PROEJCT_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_MEMBER_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAdminManagersByProjectId", method = RequestMethod.GET)
	@ResponseBody
	public ProjectMemberReadResponse fetchAdminManagers(@RequestParam(value = "projectId") Long projectId)
	{
		return projectMemberService.fetchProjectAdminManagers(projectId);
	}
	
	@ActionName(ACTION_TYPE_READ_MEMBERS_BY_PROEJCT_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_MEMBER_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readMembersByProjectId", method = RequestMethod.GET)
	@ResponseBody
	public ProjectMemberReadResponse fetchMembers(@RequestParam(value = "projectTeamId") Long projectTeamId)
	{
		return projectMemberService.fetchProjectMembers(projectTeamId);
	}
	
	/* (non-Javadoc)
	 * @see com.agilepro.commons.controllers.admin.IProjectMemberController#delete(java.lang.Long)
	 */
	@Override
	@ActionName(ACTION_TYPE_DELETE_BY_EMPLOYEE_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_MEMBER_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/deleteByEmployeeId", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse delete(@RequestParam(value = "employeeId") Long employeeId)
	{
		projectMemberService.deleteByEmployee(employeeId);

		return new BaseResponse();
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@Authorization(roles = { UserRole.TEST, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		projectMemberService.deleteAll();
		return new BaseResponse();
	}
}
