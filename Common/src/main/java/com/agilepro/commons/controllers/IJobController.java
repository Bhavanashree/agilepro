package com.agilepro.commons.controllers;

import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * Controller interface to invoke jobs.
 * 
 * @author Pritam
 */
@RemoteService
public interface IJobController
{
	/**
	 * Invokes specified job.
	 * @param jobName Job to execute.
	 * @return Success/failure response.
	 */
	public BasicSaveResponse invokeJob(String jobName);
}
