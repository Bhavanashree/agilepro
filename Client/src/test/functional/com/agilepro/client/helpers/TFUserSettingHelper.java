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
import com.agilepro.commons.controllers.admin.IProjectController;
import com.agilepro.commons.controllers.admin.IUserSettingController;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.ProjectModel;
import com.agilepro.commons.models.customer.UserSettingModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.ClientControllerFactory;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Class TFUserSettingHelper.
 *
 * @param <MultipartHttpServletRequest>
 *            the generic type
 * 
 * @author Pritam
 */
public class TFUserSettingHelper<MultipartHttpServletRequest> extends TFBase implements ITestConstants
{
	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(TFUserSettingHelper.class);

	/**
	 * The customer price plan helper.
	 **/
	private static CustomerPricePlanHelper customerPricePlanHelper = new CustomerPricePlanHelper();

	/**
	 * The customer helper.
	 **/
	private static CustomerHelper customerHelper = new CustomerHelper();

	/**
	 * The test project id.
	 */
	private static String TEST_PROJECT_KEY = "activeProjectId";

	/**
	 * customerId.
	 */
	private Long customerId;

	/**
	 * The due amount paid by customer.
	 */
	private final double dueAmount = 10000.0;

	/**
	 * The Session object.
	 */
	private ClientContext customerSession;

	/**
	 * The iuser setting controller.
	 **/
	private IUserSettingController iuserSettingController;

	/**
	 * The iproject controller.
	 **/
	private IProjectController<MultipartHttpServletRequest> iprojectController;

	/**
	 * The project id.
	 **/
	private List<Long> projectIds = new ArrayList<Long>();

	/**
	 * The user id.
	 **/
	private Long userId;

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
		CustomerModel model = new CustomerModel("Customer1", T_CUS_EMAIL_ID, T_PHONE_NUMBER, null, null, null, null, new Date(), T_PASSWORD, T_PASSWORD, T_CUS_PATH, null);
		model.setCustomerPricePlanId(idOfPricePlan);
		model.setDueAmount(dueAmount);
		customerId = customerHelper.save(clientContext, model);
		Assert.assertTrue(idOfPricePlan > 0);
		Assert.assertTrue(customerId > 0);

		customerSession = super.newClientContext(T_CUS_EMAIL_ID, T_PASSWORD, customerId);

		userId = customerSession.getUserId();

		clientControllerFactory = new ClientControllerFactory(customerSession);

		saveProjects();

		iuserSettingController = clientControllerFactory.getController(IUserSettingController.class);
	}

	/**
	 * Save projects.
	 */
	@SuppressWarnings("unchecked")
	private void saveProjects()
	{
		iprojectController = clientControllerFactory.getController(IProjectController.class);

		ProjectModel projectModel = new ProjectModel();
		projectModel.setName("Project1");
		BasicSaveResponse basicSaveResponse = iprojectController.save(projectModel, null);
		projectIds.add(basicSaveResponse.getId());

		projectModel = new ProjectModel();
		projectModel.setName("Project2");
		basicSaveResponse = iprojectController.save(projectModel, null);
		projectIds.add(basicSaveResponse.getId());

		projectModel = new ProjectModel();
		projectModel.setName("Project3");
		basicSaveResponse = iprojectController.save(projectModel, null);
		projectIds.add(basicSaveResponse.getId());
	}

	/**
	 * Test read.
	 *
	 * @param userId
	 *            the user id
	 * @return the user setting model
	 */
	public UserSettingModel testReadUserSetting(Long userId)
	{
		BasicReadResponse<UserSettingModel> basicReadResponse = iuserSettingController.read(userId);

		return basicReadResponse.getModel();
	}

	/**
	 * Test save.
	 */
	@Test
	public void testSaveUserSetting()
	{
		UserSettingModel userSettingModel = new UserSettingModel(userId, TEST_PROJECT_KEY, projectIds.get(0).toString());
		BasicSaveResponse basicSaveResponse = iuserSettingController.save(userSettingModel);

		userSettingModel = testReadUserSetting(userId);

		Assert.assertTrue(basicSaveResponse.getId() > 0);
		Assert.assertEquals(userSettingModel.getValue(), projectIds.get(0).toString());

		iuserSettingController.deleteAll();
	}

	/**
	 * Test update.
	 */
	@Test
	public void testUpdateUserSetting()
	{
		UserSettingModel userSettingModel = new UserSettingModel(userId, TEST_PROJECT_KEY, projectIds.get(1).toString());
		iuserSettingController.save(userSettingModel);

		userSettingModel = testReadUserSetting(customerSession.getUserId());

		UserSettingModel modelForUpdate = new UserSettingModel(userId, TEST_PROJECT_KEY, projectIds.get(2).toString());
		modelForUpdate.setVersion(userSettingModel.getVersion());
		modelForUpdate.setId(userSettingModel.getId());

		iuserSettingController.update(modelForUpdate);
	}

	/**
	 * cleanup.
	 */
	@AfterClass
	public void cleanup()
	{
		iuserSettingController.deleteAll();
		iprojectController.deleteAll();

		customerHelper.deleteAll(clientContext);
		customerPricePlanHelper.deleteAll(clientContext);
	}
}
