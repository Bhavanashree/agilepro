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
import com.agilepro.commons.ProjectMemberRole;
import com.agilepro.commons.controllers.admin.IEmployeeController;
import com.agilepro.commons.controllers.admin.IProjectController;
import com.agilepro.commons.controllers.admin.IProjectMemberController;
import com.agilepro.commons.models.admin.DesignationModel;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.ProjectMemberModel;
import com.agilepro.commons.models.customer.ProjectModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.ClientControllerFactory;
import com.yukthi.webutils.common.models.BasicReadResponse;

/**
 * The Class TFProjectMemberHelper.
 *
 * @param <MultipartHttpServletRequest>
 *            the generic type
 *            
 * @author Pritam
 */
public class TFProjectMemberHelper<MultipartHttpServletRequest> extends TFBase implements ITestConstants
{
	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(TFProjectMemberHelper.class);

	/**
	 * DesignationHelper object with default values.
	 */
	private DesignationHelper designationHelper = new DesignationHelper();

	/**
	 * CustomerHelper object with default values.
	 */
	private CustomerHelper customerHelper = new CustomerHelper();
	/**
	 * CustomerPricePlanHelper object with default values.
	 */
	private CustomerPricePlanHelper pricePlanHelper = new CustomerPricePlanHelper();
	/**
	 * customerId.
	 */
	private Long customerId;

	/**
	 * mailId.
	 */
	private String emailId = "customer@gmail.com";

	/**
	 * designationId.
	 */
	private Long designationId;

	/**
	 * designation name.
	 */
	private String designationName = "Manager";

	/**
	 * The Session object.
	 */
	private ClientContext clientCurrentSession;

	/**
	 * The I employee controller.
	 **/
	private IEmployeeController iemployeeController;

	/**
	 * The iproject member controller.
	 **/
	private IProjectMemberController iprojectMemberController;

	/**
	 * The iproject controller.
	 **/
	private IProjectController<MultipartHttpServletRequest> iprojectController;

	/**
	 * The employee ids.
	 **/
	private List<Long> employeeIds = new ArrayList<Long>();

	/**
	 * The project ids.
	 **/
	private List<Long> projectIds = new ArrayList<Long>();

	/**
	 * saving the pricePlan and customer before saving employee.
	 */
	@SuppressWarnings("unchecked")
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
		CustomerModel model = new CustomerModel("Test1", emailId, T_PHONE_NUMBER, null, null, null, null, new Date(), T_PASSWORD, T_PASSWORD, "path1", null);
		model.setCustomerPricePlanId(idOfPricePlan);
		model.setDueAmount(T_DUE_AMOUNT);
		customerId = customerHelper.save(clientContext, model);
		Assert.assertTrue(idOfPricePlan > 0);
		Assert.assertTrue(customerId > 0);

		clientCurrentSession = super.newClientContext(emailId, T_PASSWORD, customerId);

		// designation
		DesignationModel designationModel = new DesignationModel(0L, designationName, null, null);
		designationId = designationHelper.save(clientCurrentSession, designationModel);
		designationModel.setId(designationId);
		Assert.assertTrue(designationId > 0);

		clientControllerFactory = new ClientControllerFactory(clientCurrentSession);

		iemployeeController = clientControllerFactory.getController(IEmployeeController.class);
		iprojectController = clientControllerFactory.getController(IProjectController.class);
		iprojectMemberController = clientControllerFactory.getController(IProjectMemberController.class);

		saveProjects();
		saveEmployees();
	}

	/**
	 * Save employees.
	 */
	private void saveEmployees()
	{
		EmployeeModel employeeModel = new EmployeeModel("employee1", "emp1@gmail.com", T_PHONE_NUMBER, T_PASSWORD, T_PASSWORD, designationId);
		employeeIds.add(iemployeeController.save(employeeModel).getId());

		employeeModel = new EmployeeModel("employee2", "emp2@gmail.com", T_PHONE_NUMBER, T_PASSWORD, T_PASSWORD, designationId);
		employeeIds.add(iemployeeController.save(employeeModel).getId());

		employeeModel = new EmployeeModel("employee3", "emp3@gmail.com", T_PHONE_NUMBER, T_PASSWORD, T_PASSWORD, designationId);
		employeeIds.add(iemployeeController.save(employeeModel).getId());
	}

	/**
	 * Save projects.
	 */
	private void saveProjects()
	{
		ProjectModel projectModel = new ProjectModel("Project1");
		projectIds.add(iprojectController.save(projectModel, null).getId());

		projectModel = new ProjectModel("Project2");
		projectIds.add(iprojectController.save(projectModel, null).getId());

		projectModel = new ProjectModel("Project3");
		projectIds.add(iprojectController.save(projectModel, null).getId());
	}

	/**
	 * Gets the project member model.
	 *
	 * @param projectId
	 *            the project id
	 * @return the project member model
	 */
	private List<ProjectMemberModel> getProjectMemberModel(Long projectId)
	{
		/*BasicReadResponse<List<ProjectMemberModel>> basicReadResponse = iprojectMemberController.fetchProjectMembers(projectId);

		return basicReadResponse.getModel();*/
		
		return null;
	}

	/**
	 * Test save.
	 */
	@Test
	public void testSaveProjectMember()
	{
		ProjectMemberModel projectMemberModel = new ProjectMemberModel(projectIds.get(0), employeeIds.get(0), ProjectMemberRole.PROJECT_ADMIN);

		iprojectMemberController.save(projectMemberModel);

		List<ProjectMemberModel> projectMemberModels = getProjectMemberModel(projectIds.get(0));

		Assert.assertTrue(projectMemberModels.contains(projectMemberModel));
	}

	/**
	 * cleanup.
	 */
	@AfterClass
	public void cleanup()
	{
		iprojectMemberController.deleteAll();
		iemployeeController.deleteAll();
		iprojectController.deleteAll();

		designationHelper.deleteAll(clientCurrentSession);
		customerHelper.deleteAll(clientContext);
		pricePlanHelper.deleteAll(clientContext);
	}
}
