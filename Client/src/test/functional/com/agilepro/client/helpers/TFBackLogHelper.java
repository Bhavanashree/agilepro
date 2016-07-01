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

import com.agilepro.commons.IBackLogController;
import com.agilepro.commons.PaymentCycle;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.agilepro.commons.models.projects.BackLogModel;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.ClientControllerFactory;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class TfBackLogHelper.
 */
public class TFBackLogHelper extends TFBase
{

	/** The logger. */
	private static Logger logger = LogManager.getLogger(TFBackLogHelper.class);
	/**
	 * The i campaign controller.
	 **/
	private IBackLogController backlogcontroller;

	/**
	 * The customer helper.
	 **/
	private CustomerHelper customerHelper = new CustomerHelper();
	/**
	 * The price plan helper.
	 **/
	private CustomerPricePlanHelper pricePlanHelper = new CustomerPricePlanHelper();
	/**
	 * The Session object.
	 */
	private ClientContext clientCurrentSession;
	/**
	 * customerId.
	 */
	private Long customerId;
	/**
	 * basicsave response.
	 */
	private BasicSaveResponse response1;
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
	 * The due amount paid by customer.
	 */
	private final double dueAmount = 10000.0;

	/**
	 * before class saving price plan, customer , campaign type.
	 */
	@BeforeClass
	public void setup()
	{
		backlogcontroller = (IBackLogController) super.clientControllerFactory.getController(IBackLogController.class);

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
		clientCurrentSession = super.newClientContext(emailId, password, customerId);
		// camp type

		clientControllerFactory = new ClientControllerFactory(clientCurrentSession);

	}

	/**
	 * Gets the backlog.
	 *
	 * @param backlogId
	 *            the backlog id
	 * @return the backlog
	 */
	private BackLogModel getBacklog(long backlogId)
	{
		BasicReadResponse<BackLogModel> response = backlogcontroller.read(backlogId);
		return response.getModel();
	}

	/**
	 * Test save.
	 */
	@Test
	public void testSave()
	{
		clientControllerFactory = new ClientControllerFactory(clientCurrentSession);

		backlogcontroller = (IBackLogController) super.clientControllerFactory.getController(IBackLogController.class);

		BackLogModel model = new BackLogModel();
		model.setTitle("studentGrade");
		model.setDescription("hello");
		model.setEstimate(23);
		BasicSaveResponse response2 = backlogcontroller.save(model);

		Assert.assertEquals(response2.getCode(), IWebUtilsCommonConstants.RESPONSE_CODE_SUCCESS);
		// validate save
		getBacklog(response2.getId());
	}

	/**
	 * Test update.
	 */
	@Test
	public void testUpdate()
	{
		clientControllerFactory = new ClientControllerFactory(clientCurrentSession);

		backlogcontroller = (IBackLogController) super.clientControllerFactory.getController(IBackLogController.class);

		BackLogModel model = new BackLogModel();
		model.setTitle("studentGrade");
		model.setDescription("hello");
		model.setEstimate(23);

		// validate save
		BasicSaveResponse response3 = backlogcontroller.save(model);

		Assert.assertEquals(response3.getCode(), IWebUtilsCommonConstants.RESPONSE_CODE_SUCCESS);

		// validate save
		getBacklog(response3.getId());

		BackLogModel model2 = new BackLogModel();
		model2.setTitle("studentGraxxde");
		model2.setDescription("hello");
		model2.setEstimate(23);
		model2.setId(response3.getId());
		backlogcontroller.update(model2);
	}

	/**
	 * Test delete.
	 */
	@Test
	public void testDelete()
	{
		clientControllerFactory = new ClientControllerFactory(clientCurrentSession);

		backlogcontroller = (IBackLogController) super.clientControllerFactory.getController(IBackLogController.class);

		BackLogModel model = new BackLogModel();
		model.setTitle("studentGrade");
		model.setDescription("hello");
		model.setEstimate(23);
		BasicSaveResponse response = backlogcontroller.save(model);

		Assert.assertEquals(response.getCode(), IWebUtilsCommonConstants.RESPONSE_CODE_SUCCESS);

		// validate save
		getBacklog(response.getId());
		backlogcontroller.delete(response.getId());
	}

	/**
	 * Clean.
	 */
	@AfterClass
	private void clean()
	{
		backlogcontroller.deleteAll();
		backlogcontroller.deleteAll();
		customerHelper.deleteAll(clientContext);
		pricePlanHelper.deleteAll(clientContext);
	}
}
