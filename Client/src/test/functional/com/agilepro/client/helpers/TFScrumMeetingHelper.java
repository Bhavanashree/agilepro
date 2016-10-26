package com.agilepro.client.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agilepro.commons.controllers.admin.ICustomerController;
import com.agilepro.commons.controllers.admin.IProjectController;
import com.agilepro.commons.controllers.scrum.IScrumMeetingController;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.ProjectModel;
import com.agilepro.commons.models.scrum.ScrumMeetingModel;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.ClientControllerFactory;

/**
 * The Class TFScrumMeetingHelper.
 * 
 * @author Pritam
 */
public class TFScrumMeetingHelper extends TFBase implements ITestConstants
{
	/** 
	 * The iscrum meeting controller. 
	 **/
	private IScrumMeetingController iscrumMeetingController;
	
	/** 
	 * The iproject controller. 
	 **/
	private IProjectController iprojectController;
	
	/** 
	 * The icustomer controller. *
	 */
	private ICustomerController icustomerController;
	
	/** 
	 * The projects. 
	 **/
	private List<ProjectModel> projects;
	
	/**
	 * Inits the prc cus.
	 */
	@BeforeClass
	public void initPrcCus()
	{
		clientControllerFactory = new ClientControllerFactory(clientContext);
		icustomerController = clientControllerFactory.getController(ICustomerController.class);
		CustomerModel customer = (CustomerModel) icustomerController.fetchCustomerByEmail(T_CUS_EMAIL_ID).getModel();  
				
		ClientContext customerSession = super.newClientContext(T_CUS_EMAIL_ID, T_PASSWORD, customer.getId());
		clientControllerFactory = new ClientControllerFactory(customerSession);
		
		iprojectController = clientControllerFactory.getController(IProjectController.class);
		
		//projects = (List<ProjectModel>) iprojectController.fetchProjects().getModel();
		
		iscrumMeetingController = clientControllerFactory.getController(IScrumMeetingController.class);
	}
	
	@Test
	public void testSaveScrumMeeting() throws ParseException
	{
		//projects.forEach(project -> iscrumMeetingController.saveScrumMeeting(new ScrumMeetingModel(project.getId())));
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
		
		iscrumMeetingController.saveScrumMeeting(new ScrumMeetingModel(1L, today));
	}
}
