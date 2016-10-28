package com.agilepro.commons.models.scrum;

import java.util.List;

import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class ScrumMeetingConversationReader for reading the conversation with
 * user.
 * 
 * @author Pritam
 */
@Model(name = "ScrumMeetingConversationReader")
public class ScrumMeetingConversationReader
{
	/**
	 * The user id.
	 **/
	private Long userId;

	/**
	 * The scrum meeting conversation models.
	 **/
	private List<ScrumMeetingConversationModel> scrumMeetingConversationModels;

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId
	 *            the new user id
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	/**
	 * Gets the scrum meeting conversation models.
	 *
	 * @return the scrum meeting conversation models
	 */
	public List<ScrumMeetingConversationModel> getScrumMeetingConversationModels()
	{
		return scrumMeetingConversationModels;
	}

	/**
	 * Sets the scrum meeting conversation models.
	 *
	 * @param scrumMeetingConversationModels
	 *            the new scrum meeting conversation models
	 */
	public void setScrumMeetingConversationModels(List<ScrumMeetingConversationModel> scrumMeetingConversationModels)
	{
		this.scrumMeetingConversationModels = scrumMeetingConversationModels;
	}
}
