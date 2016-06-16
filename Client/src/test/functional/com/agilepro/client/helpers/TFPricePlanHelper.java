package com.agilepro.client.helpers;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.agilepro.commons.PaymentCycle;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanVariable;
import com.yukthi.webutils.client.RestException;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;

/**
 * The Class TFPricePlanHelper for testing all the cases..
 */
public class TFPricePlanHelper extends TFBase
{
	/**
	 * The positive save.
	 */
	private static final String POS_SAVE = "positive save";

	/**
	 * The price plan helper.
	 */
	private CustomerPricePlanHelper pricePlanHelper = new CustomerPricePlanHelper();

	/**
	 * The customer helper.
	 */
	private CustomerHelper customerHelper = new CustomerHelper();

	/**
	 * The customer invoice helper.
	 */
	private CustomerInvoiceDetailsHelper customerinvoiceHelper = new CustomerInvoiceDetailsHelper();

	/**
	 * The payment helper.
	 */
	private PaymentHelper paymentHelper = new PaymentHelper();

	/**
	 * Test save.
	 *
	 * @param model
	 *            the customer price plan model
	 * @param expectedExceptionCode
	 *            the expected exception code
	 * @param useCase
	 *            the use case
	 * @return the long id of customer price plan
	 */
	private long testSave(CustomerPricePlanModel model, int expectedExceptionCode, String useCase)
	{
		try
		{
			long id = pricePlanHelper.save(clientContext, model);

			if(expectedExceptionCode > 0)
			{
				Assert.fail("No exception is thrown during save - " + useCase);
			}
			else
			{
				Assert.assertTrue(id > 0);
			}
			return id;
		} catch(RestException exception)
		{
			if(expectedExceptionCode <= 0)
			{
				Assert.fail("An exception is thrown when it is not expected - " + useCase);
			}

			Assert.assertEquals(exception.getResponseCode(), expectedExceptionCode);
			return 0;
		}
	}

	/**
	 * Test customer price plan save method with all required fields.
	 */
	@Test
	public void testSave()
	{
		final double varValue = 4.0;
		CustomerPricePlanModel model = new CustomerPricePlanModel();
		model.setName("Test");
		model.setPaymentCycle(PaymentCycle.Daily);

		model.setExpressions(Arrays.asList(new CustomerPricePlanExpression("testExpr1", "exp1", "7+2")));

		model.setNumericVariables(Arrays.asList(new CustomerPricePlanVariable("testvar1", "var1", varValue), new CustomerPricePlanVariable("testvar2", "var2", varValue)));

		testSave(model, 0, POS_SAVE);
	}

	/**
	 * Test customer price plan save method with no expression.
	 */
	@Test
	public void testSave_negative()
	{
		CustomerPricePlanModel model1 = new CustomerPricePlanModel();
		model1.setName("Sample");
		model1.setPaymentCycle(PaymentCycle.Daily);

		testSave(model1, IWebUtilsCommonConstants.RESPONSE_CODE_INVALID_REQUEST, "No expressions");
	}

	/**
	 * Test customer price plan save method with invalid expression.
	 */
	@Test
	public void testExpression_negative()
	{

		CustomerPricePlanModel model = new CustomerPricePlanModel();
		model.setName("test2");
		model.setPaymentCycle(PaymentCycle.Daily);
		model.setExpressions(Arrays.asList(new CustomerPricePlanExpression("testExpr2", "exp2", "3+-(2)")));
		testSave(model, IWebUtilsCommonConstants.RESPONSE_CODE_INVALID_REQUEST, "Invalid expressions");
	}

	/**
	 * Test customer price plan read method with all required fields.
	 */
	@Test
	public void testRead()
	{
		final double varValue = 4.0;
		String planName = "Test3";
		String expName = "testExpr3";
		String varName = "testvar3";
		// create entity
		CustomerPricePlanModel model = new CustomerPricePlanModel();
		model.setName(planName);
		model.setPaymentCycle(PaymentCycle.Daily);
		model.setExpressions(Arrays.asList(new CustomerPricePlanExpression(expName, "exp3", "3+1")));
		model.setNumericVariables(Arrays.asList(new CustomerPricePlanVariable(varName, "var3", varValue), new CustomerPricePlanVariable("testvar4", "var4", varValue)));
		long id = testSave(model, 0, POS_SAVE);
		// read entity
		CustomerPricePlanModel plan = pricePlanHelper.read(clientContext, id);
		// validate entity
		Assert.assertEquals(plan.getName(), planName);
		Assert.assertEquals(plan.getNumericVariables().get(0).getName(), varName);
		Assert.assertEquals(plan.getExpressions().get(0).getName(), expName);
	}

	/**
	 * Test customer price plan update method with all required fields.
	 */
	@Test
	public void testUpdate()
	{
		final double varValue = 4.0;
		// create entity
		CustomerPricePlanModel model = new CustomerPricePlanModel();
		model.setName("Test4");
		model.setPaymentCycle(PaymentCycle.Daily);
		model.setExpressions(Arrays.asList(new CustomerPricePlanExpression("testExpr4", "exp4", "3+5")));
		model.setNumericVariables(Arrays.asList(new CustomerPricePlanVariable("testvar5", "var5", varValue), new CustomerPricePlanVariable("testvar6", "var6", varValue)));
		long id = testSave(model, 0, POS_SAVE);

		// read entity
		String newName = "abcd";
		CustomerPricePlanModel plan = pricePlanHelper.read(clientContext, id);
		plan.setName(newName);

		// invoke update
		pricePlanHelper.update(clientContext, plan);

		// validate entity
		CustomerPricePlanModel c1 = pricePlanHelper.read(clientContext, id);
		Assert.assertEquals(c1.getName(), newName);
	}

	/**
	 * Test customer price plan delete method with all required fields.
	 */
	@Test
	public void testDelete()
	{
		final double varValue = 4.0;
		// create entity
		CustomerPricePlanModel model = new CustomerPricePlanModel();
		model.setName("Test5");
		model.setPaymentCycle(PaymentCycle.Daily);
		model.setExpressions(Arrays.asList(new CustomerPricePlanExpression("testExpr5", "exp5", "3+9")));
		model.setNumericVariables(Arrays.asList(new CustomerPricePlanVariable("testvar7", "var7", varValue), new CustomerPricePlanVariable("testvar8", "var8", varValue)));
		long id = testSave(model, 0, POS_SAVE);
		// delete entity
		pricePlanHelper.delete(clientContext, id);

		CustomerPricePlanModel plan = pricePlanHelper.read(clientContext, id);
		Assert.assertEquals(plan, null);
	}

	/**
	 * Test customer price plan save method with long name value.
	 */
	@Test
	public void testSaveMaximumLength()
	{
		CustomerPricePlanModel model = new CustomerPricePlanModel();
		model.setName("The more you test the more perfect your code becomes so it again and again");
		model.setExpressions(Arrays.asList(new CustomerPricePlanExpression("testExpr", "test7", "3+2")));
		testSave(model, IWebUtilsCommonConstants.RESPONSE_CODE_INVALID_REQUEST, "Long name value is passed");
	}

	/**
	 * Test calculate due amount of customer.
	 */
	@Test
	public void testCalculateAmount()
	{
		final double varValue = 4.0;
		final int add = -2;
		final double dueAmount = 6.0;
		final String password = "abcde";
		String varName1 = "testvar9";
		String varName2 = "testvar10";
		CustomerPricePlanModel model = new CustomerPricePlanModel();
		model.setName("sample");
		model.setPaymentCycle(PaymentCycle.Monthly);
		model.setExpressions(Arrays.asList(new CustomerPricePlanExpression("testExpr6", "exp6", "1+2")));
		model.setNumericVariables(Arrays.asList(new CustomerPricePlanVariable(varName1, "var9", varValue), new CustomerPricePlanVariable(varName2, "var10", varValue)));
		long id = pricePlanHelper.save(clientContext, model);

		Date date = new Date();
		date = DateUtils.addMonths(date, add);

		CustomerModel cust = new CustomerModel("test", "abc@gmail.com", "23456768745", null, null, null, null, date, password, password, null, null);
		cust.setCustomerPricePlanId(id);
		cust.setCustomerPricePlanId(id);
		cust.setSubDomainPath("null");
		Map<String, Double> variableMap = new HashMap<String, Double>();
		variableMap.put(varName1, varValue);
		variableMap.put(varName2, varValue);
		cust.setVariableMap(variableMap);
		long custId = customerHelper.save(clientContext, cust);

		pricePlanHelper.calculateAmount(clientContext);
		CustomerModel custModel = customerHelper.read(clientContext, custId);
		Assert.assertEquals(custModel.getDueAmount(), dueAmount);
	}

	/**
	 * Cleanup.
	 */
	//@AfterClass
	public void cleanup()
	{

		customerinvoiceHelper.deleteAll(clientContext);
		paymentHelper.deleteAll(clientContext);
		customerHelper.deleteAll(clientContext);
		pricePlanHelper.deleteAll(clientContext);
	}
}
