package com.agilepro.commons.controllers.scrum;

import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IScrumMeetingConversationController.
 * 
 * @author Pritam
 */
@RemoteService
public interface IScrumMeetingConversationController
{
	public BasicSaveResponse save(ScrumMeetingConversationModel scrumMeetingConversationModel);
}
