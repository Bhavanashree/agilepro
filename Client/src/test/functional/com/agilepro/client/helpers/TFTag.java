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
public class TFTag extends TFBase implements ITestConstants
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
		CustomerModel model = new CustomerModel("Test1", T_CUS_EMAIL_ID, T_PHONE_NUMBER, null, null, null, null, new Date(), T_PASSWORD, T_PASSWORD, "path1", null);
		model.setCustomerPricePlanId(idOfPricePlan);
		model.setDueAmount(T_DUE_AMOUNT);
		customerId = customerHelper.save(clientContext, model);
		Assert.assertTrue(idOfPricePlan > 0);
		Assert.assertTrue(customerId > 0);

		customerSession = super.newClientContext(T_CUS_EMAIL_ID, T_PASSWORD, customerId);
		
		clientControllerFactory = new ClientControllerFactory(customerSession);
		
		itagController = clientControllerFactory.getController(ITagController.class);
	}

	private TagModel getTag(long tagId)
	{
		BasicReadResponse<TagModel> response = itagController.read(tagId);
		
		return response.getModel();
	}
	
	@Test
	public void testSaveTag()
	{
		TagModel tagsModel = new TagModel("FirstTag");
		tagsModel.setDescription(T_TAG_DESCRIPTION);

		BasicSaveResponse responseTag = itagController.save(tagsModel);

		Assert.assertEquals(responseTag.getCode(), IWebUtilsCommonConstants.RESPONSE_CODE_SUCCESS);
		
		tagsModel = getTag(responseTag.getId());
		
		Assert.assertTrue(tagsModel.getId() > 0);
	}

	@Test
	public void testReadTag()
	{
		String tagName = "SecondTag";
		TagModel tagsModel = new TagModel(tagName);
		tagsModel.setDescription(T_TAG_DESCRIPTION);

		BasicSaveResponse responseTag = itagController.save(tagsModel);

		TagModel fetchdModel = getTag(responseTag.getId());
		Assert.assertEquals(fetchdModel.getName(), tagName);
		Assert.assertEquals(fetchdModel.getDescription(), T_TAG_DESCRIPTION);
	}

	@Test
	public void testUpdateTag()
	{
		String tagName = "FourthTag";
		TagModel tagsModel = new TagModel("ThirdTag");
		tagsModel.setDescription(T_TAG_DESCRIPTION);

		BasicSaveResponse responseTag = itagController.save(tagsModel);

		TagModel fetchdModel = getTag(responseTag.getId());

		fetchdModel.setName(tagName);
		fetchdModel.setDescription(T_TAG_DESCRIPTION);

		itagController.update(fetchdModel);

		TagModel updatedModel = getTag(fetchdModel.getId());
		
		Assert.assertEquals(updatedModel.getName(), tagName);
		Assert.assertEquals(updatedModel.getDescription(), T_TAG_DESCRIPTION);
	}

	@Test
	public void testDeleteTag()
	{
		TagModel tagsModel = new TagModel("FifthTag");
		tagsModel.setDescription(T_TAG_DESCRIPTION);

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
