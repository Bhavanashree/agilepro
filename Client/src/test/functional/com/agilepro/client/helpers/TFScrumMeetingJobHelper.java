package com.agilepro.client.helpers;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agilepro.commons.controllers.scrum.IScrumMeetingJobController;

/**
 * The Class TFScrumMeetingJobHelper.Testing the cron job.
 * 
 * @author Pritam
 */
public class TFScrumMeetingJobHelper extends TFBase implements ITestConstants
{
	/** 
	 * The iscrum meeting job controller. 
	 */
	private IScrumMeetingJobController iscrumMeetingJobController;
	
	@BeforeClass
	public void initScrum()
	{
		iscrumMeetingJobController = clientControllerFactory.getController(IScrumMeetingJobController.class);
	}
	
	@Test
	public void saveScrumMeetingJob()
	{
		iscrumMeetingJobController.save("ScrumMeetingJob");
	}
}
