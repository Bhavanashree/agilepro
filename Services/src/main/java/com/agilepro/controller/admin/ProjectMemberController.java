package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_PROJECT_MEMBER;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.List;

import javax.validation.Valid;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.admin.IProjectMemberController;
import com.agilepro.commons.models.customer.ProjectMemberModel;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.agilepro.services.admin.ProjectMemberService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
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
	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_MEMBER_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<ProjectMemberModel>> fetchProjectMembers(@RequestParam(value = "projectId") Long projectId)
	{

		return new BasicReadResponse<List<ProjectMemberModel>>(projectMemberService.fetchProjectMembers(projectId));
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
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_MEMBER_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		projectMemberService.deleteByEmployeeId(id);

		return new BaseResponse();
	}
}
