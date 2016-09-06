package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_CONVERSATION_TITLE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
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
import com.agilepro.commons.controllers.admin.IConversationTitleController;
import com.agilepro.commons.models.project.ConversationTitleModel;
import com.agilepro.persistence.entity.project.ConversationTitleEntity;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.project.ConversationTitleService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class ConversationTitleController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_CONVERSATION_TITLE)
@RequestMapping("/conversationTitle")
public class ConversationTitleController extends BaseController implements IConversationTitleController
{
	/**
	 * The conversation title service.
	 **/
	@Autowired
	private ConversationTitleService conversationTitleService;

	/**
	 * Save.
	 *
	 * @param conversationTitleModel
	 *            the conversation title model
	 * @return the basic save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Authorization(roles = { UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid ConversationTitleModel conversationTitleModel)
	{
		ConversationTitleEntity entity = conversationTitleService.save(conversationTitleModel);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Fetch conversations.
	 *
	 * @param storyId
	 *            the story id
	 * @return the basic read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<ConversationTitleModel>> fetchConversations(@RequestParam(value = "storyId") Long storyId)
	{
		return new BasicReadResponse<List<ConversationTitleModel>>(conversationTitleService.fetchConversationTitles(storyId));
	}
}
