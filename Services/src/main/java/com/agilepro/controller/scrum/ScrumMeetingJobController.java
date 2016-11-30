package com.agilepro.controller.scrum;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_EXECUTE;
import static com.agilepro.commons.IAgileproActions.JOB_NAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agilepro.commons.controllers.scrum.IScrumMeetingJobController;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.services.job.JobService;

/**
 * ScrumMeetingJobController for testing execution of scrum metting job.
 * 
 * @author Pritam.
 */
public class ScrumMeetingJobController implements IScrumMeetingJobController
{
	/**
	 * Job service to invoke jobs.
	 */
	@Autowired
	private JobService jobService;
	  
	/* (non-Javadoc)
	 * @see com.agilepro.commons.controllers.scrum.IScrumMeetingJobController#saveScrumMeetingJob(java.lang.String)
	 */
	@Override
	@ActionName(ACTION_TYPE_EXECUTE)
	@RequestMapping(value = "/execute/{" + JOB_NAME + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicSaveResponse save(@PathVariable(JOB_NAME) String jobName)
	{
		jobService.executeJob(jobName);
		
		return new BasicSaveResponse();
	}
}
