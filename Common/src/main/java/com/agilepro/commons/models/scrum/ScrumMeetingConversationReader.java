package com.agilepro.commons.models.scrum;

import java.util.List;

import com.yukthi.webutils.common.annotations.IgnoreField;
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
	 * The scrum meeting conversation models.
	 **/
	@IgnoreField
	private List<ScrumMeetingConversationModel> scrumMeetingConversationModels;

	public ScrumMeetingConversationReader(List<ScrumMeetingConversationModel> scrumMeetingConversationModels) {
		super();
		this.scrumMeetingConversationModels = scrumMeetingConversationModels;
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
