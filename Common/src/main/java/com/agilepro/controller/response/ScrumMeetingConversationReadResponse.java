package com.agilepro.controller.response;

import java.util.List;

import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.yukthi.webutils.common.models.BasicReadResponse;

/**
 * The Class ScrumMeetingConversationReadResponse.
 * 
 * @author Pritam.
 */
public class ScrumMeetingConversationReadResponse extends BasicReadResponse<List<ScrumMeetingConversationModel>>
{
	/**
	 * The new user message.
	 **/
	private List<ScrumMeetingConversationModel> newUserMessage;

	/**
	 * Instantiates a new scrum meeting conversation read response.
	 *
	 * @param newUserMessage
	 *            the new user message
	 */
	public ScrumMeetingConversationReadResponse(List<ScrumMeetingConversationModel> newUserMessage)
	{
		super();
		this.newUserMessage = newUserMessage;
	}

	/**
	 * Gets the new user message.
	 *
	 * @return the new user message
	 */
	public List<ScrumMeetingConversationModel> getNewUserMessage()
	{
		return newUserMessage;
	}

	/**
	 * Sets the new user message.
	 *
	 * @param newUserMessage
	 *            the new new user message
	 */
	public void setNewUserMessage(List<ScrumMeetingConversationModel> newUserMessage)
	{
		this.newUserMessage = newUserMessage;
	}
}
