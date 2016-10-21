package com.agilepro.controller.bug;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_BUG_ATTACHMENT_MESSAGE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.bug.BugAttachmentModel;
import com.agilepro.commons.models.project.StoryAttachmentModel;
import com.agilepro.services.bug.BugAttachmentService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.annotations.AttachmentsExpected;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class BugAttachmentController.
 */
@RestController
@ActionName(ACTION_PREFIX_BUG_ATTACHMENT_MESSAGE)
@RequestMapping("/bugAttachment")
public class BugAttachmentController extends BaseController
{
	/**
	 * The exception message.
	 **/
	private static String EXCEPTION_MESSAGE = "Please provide at least one value for file and link";

	/**
	 * The bug attachment service.
	 **/
	@Autowired
	private BugAttachmentService bugAttachmentService;

	/**
	 * Checks if is file or link.
	 *
	 * @param bugAttachmentModel
	 *            the bug attachment model
	 * @return true, if is file or link
	 */
	private boolean isFileOrLink(BugAttachmentModel bugAttachmentModel)
	{
		if(bugAttachmentModel.getFile() == null && bugAttachmentModel.getLink() == null)
		{
			return true;
		}

		return false;
	}

	/**
	 * Save.
	 *
	 * @param bugAttachmentModel
	 *            the bug attachment model
	 * @param request
	 *            the request
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AttachmentsExpected
	public BasicSaveResponse save(@RequestPart(IWebUtilsCommonConstants.MULTIPART_DEFAULT_PART) @Valid BugAttachmentModel bugAttachmentModel, MultipartHttpServletRequest request)
	{
		if(isFileOrLink(bugAttachmentModel))
		{
			throw new IllegalArgumentException(EXCEPTION_MESSAGE);
		}

		return new BasicSaveResponse(bugAttachmentService.save(bugAttachmentModel).getId());
	}

	/**
	 * Update.
	 *
	 * @param bugAttachment
	 *            the bug attachment
	 * @param request
	 *            the request
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AttachmentsExpected
	public BaseResponse update(@RequestPart(IWebUtilsCommonConstants.MULTIPART_DEFAULT_PART) @Valid BugAttachmentModel bugAttachment, MultipartHttpServletRequest request)
	{
		if(isFileOrLink(bugAttachment))
		{
			throw new IllegalArgumentException(EXCEPTION_MESSAGE);
		}

		bugAttachmentService.update(bugAttachment);

		return new BaseResponse();
	}
	
	/**
	 * Fetch attachments.
	 *
	 * @param storyId
	 *            the story id
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<BugAttachmentModel>> fetchAttachments(@RequestParam(value = "bugId") Long bugId)
	{
		return new BasicReadResponse<List<BugAttachmentModel>>(bugAttachmentService.fetchAttachments(bugId));
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		bugAttachmentService.deleteById(id);
		return new BaseResponse();
	}
}
