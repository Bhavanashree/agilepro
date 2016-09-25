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
import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.admin.DesignationModel;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.admin.EmployeeSearchQuery;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;

/**
 * The Class TFEmployeeSearchQuery for testing the search cases.
 * 
 * @author bhavana.
 *
 */

public class TFEmployeeSearchQuery extends TFBase
{

	private static Logger logger = LogManager.getLogger(TFDesignationHelper.class);
	/**
	 * The customer email id.
	 */
	private static final String EMAIL_ID = "customer@gmail.com";

	/**
	 * The password.
	 */
	private static final String PASSWORD = "abcde";
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
	 * employeeHelper object with default values.
	 */
	private EmployeeHelper employeeHelper = new EmployeeHelper();
	/**
	 * customerId.
	 */
	private Long customerId;
	/**
	 * Phone Number.
	 */
	private String phoneNumber = "1234567891";

	/**
	 * The due amount paid by customer.
	 */
	private final double dueAmount = 10000.0;

	/**
	 * EmployeeName.
	 */
	private String empName = "yukthi";
	/**
	 * The Session object.
	 */
	private ClientContext clientCurrentSession;

	/**
	 * SearchHelper object with default values.
	 */
	//private SearchHelper searchHelper = new SearchHelper();

	/**
	 * saving the pricePlan and customer before saving designation.
	 **/
	@BeforeClass
	public void setup()
	{
		// customer price plan
		CustomerPricePlanModel customerPricePlanModel = new CustomerPricePlanModel();
		customerPricePlanModel.setName("Test");
		customerPricePlanModel.setPaymentCycle(PaymentCycle.Daily);
		CustomerPricePlanExpression ex = new CustomerPricePlanExpression();
		ex.setExpression("3+2");
		ex.setName("test");
		ex.setLabel("pay21");
		List<CustomerPricePlanExpression> listExp = new ArrayList<CustomerPricePlanExpression>();
		listExp.add(ex);
		customerPricePlanModel.setExpressions(listExp);
		long idOfPricePlan = pricePlanHelper.save(clientContext, customerPricePlanModel);
		logger.debug("Saved price plan with id - {}", idOfPricePlan);
		Assert.assertTrue(idOfPricePlan > 0);

		CustomerModel model = new CustomerModel("Test1", EMAIL_ID, phoneNumber, null, null, null, null, new Date(), PASSWORD, PASSWORD, "path1", null);
		model.setCustomerPricePlanId(idOfPricePlan);
		model.setDueAmount(dueAmount);
		customerId = customerHelper.save(clientContext, model);
		Assert.assertTrue(idOfPricePlan > 0);
		Assert.assertTrue(customerId > 0);
		clientCurrentSession = super.newClientContext(EMAIL_ID, PASSWORD, customerId);

		DesignationModel designationModel = new DesignationModel(0L, "Mangaer", null, null);
		// save entity
		Long designationId = designationHelper.save(clientCurrentSession, designationModel);
		List<UserRole> listExp1 = new ArrayList<UserRole>();
		listExp1.add(UserRole.DESIGNATION_EDIT);
		listExp1.add(UserRole.DESIGNATION_DELETE);
		listExp1.add(UserRole.TEST);
		listExp1.add(UserRole.EMPLOYEE_DELETE);
		listExp1.add(UserRole.TEST);
		listExp1.add(UserRole.EMPLOYEE_VIEW);
		designationModel.setRoles(listExp1);
		Assert.assertTrue(designationId > 0);

		EmployeeModel model1 = new EmployeeModel(empName, "emp7@gmail.com", phoneNumber, null, null, 2L);
		model1.setPassword(PASSWORD);
		model1.setConfirmPassword(PASSWORD);
		model1.setDesignationId(designationId);
		Long employeeId = employeeHelper.save(clientCurrentSession, model1);
		model.setId(employeeId);
		model.setName("abce");
		Assert.assertTrue(employeeId > 0);
	}

	/**
	 * Test Employee search .
	 */
	@Test
	public void testSearchResults()
	{
		EmployeeSearchQuery query = new EmployeeSearchQuery("%u%", null, null, null, null);
		//List<EmployeeSearchResult> results = searchHelper.executeSearchQuery(clientCurrentSession, "employeeSearch", query, -1, EmployeeSearchResult.class);

		//Assert.assertEquals(results.size(), 1);
		// to ensure bean conversion is good, check first bean
		//Assert.assertEquals(results.get(0).getName(), empName);
	}

	/**
	 * cleanup.
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
