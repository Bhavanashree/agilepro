package com.agilepro.commons.controllers.scrum;

import com.agilepro.commons.models.scrum.ScrumMeetingModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IScrumMeetingController.
 * 
 * @author Pritam
 */
@RemoteService
public interface IScrumMeetingController
{
	public BasicSaveResponse saveScrumMeeting(ScrumMeetingModel scrumMeetingModel);
}
