package com.agilepro.controller.response;

import java.util.List;
import com.agilepro.commons.models.scrum.ScrumMeetingConversationReader;
import com.yukthi.webutils.common.models.BasicReadResponse;

/**
 * The Class ScrumMeetingConversationReadResponse.
 * 
 * @author Pritam.
 */
public class ScrumMeetingConversationReadResponse extends BasicReadResponse<List<ScrumMeetingConversationReader>>
{
	private List<ScrumMeetingConversationReader> scrumMeetingConversationReaders;

	public ScrumMeetingConversationReadResponse(List<ScrumMeetingConversationReader> scrumMeetingConversationReaders) {
		super();
		this.scrumMeetingConversationReaders = scrumMeetingConversationReaders;
	}

	public List<ScrumMeetingConversationReader> getScrumMeetingConversationReaders() {
		return scrumMeetingConversationReaders;
	}

	public void setScrumMeetingConversationReaders(List<ScrumMeetingConversationReader> scrumMeetingConversationReaders) {
		this.scrumMeetingConversationReaders = scrumMeetingConversationReaders;
	}
}
