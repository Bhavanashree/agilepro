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
import com.agilepro.commons.controllers.admin.IEmployeeController;
import com.agilepro.commons.models.admin.DesignationModel;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.ClientControllerFactory;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Class TFEmployee.
 * 
 * @author Pritam
 */
public class TFEmployee extends TFBase
{
	
	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(TFEmployee.class);

	/**
	 * DesignationHelper object with default values.
	 */
	private DesignationHelper designationHelper = new DesignationHelper();

	/**
	 * CustomerHelper object with default values.
	 */
	private CustomerHelper customerHelper = new CustomerHelper();
	/**
	 * CustomerPricePlanHelper object with default values.
	 */
	private CustomerPricePlanHelper pricePlanHelper = new CustomerPricePlanHelper();
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
	 * designationId.
	 */
	private Long designationId;

	/**
	 * designation name.
	 */
	private String designationName = "Manager";
	/**
	 * The due amount paid by customer.
	 */
	private final double dueAmount = 10000.0;

	/**
	 * The Session object.
	 */
	private ClientContext clientCurrentSession;

	/**
	 * The I employee controller.
	 **/
	private IEmployeeController iemployeeController;

	/**
	 * saving the pricePlan and customer before saving employee.
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
		Assert.assertTrue(idOfPricePlan > 0);
		Assert.assertTrue(customerId > 0);

		clientCurrentSession = super.newClientContext(emailId, password, customerId);

		// designation
		DesignationModel designationModel = new DesignationModel(0L, designationName, null, null);
		designationId = designationHelper.save(clientCurrentSession, designationModel);
		designationModel.setId(designationId);
		Assert.assertTrue(designationId > 0);

		clientControllerFactory = new ClientControllerFactory(clientCurrentSession);

		iemployeeController = clientControllerFactory.getController(IEmployeeController.class);
	}

	/**
	 * Gets the employee.
	 *
	 * @param employeeId
	 *            the employee id
	 * @return the employee
	 */
	public EmployeeModel getEmployee(Long employeeId)
	{
		BasicReadResponse<EmployeeModel> basicReadResponse = iemployeeController.read(employeeId);

		return basicReadResponse.getModel();
	}

	/**
	 * Test save.
	 */
	@Test
	public void testSave()
	{
		EmployeeModel model = new EmployeeModel("employee1", "emp7@gmail.com", phoneNumber, password, password, designationId);

		BasicSaveResponse basicSaveResponse = iemployeeController.save(model);

		Assert.assertTrue(basicSaveResponse.getId() > 0);
		logger.debug("Saved new employee with id - {}", basicSaveResponse.getId());
	}

	@Test
	public void testRead()
	{
		EmployeeModel model = new EmployeeModel("employee2", "emp2@gmail.com", phoneNumber, password, password, designationId);

		BasicSaveResponse basicSaveResponse = iemployeeController.save(model);
		logger.debug("Saved new employee with id - {}", basicSaveResponse.getId());
		
		EmployeeModel fetchedModel = getEmployee(basicSaveResponse.getId());
		
		Assert.assertTrue(fetchedModel.getId() > 0);
		Assert.assertEquals(fetchedModel.getName(), "employee2");
		Assert.assertEquals(fetchedModel.getMailId(), "emp2@gmail.com");
	}

	@Test
	public void testUpdate()
	{
		EmployeeModel model = new EmployeeModel("employee3", "emp3@gmail.com", phoneNumber, password, password, designationId);
		
		BasicSaveResponse basicSaveResponse = iemployeeController.save(model);
		logger.debug("Saved new employee with id - {}", basicSaveResponse.getId());
		
		EmployeeModel fetchedModel = getEmployee(basicSaveResponse.getId());
		fetchedModel.setName("admin");
		fetchedModel.setPassword(password);
		fetchedModel.setConfirmPassword(password);
		
		iemployeeController.update(fetchedModel);

		fetchedModel = getEmployee(basicSaveResponse.getId());
		
		Assert.assertEquals(fetchedModel.getName(), "admin");
		Assert.assertEquals(fetchedModel.getMailId(), "emp3@gmail.com");
	}
	
	@Test
	public void testDelete()
	{
		EmployeeModel model = new EmployeeModel("employee4", "emp1@gmail.com", phoneNumber, password, password, designationId);
		
		BasicSaveResponse basicSaveResponse = iemployeeController.save(model);
		Assert.assertTrue(basicSaveResponse.getId() > 0);
		logger.debug("Saved new employee with id - {}", basicSaveResponse.getId());
		
		iemployeeController.delete(basicSaveResponse.getId());
		
		EmployeeModel fetchedModel = getEmployee(basicSaveResponse.getId());
		
		Assert.assertNull(fetchedModel);
		logger.debug("Deleted employee with id - {}", basicSaveResponse.getId());
	}
	
	/**
	 *cleanup. 
	 */
	@AfterClass
	public void cleanup()
	{
		iemployeeController.deleteAll();
		designationHelper.deleteAll(clientCurrentSession);
		
		customerHelper.deleteAll(clientContext);
		pricePlanHelper.deleteAll(clientContext);
	}
}
