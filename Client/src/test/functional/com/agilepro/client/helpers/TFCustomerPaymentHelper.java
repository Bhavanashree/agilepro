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
import com.agilepro.commons.models.customer.CustomerPaymentModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;

/**
 * Test cases for customer Payment.
 * 
 * @author bhavana
 */
public class TFCustomerPaymentHelper extends TFBase
{
	/**
	 * The customer email id.
	 */
	private static final String EMAIL_ID = "customer@gmail.com";

	/**
	 * The password.
	 */
	private static final String PASSWORD = "abcde";
	/**
	 * The due amount paid by customer.
	 */
	private static final double CUST_DUE_AMOUNT = 10000.0;

	/**
	 * the amount paid.
	 */
	private static final double PAYMENT_AMOUNT = 5000.0;

	/**
	 * Updating payment.
	 */
	private static final double UPDATED_PAYMENT_AMOUNT = 3000.0;

	private static Logger logger = LogManager.getLogger(TFCustomerPaymentHelper.class);
	/**
	 * PaymentHelper object with default values.
	 */
	private PaymentHelper paymentHelper = new PaymentHelper();

	/**
	 * CustomerHelper object with default values.
	 */
	private CustomerHelper customerHelper = new CustomerHelper();
	/**
	 * CustomerInvoiceDetailsHelper object with default values.
	 */
	private CustomerInvoiceDetailsHelper invoiceHelper = new CustomerInvoiceDetailsHelper();

	/**
	 * CustomerPricePlanHelper object with default values.
	 */
	private CustomerPricePlanHelper pricePlanHelper = new CustomerPricePlanHelper();

	/**
	 * Phone Number.
	 */
	private String phoneNumber = "1234567891";

	/**
	 * customerId.
	 */
	private long customerId;

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
		CustomerModel model = new CustomerModel("Test1", EMAIL_ID, phoneNumber, null, null, null, null, new Date(), PASSWORD, PASSWORD, "path1", null);
		model.setCustomerPricePlanId(idOfPricePlan);
		model.setDueAmount(CUST_DUE_AMOUNT);
		customerId = customerHelper.save(clientContext, model);
		Assert.assertTrue(idOfPricePlan > 0);
		Assert.assertTrue(customerId > 0);
	}

	/**
	 * Tests Customer Payment save.
	 */
	@Test
	public void testSave()
	{
		// payment table
		CustomerPaymentModel customerPaymentModel = new CustomerPaymentModel(0L, customerId, new Date(), PAYMENT_AMOUNT);
		customerPaymentModel.setCustomerId(customerId);

		Long customerPaymentId = paymentHelper.save(clientContext, customerPaymentModel);
		customerPaymentModel.setId(customerPaymentId);

		Assert.assertTrue(customerPaymentId > 0);
	}

	/**
	 * Test read Payment data in the table.
	 */
	@Test
	public void testRead()
	{
		// payment table
		CustomerPaymentModel customerPaymentModel = new CustomerPaymentModel(0L, customerId, new Date(), PAYMENT_AMOUNT);
		customerPaymentModel.setId(0L);
		Long customerPaymentId = paymentHelper.save(clientContext, customerPaymentModel);
		CustomerPaymentModel customerPaymentModelFromDb = paymentHelper.read(clientContext, customerPaymentId);
		Assert.assertTrue(customerPaymentId > 0);
		Assert.assertTrue(customerPaymentModelFromDb.getCustomerId() > 0);
	}

	/**
	 * Test update Payment data in the table.
	 */

	@Test
	public void testUpdate()
	{
		CustomerModel customeFromDb = customerHelper.read(clientContext, customerId);

		// Add payment
		CustomerPaymentModel customerPaymentModel = new CustomerPaymentModel(0L, customerId, new Date(), PAYMENT_AMOUNT);

		Long customerPaymentId = paymentHelper.save(clientContext, customerPaymentModel);

		// verify the due amount is updated
		customeFromDb = customerHelper.read(clientContext, customerId);
		Assert.assertEquals(customeFromDb.getDueAmount(), CUST_DUE_AMOUNT - PAYMENT_AMOUNT);

		// update payment
		customerPaymentModel.setAmount(UPDATED_PAYMENT_AMOUNT);
		customerPaymentModel.setId(customerPaymentId);

		paymentHelper.update(clientContext, customerPaymentModel);

		// verify the payment update change in due amount
		customeFromDb = customerHelper.read(clientContext, customerId);
		Assert.assertEquals(customeFromDb.getDueAmount(), CUST_DUE_AMOUNT - UPDATED_PAYMENT_AMOUNT);
	}

	/**
	 * Test delete Payment data from table.
	 */
	@Test
	public void testDelete()
	{
		CustomerPaymentModel customerPaymentModel = new CustomerPaymentModel(0L, customerId, new Date(), CUST_DUE_AMOUNT);
		Long customerPaymentId = paymentHelper.save(clientContext, customerPaymentModel);
		Assert.assertTrue(customerPaymentId > 0);
		paymentHelper.delete(clientContext, customerPaymentId);
		// read and validate
		customerPaymentModel = paymentHelper.read(clientContext, customerPaymentId);
		Assert.assertNull(customerPaymentModel);
	}

	/**
	 * cleanup.
	 */
	@AfterClass
	public void cleanup()
	{
		invoiceHelper.deleteAll(clientContext);
		paymentHelper.deleteAll(clientContext);
		customerHelper.deleteAll(clientContext);
		pricePlanHelper.deleteAll(clientContext);
	}
}
