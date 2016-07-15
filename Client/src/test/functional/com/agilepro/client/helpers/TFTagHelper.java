package com.agilepro.client.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.agilepro.commons.PaymentCycle;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.TagModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;

/**
 * The Class TFTagHelper.
 * 
 * @author Pritam
 */
public class TFTagHelper extends TFBase
{
	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(TFTagHelper.class);

	/**
	 * The customer price plan helper.
	 **/
	private static CustomerPricePlanHelper customerPricePlanHelper = new CustomerPricePlanHelper();

	/**
	 * The customer helper.
	 **/
	private static CustomerHelper customerHelper = new CustomerHelper();

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
	 * designation name.
	 */
	private String designationName = "Manager";
	
	/**
	 * The due amount paid by customer.
	 */
	private final double dueAmount = 10000.0;

	/** 
	 * The tag helper. 
	 **/
	private TagHelper tagHelper = new TagHelper();

	/**
	 * The Session object.
	 */
	private ClientContext customerSession;

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
		CustomerModel model = new CustomerModel("Test1", emailId, phoneNumber, null, null, null, null, new Date(), password, password, "path1", null);
		model.setCustomerPricePlanId(idOfPricePlan);
		model.setDueAmount(dueAmount);
		customerId = customerHelper.save(clientContext, model);
		Assert.assertTrue(idOfPricePlan > 0);
		Assert.assertTrue(customerId > 0);

		customerSession = super.newClientContext(emailId, password, customerId);
	}

	@Test
	public void testSave()
	{
		TagModel tagsModel = new TagModel();
		tagsModel.setName("FirstTag");
		tagsModel.setDescription("This new feature for tagging user");

		long id = tagHelper.save(clientContext, tagsModel);

		Assert.assertTrue(id > 0);
	}

	@Test
	public void testRead()
	{
		TagModel tagsModel = new TagModel();
		tagsModel.setName("SecondTag");
		tagsModel.setDescription("This new feature for tagging user");

		long id = tagHelper.save(clientContext, tagsModel);
		Assert.assertTrue(id > 0);

		TagModel fetchdModel = tagHelper.read(clientContext, id);
		Assert.assertEquals(fetchdModel.getName(), "SecondTag");
		Assert.assertEquals(fetchdModel.getDescription(), "This new feature for tagging user");
	}

	@Test
	public void testUpdate()
	{
		TagModel tagsModel = new TagModel();
		tagsModel.setName("ThirdTag");
		tagsModel.setDescription("This new feature for tagging user");

		long id = tagHelper.save(clientContext, tagsModel);
		Assert.assertTrue(id > 0);

		TagModel fetchdModel = tagHelper.read(clientContext, id);

		// fetchdModel.setId(id);
		fetchdModel.setName("FourthTag");
		fetchdModel.setDescription("This  tag is for fourth user");

		tagHelper.update(clientContext, fetchdModel);

		TagModel updatedModel = tagHelper.read(clientContext, id);
		Assert.assertEquals(updatedModel.getName(), "FourthTag");
		Assert.assertEquals(updatedModel.getDescription(), "This  tag is for fourth user");
	}

	@Test
	public void testDelete()
	{
		TagModel tagsModel = new TagModel();
		tagsModel.setName("FifthTag");
		tagsModel.setDescription("This new feature for tagging user");

		long id = tagHelper.save(clientContext, tagsModel);
		Assert.assertTrue(id > 0);

		tagHelper.delete(clientContext, id);

		TagModel deletedModel = tagHelper.read(clientContext, id);
		Assert.assertNull(deletedModel);
	}

	/**
	 * cleanup.
	 */
	@AfterClass
	public void cleanup()
	{
		tagHelper.deleteAll(clientContext);

		// customerHelper.deleteAll(clientContext);
		// customerPricePlanHelper.deleteAll(clientContext);
	}
}
