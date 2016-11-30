package com.agilepro.controller;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_JOBS;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_EXECUTE;
import static com.agilepro.commons.IAgileproActions.JOB_NAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.IJobController;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;
import com.yukthi.webutils.services.job.JobService;

/**
 * Job controller which helps in invoking jobs.
 * @author akiran
 */
@ActionName(ACTION_PREFIX_JOBS)
@RequestMapping("/jobs")
@RestController
public class JobController extends BaseController implements IJobController
{
	/**
	 * Job service to invoke jobs.
	 */
	@Autowired
	private JobService jobService;
	  
	/* (non-Javadoc)
	 * @see com.agilepro.commons.controllers.IJobController#invokeJob(java.lang.String)
	 */
	@Override
	//@Authorization(roles = UserRole.TEST)
	@ActionName(ACTION_TYPE_EXECUTE)
	@RequestMapping(value = "/execute/{" + JOB_NAME + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicSaveResponse invokeJob(@PathVariable(JOB_NAME) String jobName)
	{
		jobService.executeJob(jobName);
		
		return new BasicSaveResponse();
	}
}
