package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_STORY_ATTACHMENT_MESSAGE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.List;

import javax.validation.Valid;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.project.StoryAttachmentModel;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.project.StoryAttachmentService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.annotations.AttachmentsExpected;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * The Class StoryAttachmentController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_STORY_ATTACHMENT_MESSAGE)
@RequestMapping("/storyAttachment")
public class StoryAttachmentController extends BaseController
{
	/**
	 * The story attachment service.
	 **/
	@Autowired
	private StoryAttachmentService storyAttachmentService;

	/**
	 * Save.
	 *
	 * @param storyAttachmentModel
	 *            the story attachment model
	 * @param request
	 *            the request
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AttachmentsExpected
	public BasicSaveResponse save(@RequestPart(IWebUtilsCommonConstants.MULTIPART_DEFAULT_PART) @Valid StoryAttachmentModel storyAttachmentModel, MultipartHttpServletRequest request)
	{
		return new BasicSaveResponse(storyAttachmentService.save(storyAttachmentModel).getId());
	}

	/**
	 * Update.
	 *
	 * @param storyAttachmentModel
	 *            the story attachment model
	 * @param request
	 *            the request
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AttachmentsExpected
	public BaseResponse update(@RequestPart(IWebUtilsCommonConstants.MULTIPART_DEFAULT_PART) @Valid StoryAttachmentModel storyAttachmentModel, MultipartHttpServletRequest request)
	{
		storyAttachmentService.update(storyAttachmentModel);

		return new BaseResponse();
	}
	
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		storyAttachmentService.deleteById(id);
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
	public BasicReadResponse<List<StoryAttachmentModel>> fetchAttachments(@RequestParam(value = "storyId") Long storyId)
	{
		return new BasicReadResponse<List<StoryAttachmentModel>>(storyAttachmentService.fetchAttachments(storyId));
	}
}
