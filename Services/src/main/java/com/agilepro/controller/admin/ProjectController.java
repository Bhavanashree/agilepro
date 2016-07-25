package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_PROJECT;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.admin.IProjectController;
import com.agilepro.commons.models.customer.ProjectModel;
import com.agilepro.services.admin.ProjectService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.annotations.AttachmentsExpected;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;

/**
 * The Class ProjectController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_PROJECT)
@RequestMapping("/project")
public class ProjectController extends BaseController implements IProjectController<MultipartHttpServletRequest>
{
	/**
	 * The projects service.
	 **/
	@Autowired
	private ProjectService projectService;

	/**
	 * Check dates.
	 *
	 * @param projectModel
	 *            the projects model
	 */
	private void checkDates(ProjectModel projectModel)
	{
		Date startDate = projectModel.getStartDate();
		Date endDate = projectModel.getEndDate();

		// if end date is provided
		if(endDate != null)
		{
			if(startDate == null)
			{
				throw new InvalidRequestParameterException("Start date is mandatory for end date");
			}
			else if((endDate.before(startDate)))
			{
				throw new InvalidRequestParameterException("End date should be after start date");
			}
		}
	}

	/**
	 * Save.
	 *
	 * @param projectModel
	 *            the projects model
	 * @param request
	 *            the request
	 * @return the basic save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.PROJECT_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AttachmentsExpected
	public BasicSaveResponse save(@RequestPart(IWebUtilsCommonConstants.MULTIPART_DEFAULT_PART) @Valid ProjectModel projectModel, MultipartHttpServletRequest request)
	{
		checkDates(projectModel);

		return new BasicSaveResponse(projectService.save(projectModel).getId());
	}

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<ProjectModel> read(@PathVariable(PARAM_ID) Long id)
	{
		return new BasicReadResponse<ProjectModel>(projectService.fetchFullModel(id, ProjectModel.class));
	}

	/**
	 * Update.
	 *
	 * @param projectModel
	 *            the projects model
	 * @param request
	 *            the request
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.PROJECT_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AttachmentsExpected
	public BaseResponse update(@RequestPart(IWebUtilsCommonConstants.MULTIPART_DEFAULT_PART) @Valid ProjectModel projectModel, MultipartHttpServletRequest request)
	{
		checkDates(projectModel);

		projectService.update(projectModel);
		return new BaseResponse();
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
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		projectService.deleteById(id);
		return new BaseResponse();
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@Override
	@Authorization(roles = { UserRole.TEST_DELETE_ALL, UserRole.CUSTOMER_SUPER_USER })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		projectService.deleteAll();
		return new BaseResponse();
	}

	/**
	 * Fetch projects.
	 *
	 * @return the basic read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.PROJECT_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<ProjectModel>> fetchProjects()
	{
		return new BasicReadResponse<List<ProjectModel>>(projectService.fetchProjects());
	}
}
