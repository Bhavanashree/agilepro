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
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;

/**
 * Designation helper.
 * 
 * @author bhavana
 *
 */
public class TFDesignationHelper extends TFBase
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
	 * The Session object.
	 */
	private ClientContext clientCurrentSession;

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
	}

	/**
	 * Test designation save with all required fields with proper format .
	 * 
	 **/
	@Test
	public void testSave()
	{

		DesignationModel designationModel = new DesignationModel(0L, "Manager1", null, null);
		// save entity
		Long designationId = designationHelper.save(clientCurrentSession, designationModel);
		List<UserRole> listExp1 = new ArrayList<UserRole>();
		listExp1.add(UserRole.DESIGNATION_EDIT);
		listExp1.add(UserRole.DESIGNATION_DELETE);
		listExp1.add(UserRole.DESIGNATION_DELETE_ALL);
		designationModel.setRoles(listExp1);
		Assert.assertTrue(designationId > 0);
	}

	/**
	 * Test designation read method with all required fields.
	 */
	@Test
	public void testRead()
	{
		// save entity
		DesignationModel designationModel = new DesignationModel(0L, "Manager2", null, null);
		List<UserRole> listExp1 = new ArrayList<UserRole>();
		listExp1.add(UserRole.DESIGNATION_VIEW);
		designationModel.setRoles(listExp1);
		long id = designationHelper.save(clientCurrentSession, designationModel);

		logger.debug("Saved new Designation with id - {}", id);
		Assert.assertTrue(id > 0);
		// read entity
		designationHelper.read(clientCurrentSession, id);
	}

	/**
	 * Test designation update method with all required fields.
	 */
	@Test
	public void testUpdate()
	{
		// update entity
		DesignationModel designationModel = new DesignationModel(0L, "Manager3", null, null);
		List<UserRole> listExp1 = new ArrayList<UserRole>();
		listExp1.add(UserRole.DESIGNATION_EDIT);
		listExp1.add(UserRole.DESIGNATION_VIEW);
		designationModel.setRoles(listExp1);
		long id = designationHelper.save(clientCurrentSession, designationModel);
		designationHelper.read(clientCurrentSession, id);
		designationModel.setName("admin");
		designationModel.setId(id);
		designationHelper.update(clientCurrentSession, designationModel);
		designationHelper.read(clientCurrentSession, id);
	}

	/**
	 * Test designation delete method with all required fields.
	 */
	@Test
	public void testDelete()
	{
		// delete entity
		DesignationModel model = new DesignationModel(0L, "Manager4", null, null);

		long id = designationHelper.save(clientCurrentSession, model);
		logger.debug("Saved designation with id - {}", id);

		designationHelper.delete(clientCurrentSession, id);
	}

	/**
	 * cleanup.
	 */
	@AfterClass
	public void cleanup()
	{
		designationHelper.deleteAll(clientCurrentSession);
		customerHelper.deleteAll(clientContext);
		pricePlanHelper.deleteAll(clientContext);
	}
}
