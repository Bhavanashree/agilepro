package com.agilepro.commons.controllers.scrum;

import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * IScrumMeetingJobController for testing the scrum job.
 * 
 * @author Pritam.
 */
@RemoteService
public interface IScrumMeetingJobController
{
	public BasicSaveResponse save(String jobName);
}
