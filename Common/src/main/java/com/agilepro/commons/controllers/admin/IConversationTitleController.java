package com.agilepro.commons.controllers.admin;

import java.util.List;
import com.agilepro.commons.models.project.ConversationTitleModel;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IConversationTitleController.
 * 
 * @author Pritam
 */
public interface IConversationTitleController
{
	/**
	 * Save.
	 *
	 * @param conversationTitleModel
	 *            the conversation title model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(ConversationTitleModel conversationTitleModel);

	/**
	 * Fetch conversations.
	 *
	 * @param storyId
	 *            the story id
	 * @return the basic read response
	 */
	public BasicReadResponse<List<ConversationTitleModel>> fetchConversations(Long storyId);
}
