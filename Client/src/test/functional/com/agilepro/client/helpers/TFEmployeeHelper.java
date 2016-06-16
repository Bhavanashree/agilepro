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
import com.agilepro.commons.models.admin.DesignationModel;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;

/**
 * Employee class test cases.
 * 
 * @author bhavana
 *
 */

public class TFEmployeeHelper extends TFBase
{
	private static Logger logger = LogManager.getLogger(TFEmployeeHelper.class);
	/**
	 * EmployeeHelper object with default values.
	 */
	private EmployeeHelper employeeHelper = new EmployeeHelper();

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
	}

	/**
	 * Test designation save with all required fields with proper format .
	 **/
	@Test
	public void testSave()
	{

		EmployeeModel model = new EmployeeModel(0L, "employee1", "emp7@gmail.com", phoneNumber, password, null, null, 2L);
		model.setPassword(password);
		model.setConfirmPassword(password);
		model.setDesignations(designationId);
		Long employeeId = employeeHelper.save(clientCurrentSession, model);
		model.setId(employeeId);
		model.setName("abce");
		Assert.assertTrue(employeeId > 0);
	}

	@Test
	public void testRead()
	{
		// employee
		EmployeeModel model = new EmployeeModel(0L, "employee2", "emp2@gmail.com", phoneNumber, password, password, null, 2L);
		model.setPassword(password);
		model.setConfirmPassword(password);
		model.setDesignations(designationId);
		long id = employeeHelper.save(clientCurrentSession, model);
		logger.debug("Saved new employee with id - {}", id);
		Assert.assertTrue(id > 0);
		employeeHelper.read(clientCurrentSession, id);
	}

	@Test
	public void testUpdate()
	{

		EmployeeModel model = new EmployeeModel(0L, "employee3", "emp3@gmail.com", phoneNumber, password, password, null, 2L);
		model.setPassword(password);
		model.setConfirmPassword(password);
		model.setDesignations(designationId);
		long id = employeeHelper.save(clientCurrentSession, model);
		employeeHelper.read(clientCurrentSession, id);
		model.setName("admin");
		model.setId(id);
		employeeHelper.update(clientCurrentSession, model);
		employeeHelper.read(clientCurrentSession, id);
	}

	@Test
	public void testDelete()
	{
		EmployeeModel model = new EmployeeModel(0L, "employee4", "emp1@gmail.com", phoneNumber, password, password, null, 2L);
		model.setPassword(password);
		model.setConfirmPassword(password);
		model.setDesignations(designationId);
		long id = employeeHelper.save(clientCurrentSession, model);
		logger.debug("Saved employee with id - {}", id);
		employeeHelper.delete(clientCurrentSession, id);
	}

	/**
	 *cleanup. 
	 */
	@AfterClass
	public void cleanup()
	{
		employeeHelper.deleteAll(clientCurrentSession);
		designationHelper.deleteAll(clientCurrentSession);
		customerHelper.deleteAll(clientContext);
		pricePlanHelper.deleteAll(clientContext);
	}
}
