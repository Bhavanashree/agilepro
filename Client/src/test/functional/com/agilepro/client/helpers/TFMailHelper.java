package com.agilepro.client.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agilepro.commons.PaymentCycle;
import com.agilepro.commons.controllers.admin.IDesignationController;
import com.agilepro.commons.controllers.admin.IEmployeeController;
import com.agilepro.commons.models.admin.DesignationModel;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.ClientControllerFactory;

/**
 * The Class TFMailHelper.
 * 
 * @author Pritam
 */
public class TFMailHelper extends TFBase implements ITestConstants
{
	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(TFMailHelper.class);

	/**
	 * The customer price plan helper.
	 **/
	private static CustomerPricePlanHelper customerPricePlanHelper = new CustomerPricePlanHelper();

	/**
	 * The customer helper.
	 **/
	private static CustomerHelper customerHelper = new CustomerHelper();
	
	/**
	 * The due amount paid by customer.
	 */
	private final double dueAmount = 10000.0;
	
	/**
	 * customerId.
	 */
	private Long customerId;
	
	/**
	 * The Session object.
	 */
	private ClientContext customerSession;
	
	/** 
	 * The employee sesssion. 
	 **/
	private ClientContext employeeSesssion;
	
	/**
	 * The active user id.
	 **/
	private Long activeUserId;
	
	/**
	 * The employee id.
	 **/
	private Long employeeId;
	
	/**
	 * The idesignation controller.
	 **/
	private IDesignationController idesignationController;

	/**
	 * The iemployee controller.
	 **/
	private IEmployeeController iemployeeController;
	
	/**
	 * The designation id.
	 **/
	private Long designationId;
	
	/**
	 * Inits the prc cus.
	 */
	@BeforeClass
	public void initPrcCus()
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

		// save price plan
		long idOfPricePlan = customerPricePlanHelper.save(clientContext, customerPricePlanModel);
		logger.debug("Saved price plan with id - {}", idOfPricePlan);
		Assert.assertTrue(idOfPricePlan > 0);

		/**
		 * customer.
		 **/
		CustomerModel model = new CustomerModel("Customer1", T_CUS_EMAIL_ID, T_PHONE_NUMBER, null, null, null, null, new Date(), T_PASSWORD, T_PASSWORD, T_CUS_PATH, null);
		model.setCustomerPricePlanId(idOfPricePlan);
		model.setDueAmount(dueAmount);
		customerId = customerHelper.save(clientContext, model);
		Assert.assertTrue(idOfPricePlan > 0);
		Assert.assertTrue(customerId > 0);

		customerSession = super.newClientContext(T_CUS_EMAIL_ID, T_PASSWORD, customerId);

		activeUserId = customerSession.getUserId();

		clientControllerFactory = new ClientControllerFactory(customerSession);
		
		createDesignation();

		createEmployee();
		
		employeeSesssion = super.newClientContext("employee1@gmail.com", "12345", employeeId);
		
		clientControllerFactory = new ClientControllerFactory(employeeSesssion);
	}
	
	/**
	 * Creates the designation.
	 */
	public void createDesignation()
	{
		idesignationController = clientControllerFactory.getController(IDesignationController.class);

		DesignationModel designationModel = new DesignationModel(null, "Designation Employee", null, null);

		designationId = idesignationController.save(designationModel).getId();
	}
	
	/**
	 * Creates the employee.
	 */
	public void createEmployee()
	{
		iemployeeController = clientControllerFactory.getController(IEmployeeController.class);

		EmployeeModel employeeModel = new EmployeeModel("Employee1", "employee1@gmail.com", "1234567891", "12345", "12345");

		employeeId = iemployeeController.save(employeeModel).getId();
	}
	
	/**
	 * Send mail.
	 */
	@Test
	public void sendMail()
	{
		activeUserId = employeeSesssion.getUserId();
		
	}
	
	/**
	 * Cleanup.
	 */
	@AfterClass
	public void cleanup()
	{
		// Customer
		iemployeeController.deleteAll();
		idesignationController.deleteAll();
		
		// Admin
		customerHelper.deleteAll(clientContext);
		customerPricePlanHelper.deleteAll(clientContext);
	}
}
