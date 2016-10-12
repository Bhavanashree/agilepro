package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_PROJECT_REALSE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_BY_PROJECT_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL_PROJECT_AND_PROJECT_RELEASE_BY_RELEASE_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.customer.ProjectReleaseModel;
import com.agilepro.controller.response.ProjectReleaseReadResponse;
import com.agilepro.services.admin.ProjectReleaseService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class ProjectReleaseController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_PROJECT_REALSE)
@RequestMapping("/projectRelease")
public class ProjectReleaseController extends BaseController
{
	/**
	 * The project released service.
	 **/
	@Autowired
	private ProjectReleaseService projectReleaseService;

	/**
	 * Save.
	 *
	 * @param projectReleasedModel
	 *            the already released model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.PROJECT_RELEASE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid ProjectReleaseModel projectReleasedModel)
	{
		return new BasicSaveResponse(projectReleaseService.saveProjectRelease(projectReleasedModel).getId());
	}

	/**
	 * Fetch employees.
	 *
	 * @param releaseId
	 *            the release id
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ_ALL_PROJECT_AND_PROJECT_RELEASE_BY_RELEASE_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_RELEASE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAllProjectAndProjectReleaseByReleaseId", method = RequestMethod.GET)
	@ResponseBody
	public ProjectReleaseReadResponse fetchEmployees(@RequestParam(value = "releaseId", required = false) Long releaseId)
	{
		return projectReleaseService.fetchAllProjectRelease(releaseId);
	}

	/**
	 * Delete.
	 *
	 * @param projectReleasedModel
	 *            the project released model
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE_BY_PROJECT_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_RELEASE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/deleteByProjectId", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse delete(@RequestBody @Valid ProjectReleaseModel projectReleasedModel)
	{
		projectReleaseService.deleteByProjectId(projectReleasedModel);

		return new BaseResponse();
	}
}
