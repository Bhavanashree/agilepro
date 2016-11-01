package com.agilepro.controller.scrum;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_SCRUM_MEETING_CONVERSATION;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import java.util.List;
import java.util.Map;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.agilepro.commons.controllers.scrum.IScrumMeetingConversationController;
import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.agilepro.controller.response.ScrumMeetingConversationReadResponse;
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
	 * Save.
	 *
	 * @param scrumMeetingConversationModel
	 *            the scrum meeting conversation model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid ScrumMeetingConversationModel scrumMeetingConversationModel)
	{
		return new BasicSaveResponse(scrumMeetingConversationService.save(scrumMeetingConversationModel).getId());
	}

	/**
	 * Fetch conversation.
	 *
	 * @param scrumMeetingId
	 *            the scrum meeting id
	 * @return the scrum meeting conversation read response
	 */
	@ActionName(ACTION_TYPE_READ_ALL)
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<Map<Integer, List<ScrumMeetingConversationModel>>> fetchConversation(@RequestParam(value = "scrumMeetingId", required = true) Long scrumMeetingId)
	{
		return new BasicReadResponse(scrumMeetingConversationService.fetchScrumMeetingConversation(scrumMeetingId));
	}
}
