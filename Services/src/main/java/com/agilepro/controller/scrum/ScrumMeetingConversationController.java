package com.agilepro.controller.scrum;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_SCRUM_MEETING_CONVERSATION;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ACTION_BY_SCRUM_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ACTION_CONVERSATION_BY_SCRUM_ACTION_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE_ACTION_CONVERSATION;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.scrum.IScrumMeetingConversationController;
import com.agilepro.commons.models.scrum.ScrumActionItemConversationModel;
import com.agilepro.commons.models.scrum.ScrumActionItemModel;
import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.scrum.ScrumActionItemConversationService;
import com.agilepro.services.scrum.ScrumActionItemService;
import com.agilepro.services.scrum.ScrumMeetingConversationService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class ScrumMeetingConversationController.
 * 
 * @author Pritam
 */
@ActionName(ACTION_PREFIX_SCRUM_MEETING_CONVERSATION)
@RestController
@RequestMapping("/scrumMeetingConversation")
public class ScrumMeetingConversationController extends BaseController implements IScrumMeetingConversationController
{
	/**
	 * The scrum meeting conversation service.
	 **/
	@Autowired
	private ScrumMeetingConversationService scrumMeetingConversationService;

	/**
	 * The scrum action item service.
	 **/
	@Autowired
	private ScrumActionItemService scrumActionItemService;

	/**
	 * The scrum action item conversation service.
	 **/
	@Autowired
	private ScrumActionItemConversationService scrumActionItemConversationService;

	/**
	 * Save.
	 *
	 * @param scrumMeetingConversationModel
	 *            the scrum meeting conversation model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.SCRUM_MEETING_CONVERSATION_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid ScrumMeetingConversationModel scrumMeetingConversationModel)
	{
		return new BasicSaveResponse(scrumMeetingConversationService.saveScrumConversation(scrumMeetingConversationModel).getId());
	}

	/**
	 * Fetch conversation.
	 *
	 * @param scrumMeetingId
	 *            the scrum meeting id
	 * @return the scrum meeting conversation read response
	 */
	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.SCRUM_MEETING_CONVERSATION_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<Map<Integer, List<ScrumMeetingConversationModel>>> fetchConversation(@RequestParam(value = "scrumMeetingId", required = true) Long scrumMeetingId)
	{
		return new BasicReadResponse<Map<Integer, List<ScrumMeetingConversationModel>>>(scrumMeetingConversationService.fetchScrumMeetingConversation(scrumMeetingId));
	}

	/**
	 * Fetch action items.
	 *
	 * @param scrumMeetingId
	 *            the scrum meeting id
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ_ACTION_BY_SCRUM_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.SCRUM_MEETING_CONVERSATION_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readActionByScrumId", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<ScrumActionItemModel>> fetchActionItems(@RequestParam(value = "scrumMeetingId", required = true) Long scrumMeetingId)
	{
		return new BasicReadResponse<List<ScrumActionItemModel>>(scrumActionItemService.fetchActionByScrum(scrumMeetingId));
	}

	/**
	 * Fetch action conversation by scrum id.
	 *
	 * @param scrumActionItemId
	 *            the scrum action item id
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ_ACTION_CONVERSATION_BY_SCRUM_ACTION_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.SCRUM_MEETING_CONVERSATION_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readActionConversationByScrumActionId", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<Map<Integer, List<ScrumActionItemConversationModel>>> fetchActionConversation(@RequestParam(value = "scrumActionItemId", required = true) Long scrumActionItemId)
	{
		return new BasicReadResponse<Map<Integer, List<ScrumActionItemConversationModel>>>(scrumActionItemConversationService.fetchByActionId(scrumActionItemId));
	}

	@ActionName(ACTION_TYPE_SAVE_ACTION_CONVERSATION)
	@Authorization(roles = { UserRole.SCRUM_MEETING_CONVERSATION_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/saveActionConversation", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse saveActionConversation(@RequestBody @Valid ScrumActionItemConversationModel model)
	{
		return new BasicSaveResponse(scrumActionItemConversationService.saveActionConversation(model).getId());
	}
}
