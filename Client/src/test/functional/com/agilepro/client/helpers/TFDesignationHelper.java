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
import com.agilepro.commons.controllers.admin.IDesignationController;
import com.agilepro.commons.models.admin.DesignationModel;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.ClientControllerFactory;

/**
 * Designation helper.
 * 
 * @author bhavana
 *
 */
public class TFDesignationHelper extends TFBase implements ITestConstants
{
	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(TFDesignationHelper.class);

	/**
	 * CustomerHelper object with default values.
	 */
	private CustomerHelper customerHelper = new CustomerHelper();

	/**
	 * CustomerPricePlanHelper object with default values.
	 */
	private CustomerPricePlanHelper pricePlanHelper = new CustomerPricePlanHelper();

	/**
	 * The idesignation controller.
	 **/
	private IDesignationController idesignationController;

	/**
	 * The Session object.
	 */
	private ClientContext customerSession;

	/**
	 * saving the pricePlan and customer before saving designation.
	 **/

	@BeforeClass
	public void setup()
	{
		// customer price plan
		CustomerPricePlanExpression ex = new CustomerPricePlanExpression();
		ex.setExpression("3+2");
		ex.setName("test");
		ex.setLabel("pay21");
		List<CustomerPricePlanExpression> listExp = new ArrayList<CustomerPricePlanExpression>();
		listExp.add(ex);

		CustomerPricePlanModel customerPricePlanModel = new CustomerPricePlanModel();
		customerPricePlanModel.setName("Plan1");
		customerPricePlanModel.setPaymentCycle(PaymentCycle.Daily);
		customerPricePlanModel.setExpressions(listExp);

		long idOfPricePlan = pricePlanHelper.save(clientContext, customerPricePlanModel);
		logger.debug("Saved price plan with id - {}", idOfPricePlan);
		Assert.assertTrue(idOfPricePlan > 0);

		CustomerModel model = new CustomerModel("Test1", T_CUS_EMAIL_ID, T_PHONE_NUMBER, null, null, null, null, new Date(), T_PASSWORD, T_PASSWORD, "customer1", null);
		model.setCustomerPricePlanId(idOfPricePlan);
		model.setDueAmount(T_DUE_AMOUNT);

		Long customerId = customerHelper.save(clientContext, model);
		Assert.assertTrue(idOfPricePlan > 0);
		Assert.assertTrue(customerId > 0);

		customerSession = super.newClientContext(T_CUS_EMAIL_ID, T_PASSWORD, customerId);
		clientControllerFactory = new ClientControllerFactory(customerSession);

		idesignationController = clientControllerFactory.getController(IDesignationController.class);
	}

	/**
	 * Test designation save with all required fields with proper format .
	 * 
	 **/
	@Test
	public void testSave()
	{
		DesignationModel designationModel = new DesignationModel(0L, "Manager1", null, null);
		List<UserRole> listRoles = new ArrayList<UserRole>();
		listRoles.add(UserRole.DESIGNATION_EDIT);
		listRoles.add(UserRole.DESIGNATION_DELETE);
		listRoles.add(UserRole.TEST_DELETE_ALL);
		designationModel.setRoles(listRoles);

		// save entity
		Long designationId = idesignationController.save(designationModel).getId();

		Assert.assertTrue(designationId > 0);
	}

	/**
	 * Test designation read method with all required fields.
	 */
	@Test
	public void testRead()
	{
		// save entity
		String designationName = "Manager2";
		DesignationModel designationModel = new DesignationModel(0L, designationName, null, null);
		List<UserRole> listRoles = new ArrayList<UserRole>();
		listRoles.add(UserRole.DESIGNATION_VIEW);
		designationModel.setRoles(listRoles);

		Long designationId = idesignationController.save(designationModel).getId();
		logger.debug("Saved new Designation with id - {}", designationId);
		Assert.assertTrue(designationId > 0);
		
		// read entity
		DesignationModel savedDesignationModel =  idesignationController.read(designationId).getModel();
		
		Assert.assertEquals(savedDesignationModel.getName(), designationName);
	}

	/**
	 * Test designation update method with all required fields.
	 */
	@Test
	public void testUpdate()
	{
		// update entity
		String designationNameForUpdate = "Admin";
		
		DesignationModel designationModel = new DesignationModel(0L, "Manager3", null, null);
		List<UserRole> listExp1 = new ArrayList<UserRole>();
		listExp1.add(UserRole.DESIGNATION_EDIT);
		listExp1.add(UserRole.DESIGNATION_VIEW);
		designationModel.setRoles(listExp1);
		Long designationId = idesignationController.save(designationModel).getId();
		
		DesignationModel savedDesignationModel =  idesignationController.read(designationId).getModel();
		savedDesignationModel.setName(designationNameForUpdate);
		
		idesignationController.update(savedDesignationModel);
		
		DesignationModel designationModelAfterUpdate =  idesignationController.read(designationId).getModel();
		
		Assert.assertEquals(designationModelAfterUpdate.getName(), designationNameForUpdate);
	}

	/**
	 * Test designation delete method with all required fields.
	 */
	@Test
	public void testDelete()
	{
		// delete entity
		DesignationModel designationModel = new DesignationModel(0L, "Manager4", null, null);

		Long designationId = idesignationController.save(designationModel).getId();
		logger.debug("Saved designation with id - {}", designationId);

		idesignationController.delete(designationId);
		
		DesignationModel deletedDesignationModel = idesignationController.read(designationId).getModel();
		
		Assert.assertNull(deletedDesignationModel);
	}

	/**
	 * cleanup.
	 */
	@AfterClass
	public void cleanup()
	{
		idesignationController.deleteAll();
		
		customerHelper.deleteAll(clientContext);
		pricePlanHelper.deleteAll(clientContext);
	}
}
