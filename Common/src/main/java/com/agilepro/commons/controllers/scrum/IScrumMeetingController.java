package com.agilepro.commons.controllers.scrum;

import java.util.Date;
import com.agilepro.commons.models.scrum.ScrumMeetingModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BasicReadResponse;

/**
 * The Interface IScrumMeetingController.
 * 
 * @author Pritam
 */
@RemoteService
public interface IScrumMeetingController
{
	public BasicReadResponse<ScrumMeetingModel> fetchMeetings(Date date, Long projectId);
}
