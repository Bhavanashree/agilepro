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
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.CustomerPocModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.utils.CommonUtils;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.RestException;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;

/**
 * The Class TFCustomerHelper for testing all the cases.
 * 
 * @author Pritam
 */
public class TFCustomerHelper extends TFBase implements ITestConstants
{
	/**
	 * The Logger.
	 */
	private static Logger logger = LogManager.getLogger(TFCustomerHelper.class);

	/**
	 * CustomerHelper object with default values.
	 */
	private CustomerHelper customerHelper = new CustomerHelper();

	/**
	 * CustomerPricePlanHelper object with default values.
	 */
	private CustomerPricePlanHelper pricePlanHelper = new CustomerPricePlanHelper();
	
	/**
	 * Price plan id.
	 */
	private Long idOfPricePlan;
		
	/**
	 * The setup.
	 */
	@BeforeClass
	public void setup()
	{
		/**
		 * Customer price plan.
		 **/
		CustomerPricePlanModel customerPricePlanModel = new CustomerPricePlanModel();
		customerPricePlanModel.setName("Plan1");
		customerPricePlanModel.setPaymentCycle(PaymentCycle.Daily);
		CustomerPricePlanExpression ex = new CustomerPricePlanExpression();
		ex.setExpression("3+2");
		ex.setName("test1");
		ex.setLabel("pay");
		List<CustomerPricePlanExpression> listExp = new ArrayList<CustomerPricePlanExpression>();
		listExp.add(ex);
		customerPricePlanModel.setExpressions(listExp);
		idOfPricePlan = pricePlanHelper.save(clientContext, customerPricePlanModel);
		logger.debug("Saved price plan with id - {}", idOfPricePlan);
		Assert.assertTrue(idOfPricePlan > 0);
	}
	
	private void testSave(CustomerModel model, int expectedExceptionCode, String useCase)
	{
		try
		{
			long id = customerHelper.save(clientContext, model);

			if(expectedExceptionCode > 0)
			{
				Assert.fail("No exception is thrown during save - " + useCase);
			}
			else
			{
				Assert.assertTrue(id > 0);
			}
		} catch(RestException exception)
		{
			if(expectedExceptionCode <= 0)
			{
				Assert.fail("An exception is thrown when it is not expected - " + useCase);
			}

			Assert.assertEquals(exception.getResponseCode(), expectedExceptionCode);
		}
	}

	/**
	 * Test customer save with all required fields with proper format .
	 */
	@Test
	public void testSave()
	{
		// Customer Table
		CustomerModel model = new CustomerModel("Customer1", "customer1@gmail.com", PHONE_NUMBER, null, null, null, null, new Date(), PASSWORD, PASSWORD, "path1", null);

		model.setCustomerPricePlanId(idOfPricePlan);

		CustomerPocModel customerPocModel1 = new CustomerPocModel("amit", "9861908697", "amit@gmail.com", "Bangalore new ");
		CustomerPocModel customerPocModel2 = new CustomerPocModel("raj", "9861045030", "raj@ymail.com", "Delhi ");

		ArrayList<CustomerPocModel> list = new ArrayList<CustomerPocModel>();

		list.add(customerPocModel1);
		list.add(customerPocModel2);

		model.setCustomerPocModelList(list);
		model.setVariableMap(CommonUtils.toMap("var1", 2.0, "var2", T_DUE_AMOUNT));

		long id = customerHelper.save(clientContext, model);
		logger.debug(T_DEBUG_MESSAGE, id);
		Assert.assertTrue(id > 0);

		// ensure login can be done with new customer details
		ClientContext context = super.newClientContext(model.getEmail(), model.getPassword(), id);
		Assert.assertNotNull(context);
	}

	/**
	 * Test customer save (name) maximum length.
	 */
	@Test
	public void testSaveMaximumLength()
	{
		// Customer Table
		CustomerModel model = new CustomerModel("Testing is my hobby.The more you test the more perfect your code becomes so do it again and again", "customer2@gmail.com", PHONE_NUMBER, null, null, null, null, null, PASSWORD, PASSWORD, "path2", null);

		model.setCustomerPricePlanId(idOfPricePlan);

		testSave(model, IWebUtilsCommonConstants.RESPONSE_CODE_INVALID_REQUEST, "Maximum length(name)");
	}

	/**
	 * Test customer save (name) minimum length.
	 */
	@Test
	public void testSaveMinimumLength()
	{
		// Customer table
		CustomerModel model = new CustomerModel("", "customer3@gmail.com", PHONE_NUMBER, null, null, null, null, null, PASSWORD, PASSWORD, "path3", null);

		model.setCustomerPricePlanId(idOfPricePlan);

		testSave(model, IWebUtilsCommonConstants.RESPONSE_CODE_INVALID_REQUEST, "Minimum length(name)");
	}

	/**
	 * Test customer email with wrong format.
	 */
	@Test
	public void testEmail()
	{
		// Customer table
		CustomerModel model = new CustomerModel("Customer4", "customer4gmailcom", PHONE_NUMBER, null, null, null, null, null, PASSWORD, PASSWORD, "path4", null);

		model.setCustomerPricePlanId(idOfPricePlan);

		testSave(model, IWebUtilsCommonConstants.RESPONSE_CODE_INVALID_REQUEST, "Wrong email format");
	}

	/**
	 * Test customer phone number maximum length.
	 */
	@Test
	public void testPhoneNumberMaximumLength()
	{
		// Customer table
		CustomerModel model = new CustomerModel("Customer5", "customer5@gmail.com", "1234567891123456789123456", null, null, null, null, null, PASSWORD, PASSWORD, "path5", null);

		model.setCustomerPricePlanId(idOfPricePlan);

		testSave(model, IWebUtilsCommonConstants.RESPONSE_CODE_INVALID_REQUEST, "Maximum length(phone number)");
	}

	/**
	 * Test customer phone number minimum length.
	 */
	@Test
	public void testPhoneNumberMinimumLength()
	{
		// Customer table
		CustomerModel model = new CustomerModel("Customer6", "customer6@gmail.com", "977", null, null, null, null, null, PASSWORD, PASSWORD, "path6", null);

		model.setCustomerPricePlanId(idOfPricePlan);

		testSave(model, IWebUtilsCommonConstants.RESPONSE_CODE_INVALID_REQUEST, "Minimum length(phone number)");
	}

	/**
	 * Test read customer data from the table.
	 */
	@Test
	public void testRead()
	{
		String customerName = "Customer7";
		String customerEmail = "customer7@gmail.com";
		String address = "India";
		String taxNumber = "101";
		String workType = "group";
		
		// Customer table
		CustomerModel model = new CustomerModel(customerName, customerEmail, PHONE_NUMBER, address, taxNumber, null, workType, new Date(), PASSWORD, PASSWORD, "path7", null);

		model.setCustomerPricePlanId(idOfPricePlan);

		long id = customerHelper.save(clientContext, model);
		logger.debug(T_DEBUG_MESSAGE, id);
		Assert.assertTrue(id > 0);

		CustomerModel cm = customerHelper.read(clientContext, id);
		Assert.assertEquals(customerName, cm.getName());
		Assert.assertEquals(customerEmail, cm.getEmail());
		Assert.assertEquals(PHONE_NUMBER, cm.getPhoneNumber());
		Assert.assertEquals(address, cm.getAddress());
		Assert.assertEquals(taxNumber, cm.getSalesTaxNumber());
		Assert.assertEquals(workType, cm.getWorkType());
		Assert.assertTrue(cm.getCustomerPricePlanId() > 0);
		Assert.assertEquals(cm.getCustomerPricePlanId(), idOfPricePlan.longValue());
		Assert.assertNotNull(cm.getRegistrationDate());
	}

	/**
	 * Test update customer data in the table.
	 */
	@Test
	public void testUpdate()
	{
		String customerName = "Customer8";
		String customerEmail = "customer8@gmail.com";
		String newName = "New Name";
		
		// Customer table
		CustomerModel model = new CustomerModel(customerName, customerEmail, PHONE_NUMBER, null, null, null, null, new Date(), PASSWORD, PASSWORD, "path8", null);

		model.setCustomerPricePlanId(idOfPricePlan);

		long id = customerHelper.save(clientContext, model);
		logger.debug(T_DEBUG_MESSAGE, id);
		Assert.assertTrue(id > 0);

		CustomerModel modelFromDb = customerHelper.read(clientContext, id);

		model = new CustomerModel(customerName, customerEmail, PHONE_NUMBER, null, null, null, null, new Date(), PASSWORD, PASSWORD, "path9", null);
		model.setName(newName);
		model.setId(id);
		model.setVersion(modelFromDb.getVersion());
		model.setCustomerPricePlanId(idOfPricePlan);

		customerHelper.update(clientContext, model);

		CustomerModel cm = customerHelper.read(clientContext, id);

		Assert.assertEquals(newName, cm.getName());
		Assert.assertEquals(id, cm.getId().intValue());
		Assert.assertEquals((int) cm.getVersion(), 2);
	}

	/**
	 * Test delete customer data from table.
	 */
	@Test
	public void testDelete()
	{
		// Customer table
		CustomerModel model = new CustomerModel("Customer9", "customer9@gmail.com", PHONE_NUMBER, null, null, null, null, new Date(), PASSWORD, PASSWORD, "path10", null);
		
		model.setCustomerPricePlanId(idOfPricePlan);

		long id = customerHelper.save(clientContext, model);
		logger.debug(T_DEBUG_MESSAGE, id);
		Assert.assertTrue(id > 0);

		// Deleting Customer record
		customerHelper.delete(clientContext, id);

		// read and validate
		model = customerHelper.read(clientContext, id);
		Assert.assertNull(model);
	}

	@AfterClass
	public void cleanUp()
	{
		customerHelper.deleteAll(clientContext);	
		pricePlanHelper.deleteAll(clientContext);
	}
}
