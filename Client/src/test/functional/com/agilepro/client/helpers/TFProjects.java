package com.agilepro.client.helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agilepro.commons.PaymentCycle;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.ProjectsModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;

/**
 * The Class TFProjects.
 * 
 * @author Pritam
 */
public class TFProjects extends TFBase implements ITestConstants
{
	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(TFProjects.class);

	/**
	 * CustomerHelper object with default values.
	 */
	private CustomerHelper customerHelper = new CustomerHelper();

	/**
	 * CustomerPricePlanHelper object with default values.
	 */
	private CustomerPricePlanHelper pricePlanHelper = new CustomerPricePlanHelper();
	
	/** 
	 * The project helper. 
	 **/
	private ProjectHelper projectHelper = new ProjectHelper();

	/**
	 * customerId.
	 */
	private Long customerId;

	/**
	 * Password.
	 */
	private String password = "12345";

	/**
	 * mailId.
	 */
	private String emailId = "customer@gmail.com";

	/**
	 * Phone Number.
	 */
	private String phoneNumber = "1234567891";

	/**
	 * The due amount paid by customer.
	 */
	private final double dueAmount = 10000.0;

	/**
	 * The Session object.
	 */
	private ClientContext customerSession;

	/** 
	 * The current date. 
	 **/
	private Date currentDate = new Date();
	
	/** 
	 * The calendar. 
	 **/
	private Calendar calendar = Calendar.getInstance();
	
	/**
	 * Setup.
	 */
	@BeforeClass
	public void setup()
	{
		/**
		 * customer price plan.
		 **/
		CustomerPricePlanModel customerPricePlanModel = new CustomerPricePlanModel();
		customerPricePlanModel.setName("Test12");
		customerPricePlanModel.setPaymentCycle(PaymentCycle.Daily);
		CustomerPricePlanExpression ex = new CustomerPricePlanExpression();
		ex.setExpression("3+2");
		ex.setName("test11");
		ex.setLabel("pay");
		List<CustomerPricePlanExpression> listExp = new ArrayList<CustomerPricePlanExpression>();
		listExp.add(ex);
		customerPricePlanModel.setExpressions(listExp);
		long idOfPricePlan = pricePlanHelper.save(clientContext, customerPricePlanModel);
		logger.debug("Saved price plan with id - {}", idOfPricePlan);
		Assert.assertTrue(idOfPricePlan > 0);

		/**
		 * customer.
		 **/
		CustomerModel model = new CustomerModel("Test1", emailId, phoneNumber, null, null, null, null, new Date(), password, password, "path1", null);
		model.setCustomerPricePlanId(idOfPricePlan);
		model.setDueAmount(dueAmount);
		customerId = customerHelper.save(clientContext, model);
		logger.debug("Saved customer with id - {}", customerId);
		Assert.assertTrue(customerId > 0);

		customerSession = super.newClientContext(emailId, password, customerId);
	}

	/**
	 * Test save.
	 *
	 * @param projectsModel the projects model
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the long
	 */
	private long saveProject(ProjectsModel projectsModel, Integer startDate, Integer endDate)
	{
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, startDate);
		projectsModel.setStartDate(calendar.getTime());

		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, endDate);
		projectsModel.setEndDate(calendar.getTime());
		
		return projectHelper.save(customerSession, projectsModel);
	}
	
	/**
	 * Test save.
	 */
	@Test
	public void testSave()
	{
		ProjectsModel projectsModel = new ProjectsModel();
		projectsModel.setName("Project1");
		long projectId =  saveProject(projectsModel, START_DATE, END_DATE);
		
		logger.debug("Saved project with id - {}", projectId);
		Assert.assertTrue(projectId > 0);
	}

	@Test
	public void testSaveDate()
	{
		ProjectsModel projectsModel = new ProjectsModel();
		projectsModel.setName("Project2");

		saveProject(projectsModel, END_DATE, START_DATE);
	}
	
	@Test
	public void testRead()
	{
		String projectName = "Project3";
		ProjectsModel projectsModel = new ProjectsModel();
		projectsModel.setName(projectName);
		long projectId = saveProject(projectsModel, START_DATE, END_DATE);
		logger.debug("Saved project with id - {}", projectId);
		
		ProjectsModel  fetchedModel = projectHelper.read(customerSession, projectId);
		logger.debug("Reading model with id - {}", fetchedModel.getId());
		
		Assert.assertEquals(fetchedModel.getName(), projectName);
	}
	
	@Test
	public void testUpdate()
	{
		String projectOldName  = "Project4";
		String projectNewName = "Project5";
		
		// saving new project
		ProjectsModel projectsModel = new ProjectsModel();
		projectsModel.setName(projectOldName);
		long projectId = saveProject(projectsModel, START_DATE, END_DATE);
		
		// reading the saved project
		ProjectsModel fetchedModel = projectHelper.read(customerSession, projectId);
		Assert.assertEquals(fetchedModel.getName(), projectOldName);
		
		// updating the project
		fetchedModel.setName(projectNewName);
		projectHelper.update(customerSession, fetchedModel);
		ProjectsModel updateModel = projectHelper.read(customerSession, fetchedModel.getId());
		
		Assert.assertEquals(updateModel.getName(), projectNewName);
	}
	
	@Test
	public void testDelete()
	{
		ProjectsModel projectsModel = new ProjectsModel();
		projectsModel.setName("Project6");
		long projectId = saveProject(projectsModel, START_DATE, END_DATE);

		projectHelper.delete(customerSession, projectId);
		
		ProjectsModel fetchedModel = projectHelper.read(customerSession, projectId);
		Assert.assertNull(fetchedModel);
	}
	
	/**
	 * cleanup.
	 */
	@AfterClass
	public void cleanup()
	{
		projectHelper.deleteAll(customerSession);
		
		customerHelper.deleteAll(clientContext);
		pricePlanHelper.deleteAll(clientContext);
	}
}
