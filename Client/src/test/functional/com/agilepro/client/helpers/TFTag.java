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
import com.agilepro.commons.controllers.admin.ITagController;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.TagModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.ClientControllerFactory;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Class TFTagHelper.
 * 
 * @author Pritam
 */
public class TFTag extends TFBase
{
	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(TFTag.class);

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
	 * The itag controller. 
	 **/
	private ITagController itagController;
	
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
		
		clientControllerFactory = new ClientControllerFactory(customerSession);
		
		itagController = clientControllerFactory.getController(ITagController.class);
	}

	private TagModel getTag(long tagId)
	{
		BasicReadResponse<TagModel> response = itagController.read(tagId);
		
		return response.getModel();
	}
	
	@Test
	public void testSave()
	{
		TagModel tagsModel = new TagModel();
		tagsModel.setName("FirstTag");
		tagsModel.setDescription("This new feature for tagging user");

		BasicSaveResponse responseTag = itagController.save(tagsModel);

		Assert.assertEquals(responseTag.getCode(), IWebUtilsCommonConstants.RESPONSE_CODE_SUCCESS);
		
		tagsModel = getTag(responseTag.getId());
		
		Assert.assertTrue(tagsModel.getId() > 0);
	}

	@Test
	public void testRead()
	{
		TagModel tagsModel = new TagModel();
		tagsModel.setName("SecondTag");
		tagsModel.setDescription("This new feature for tagging user");

		BasicSaveResponse responseTag = itagController.save(tagsModel);

		TagModel fetchdModel = getTag(responseTag.getId());
		Assert.assertEquals(fetchdModel.getName(), "SecondTag");
		Assert.assertEquals(fetchdModel.getDescription(), "This new feature for tagging user");
	}

	@Test
	public void testUpdate()
	{
		TagModel tagsModel = new TagModel();
		tagsModel.setName("ThirdTag");
		tagsModel.setDescription("This new feature for tagging user");

		BasicSaveResponse responseTag = itagController.save(tagsModel);

		TagModel fetchdModel = getTag(responseTag.getId());

		// fetchdModel.setId(id);
		fetchdModel.setName("FourthTag");
		fetchdModel.setDescription("This  tag is for fourth user");

		itagController.update(fetchdModel);

		TagModel updatedModel = getTag(fetchdModel.getId());
		
		Assert.assertEquals(updatedModel.getName(), "FourthTag");
		Assert.assertEquals(updatedModel.getDescription(), "This  tag is for fourth user");
	}

	@Test
	public void testDelete()
	{
		TagModel tagsModel = new TagModel();
		tagsModel.setName("FifthTag");
		tagsModel.setDescription("This new feature for tagging user");

		BasicSaveResponse responseTag = itagController.save(tagsModel);

		itagController.delete(responseTag.getId());

		TagModel deletedModel = getTag(responseTag.getId());
		Assert.assertNull(deletedModel);
	}

	/**
	 * cleanup.
	 */
	@AfterClass
	public void cleanup()
	{
		itagController.deleteAll();

		customerHelper.deleteAll(clientContext);
		customerPricePlanHelper.deleteAll(clientContext);
	}
}
