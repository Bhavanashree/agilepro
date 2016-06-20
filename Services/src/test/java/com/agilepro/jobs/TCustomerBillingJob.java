package com.agilepro.jobs;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.JobExecutionException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.agilepro.commons.PaymentCycle;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanVariable;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.persistence.entity.admin.CustomerInvoiceDetailsEntity;
import com.agilepro.persistence.entity.admin.CustomerPricePlanEntity;
import com.agilepro.persistence.repository.admin.ICustomerRepository;
import com.agilepro.services.admin.CustomerPricePlanService;
import com.yukthi.utils.ReflectionUtils;

/**
 * The Class TCustomerBillingJob.
 */
public class TCustomerBillingJob
{

	/**
	 * The price plan variable name.
	 */
	private static final String VAR_NAME = "projects";

	/**
	 * The price plan variable value.
	 */
	private static final double VAR_VALUE = 10.0;

	/**
	 * The price plan expr name.
	 */
	private static final String EXPR_NAME = "expr";

	/**
	 * The customer price plan expression.
	 */
	private static final String EXRESSION = "projects + 2";

	/**
	 * The customer repository.
	 */
	private static final String CUST_REP0 = "customerRepository";

	/**
	 * The price plan service.
	 */
	private static final String PLAN_SERVICE = "pricePlanService";

	/**
	 * Test execute.
	 * 
	 * @param <E>
	 *
	 * @throws JobExecutionException
	 *             the job execution exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testExecute() throws JobExecutionException
	{
		final int add = -2;
		final double dueAmount = 24.0;
		// create instance of target class
		CustomerBillingJob customerBillingJob = new CustomerBillingJob();

		ICustomerRepository mockCustomerRepository = mock(ICustomerRepository.class);
		CustomerPricePlanService mockPricePlanService = mock(CustomerPricePlanService.class);

		CustomerPricePlanVariable var = new CustomerPricePlanVariable(VAR_NAME, "var1", VAR_VALUE);
		CustomerPricePlanExpression ex = new CustomerPricePlanExpression(EXPR_NAME, "exp1", EXRESSION);

		Date date = new Date();
		date = DateUtils.addMonths(date, add);
		CustomerPricePlanEntity pricePlanmodel = new CustomerPricePlanEntity((long) 1, "First", null, Arrays.asList(var), Arrays.asList(ex), PaymentCycle.Monthly);
		CustomerEntity entity = new CustomerEntity(null, pricePlanmodel, 0.0, null, null, null);
		entity.setRegistrationDate(date);
		CustomerEntity entity1 = new CustomerEntity(null, pricePlanmodel, 0.0, null, null, null);
		entity1.setRegistrationDate(date);
		List<CustomerEntity> customerList = Arrays.asList(entity, entity1);
		when(mockCustomerRepository.findCustomerByPayDate(any(Date.class))).thenReturn(customerList);

		ReflectionUtils.setFieldValue(customerBillingJob, CUST_REP0, mockCustomerRepository);
		ReflectionUtils.setFieldValue(customerBillingJob, PLAN_SERVICE, mockPricePlanService);

		// execute test method
		customerBillingJob.execute(null, null);

		// validate the result
		Assert.assertEquals(entity.getDueAmount(), dueAmount);

		// Verification
		verify(mockPricePlanService).addInvoiceDetails(eq(dueAmount), any(Date.class), any(Date.class), eq(entity1), (List<CustomerInvoiceDetailsEntity>) any(CustomerInvoiceDetailsEntity.class));
	}

	/**
	 * Test calculate due amount.
	 *
	 * @throws JobExecutionException
	 *             the job execution exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCalculateDueAmount() throws JobExecutionException
	{
		final int add = -2;
		final double defVal = 15.0;
		final double dueAmount = 34.0;
		// create instance of target class
		CustomerBillingJob customerBillingJob = new CustomerBillingJob();

		// set test data on target object
		ICustomerRepository mockCustomerRepository = mock(ICustomerRepository.class);
		CustomerPricePlanService mockPricePlanService = mock(CustomerPricePlanService.class);

		CustomerPricePlanVariable var = new CustomerPricePlanVariable(VAR_NAME, "var2", VAR_VALUE);
		CustomerPricePlanExpression ex = new CustomerPricePlanExpression(EXPR_NAME, "exp2", EXRESSION);
		CustomerPricePlanEntity pricePlanmodel = new CustomerPricePlanEntity((long) 1, "Second", null, Arrays.asList(var), Arrays.asList(ex), PaymentCycle.Monthly);
		Map<String, Double> variableMap = new HashMap<String, Double>();
		variableMap.put(VAR_NAME, defVal);
		Date date = new Date();
		date = DateUtils.addMonths(date, add);
		CustomerEntity entity = new CustomerEntity(null, pricePlanmodel, 0.0, variableMap, null, null);
		CustomerEntity entity1 = new CustomerEntity(null, pricePlanmodel, 0.0, variableMap, null, null);
		entity.setRegistrationDate(date);
		entity1.setRegistrationDate(date);
		List<CustomerEntity> customerList = Arrays.asList(entity, entity1);
		when(mockCustomerRepository.findCustomerByPayDate(any(Date.class))).thenReturn(customerList);

		ReflectionUtils.setFieldValue(customerBillingJob, CUST_REP0, mockCustomerRepository);
		ReflectionUtils.setFieldValue(customerBillingJob, PLAN_SERVICE, mockPricePlanService);
		// execute test method
		customerBillingJob.execute(null, null);

		// validate the result
		Assert.assertEquals(entity.getDueAmount(), dueAmount);
		// Verification
		verify(mockPricePlanService).addInvoiceDetails(eq(dueAmount), any(Date.class), any(Date.class), eq(entity1), (List<CustomerInvoiceDetailsEntity>) any(CustomerInvoiceDetailsEntity.class));
	}

	/**
	 * Test default value.
	 *
	 * @throws JobExecutionException
	 *             the job execution exception
	 */
	@Test
	public void testDefaultValue() throws JobExecutionException
	{
		final int add = -2;
		// create instance of target class
		CustomerBillingJob customerBillingJob = new CustomerBillingJob();

		// set test data on target object
		ICustomerRepository mockCustomerRepository = mock(ICustomerRepository.class);
		CustomerPricePlanVariable var = new CustomerPricePlanVariable(VAR_NAME, "var3", null);
		CustomerPricePlanExpression ex = new CustomerPricePlanExpression(EXPR_NAME, "exp3", EXRESSION);

		Date date = new Date();
		date = DateUtils.addMonths(date, add);
		CustomerPricePlanEntity pricePlanmodel = new CustomerPricePlanEntity((long) 1, "Third", null, Arrays.asList(var), Arrays.asList(ex), PaymentCycle.Monthly);
		CustomerEntity entity = new CustomerEntity(null, pricePlanmodel, 0.0, null, null, null);
		CustomerEntity entity1 = new CustomerEntity(null, pricePlanmodel, 0.0, null, null, null);
		entity.setRegistrationDate(date);
		entity1.setRegistrationDate(date);
		List<CustomerEntity> customerList = Arrays.asList(entity, entity1);
		when(mockCustomerRepository.findCustomerByPayDate(any(Date.class))).thenReturn(customerList);

		ReflectionUtils.setFieldValue(customerBillingJob, CUST_REP0, mockCustomerRepository);

		// execute test method
		try
		{
			customerBillingJob.execute(null, null);
			Assert.assertTrue(false);
		} catch(Exception exception)
		{
			Assert.assertTrue(exception.getMessage().contains("value not provided for price-plan"), exception.getMessage());
		}
	}
}
