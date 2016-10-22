package com.agilepro.controller.bug;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_BUG_COMMENTS;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_BUG_COMMENT_BY_BUG_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
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

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.bug.IBugCommentController;
import com.agilepro.commons.models.bug.BugCommentsModel;
import com.agilepro.services.bug.BugCommentService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class BugCommentController.
 */
@RestController
@ActionName(ACTION_PREFIX_BUG_COMMENTS)
@RequestMapping("/bugComment")
public class BugCommentController extends BaseController implements IBugCommentController
{

	/**
	 * The bug comment service.
	 **/
	@Autowired
	private BugCommentService bugCommentService;

	/**
	 * Save the BugCommentModel.
	 *
	 * @param model
	 *            BugCommentModel
	 * @return the BugCommentModel save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = {UserRole.BUG_COMMENT_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid BugCommentsModel model)
	{
		bugCommentService.saveBugComment(model);
		return new BasicSaveResponse();
	}

	/**
	 * Read the Bug.
	 *
	 * @param id
	 *            id of BugCommentModel
	 * 
	 * @return the BugCommentModel read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = {UserRole.BUG_COMMENT_VIEW , UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<BugCommentsModel> read(@PathVariable(PARAM_ID) Long id)
	{
		BugCommentsModel model = bugCommentService.fetchFullModel(id, BugCommentsModel.class);

		return new BasicReadResponse<BugCommentsModel>(model);
	}

	@ActionName(ACTION_TYPE_READ_BUG_COMMENT_BY_BUG_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.BUG_COMMENT_VIEW, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT , UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readByBugId", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<BugCommentsModel>> fetchAllcomments(@RequestParam(value = "bugId", required = false) Long bug)
	{
		return new BasicReadResponse<List<BugCommentsModel>>(bugCommentService.fetchcommentsByBugId(bug));
	}

	/**
	 * Update the bug.
	 *
	 * @param model
	 *            id of BugCommentModel
	 * 
	 * @return the BugCommentModel response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = {UserRole.BUG_COMMENT_UPDATE, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid BugCommentsModel model)
	{
		bugCommentService.update(model);

		return new BaseResponse();
	}

	/**
	 * Delete the bugCommentModel.
	 *
	 * @param id
	 *            the id of bugComment
	 * 
	 * @return the bug save response
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = {UserRole.BUG_COMMENT_DELETE, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		bugCommentService.deleteById(id);

		return new BaseResponse();
	}
}
