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

import com.agilepro.client.helpers.CustomerHelper;
import com.agilepro.client.helpers.CustomerPricePlanHelper;
import com.agilepro.commons.PaymentCycle;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.CustomerSearchQuery;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;

/**
 * The Class TFCustomerSearchQuery. for testing the search cases.
 * 
 * @author Pritam
 */
public class TFCustomerSearchQuery extends TFBase implements ITestConstants
{
	/**
	 * The Logger.
	 */
	private static Logger logger = LogManager.getLogger(TFCustomerSearchQuery.class);
	
	/**
	 * SearchHelper object with default values.
	 */
//	private SearchHelper searchHelper = new SearchHelper();
	
	/**
	 * CustomerPricePlan object with default values.
	 */
	private CustomerPricePlanHelper pricePlanHelper = new CustomerPricePlanHelper();

	/**
	 * CustomerHelper object with default values.
	 */
	private CustomerHelper customerHelper = new CustomerHelper();
	
	/**
	 * Customer Id.
	 */
	private Long customerId;
	
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
		customerPricePlanModel.setName("Plan2");
		customerPricePlanModel.setPaymentCycle(PaymentCycle.Daily);
		CustomerPricePlanExpression ex = new CustomerPricePlanExpression();
		ex.setExpression("3+2");
		ex.setName("test1");
		ex.setLabel("pay");
		List<CustomerPricePlanExpression> listExp = new ArrayList<CustomerPricePlanExpression>();
		listExp.add(ex);
		customerPricePlanModel.setExpressions(listExp);
		long idOfPricePlan = pricePlanHelper.save(clientContext, customerPricePlanModel);
		logger.debug("Saved price plan with id - {}", idOfPricePlan);
		Assert.assertTrue(idOfPricePlan > 0);

		/**
		 *Customer. 
		 **/
		CustomerModel model = new CustomerModel("Customer1", T_CUS_EMAIL_ID, T_PHONE_NUMBER, null, null, null, null, new Date(), T_PASSWORD, T_PASSWORD, "clientGroupPath", null);
		model.setSubDomainPath("bbb");
		model.setCustomerPricePlanId(idOfPricePlan);
		model.setDueAmount(T_DUE_AMOUNT);
		customerId = customerHelper.save(clientContext, model);
		Assert.assertTrue(idOfPricePlan > 0);
		Assert.assertTrue(customerId > 0);
	}
	
	/**
	 * Test customer search .
	 */
	@Test
	public void testSearchResults()
	{
		CustomerSearchQuery query = new CustomerSearchQuery("%e%", null, null, null, null);
		//List<CustomerSearchResult> results = searchHelper.executeSearchQuery(clientContext, "customerSearch", query, -1, CustomerSearchResult.class);
		//AssertJUnit.assertEquals(results.size(), 1);
	//	AssertJUnit.assertEquals(results.get(0).getPhoneNumber(), PHONE_NUMBER);
	}
	
	/**
	 * Clean up.
	 */
	@AfterClass
	public void cleanUp()
	{
		customerHelper.deleteAll(clientContext);
		pricePlanHelper.deleteAll(clientContext);
	}
}
