package com.agilepro.jobs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanVariable;
import com.agilepro.commons.models.customer.priceplan.ExpressionValueDetails;
import com.agilepro.commons.models.customer.priceplan.VariableValueDetails;
import com.agilepro.controller.Expression;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.persistence.entity.admin.CustomerInvoiceDetailsEntity;
import com.agilepro.persistence.entity.admin.CustomerPricePlanEntity;
import com.agilepro.persistence.repository.admin.ICustomerRepository;
import com.agilepro.services.admin.CustomerPricePlanService;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.annotations.CronJob;
import com.yukthi.webutils.services.job.IJob;

/**
 * The Class CustomerBillingJob is responsible for calculating price plan due
 * amount for customers.It calculates the due amount on daily basis i.e once in
 * 24 hours.
 */
// @CronJob(name = "CustomerBilling", cronExpression = "0 59 23 1/1 * ? *")
//@CronJob(name = "CustomerBilling", cronExpression = "0 0/60 * 1/1 * ? *")
public class CustomerBillingJob implements IJob
{
	/**
	 * Default variable representing project count.
	 */
	public static final String DEF_VAR_PROJECT_COUNT = "projectCount";

	/**
	 * Default variable representing storage quantity in MB.
	 */
	public static final String DEF_VAR_STORAGE_IN_MB = "storageInMb";
	
	/**
	 * Default variable representing current total value.
	 */
	public static final String DEF_VAR_TOTAL_VAL = "total";
	
	/**
	 * Default variable representing number of user by target customer.
	 */
	public static final String DEF_VAR_USER_COUNT = "userCount";

	/**
	 *  The Constant MB. 
	 */
	private static final double MB = 100.0;
	
	/**
	 *  The Constant COUNT. 
	 */
	private static final double COUNT = 10.0;
	/**
	 * The logger to log the important information .
	 */
	private static Logger logger = LogManager.getLogger(CustomerBillingJob.class);
	
	/**
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * The Customer repository.
	 */
	private ICustomerRepository customerRepository;

	/**
	 *  The price plan service. 
	 */
	@Autowired
	private CustomerPricePlanService pricePlanService;

	/**
	 * Used to get customer repository & customer invoice detail repository from
	 * repository factory.
	 */
	@PostConstruct
	private void init()
	{
		this.customerRepository = repositoryFactory.getRepository(ICustomerRepository.class);
	}

	/**
	 * Execute.
	 *
	 * @param jobData
	 *            the job data
	 * @param context
	 *            the context
	 * @throws JobExecutionException
	 *             the job execution exception
	 */
	@Override
	public void execute(Object jobData, JobExecutionContext context) throws JobExecutionException
	{
		logger.debug("Executing customer billing job...");
		Date curDate = DateUtils.truncate(new Date(), Calendar.DATE);

		CustomerInvoiceDetailsEntity invoiceDetail = null;

		List<CustomerEntity> customerEntityList = customerRepository.findCustomerByPayDate(curDate);

		if(customerEntityList == null || customerEntityList.isEmpty())
		{
			logger.debug("No customers found to process");
		}

		Date nextEvalDate = null, lastEvalDate = null;
		List<CustomerInvoiceDetailsEntity> invoiceLst = new ArrayList<>();
		Double dueAmount = null;

		// iterate over each customer entity and calculate due amount
		for(CustomerEntity custEntity : customerEntityList)
		{
			if(custEntity.getLastPayEvalDate() == null)
			{
				logger.debug("Found customer {} with null last eval date. Hence using registration date- {}", custEntity.getId(), custEntity.getRegistrationDate());

				custEntity.setLastPayEvalDate(custEntity.getRegistrationDate());

				nextEvalDate = custEntity.getCustomerPricePlan().getPaymentCycle().nextEvalDate(custEntity.getRegistrationDate());
				nextEvalDate = DateUtils.truncate(nextEvalDate, Calendar.DATE);

				custEntity.setNextPayEvalDate(nextEvalDate);

				if(custEntity.getNextPayEvalDate().after(curDate))
				{
					logger.debug("As customer {} next eval date '{}' is in future, ignoring billing amount evaluation", custEntity.getId(), nextEvalDate);

					customerRepository.updateCustomer(0, custEntity.getLastPayEvalDate(), nextEvalDate, custEntity.getId(), custEntity.getVersion());
					continue;
				}
			}

			nextEvalDate = DateUtils.truncate(custEntity.getNextPayEvalDate(), Calendar.DATE);
			invoiceLst.clear();
			dueAmount = custEntity.getDueAmount();

			if(dueAmount == null)
			{
				dueAmount = 0.0;
			}

			while(nextEvalDate.compareTo(curDate) <= 0)
			{
				logger.debug("Evaluating due amount for customer {} for evaluation date - {}", custEntity.getId(), nextEvalDate);

				lastEvalDate = nextEvalDate;
				nextEvalDate = custEntity.getCustomerPricePlan().getPaymentCycle().nextEvalDate(lastEvalDate);
				nextEvalDate = DateUtils.truncate(nextEvalDate, Calendar.DATE);

				invoiceDetail = new CustomerInvoiceDetailsEntity(custEntity.getId(), custEntity.getCustomerPricePlan().getId(), 0.0, lastEvalDate, curDate);

				calculateDueAmount(custEntity.getCustomerPricePlan(), custEntity, invoiceDetail);

				logger.debug("Evaluating due amount as {} for customer {} for evaluation date - {}", invoiceDetail.getTotalAmount(), custEntity.getId(), nextEvalDate);
				invoiceLst.add(invoiceDetail);

				dueAmount += invoiceDetail.getTotalAmount();
			}

			// update db with due amount and invoice details
			pricePlanService.addInvoiceDetails(dueAmount, lastEvalDate, nextEvalDate, custEntity, invoiceLst);

			// setting on customer entity for testability (in actual flow
			// this will not be used).
			custEntity.setDueAmount(dueAmount);
		}
	}

	/**
	 * Calculates the runtime variables.
	 * 
	 * @return Runtime variables with values
	 */
	private void getRuntimeVariables(Map<String, Double> variables, List<VariableValueDetails> variableValueList)
	{
		variables.put(DEF_VAR_STORAGE_IN_MB, MB);
		variables.put(DEF_VAR_PROJECT_COUNT, COUNT);
		variables.put(DEF_VAR_USER_COUNT, COUNT);

		variableValueList.add(new VariableValueDetails(DEF_VAR_STORAGE_IN_MB, DEF_VAR_STORAGE_IN_MB, MB));
		variableValueList.add(new VariableValueDetails(DEF_VAR_PROJECT_COUNT, DEF_VAR_PROJECT_COUNT, COUNT));
		variableValueList.add(new VariableValueDetails(DEF_VAR_USER_COUNT, DEF_VAR_USER_COUNT, COUNT));
	}

	/**
	 * Calculate due amount for customers.
	 *
	 * @param pricePlan
	 *            the price plan
	 * @param customer
	 *            the customer
	 * @param invoiceDetails
	 *            the detail
	 * @return total cost calculated from available expressions
	 */
	private void calculateDueAmount(CustomerPricePlanEntity pricePlan, CustomerEntity customer, CustomerInvoiceDetailsEntity invoiceDetails)
	{
		Map<String, Double> variableValueMap = new HashMap<>();

		List<VariableValueDetails> variableValueList = new ArrayList<>();
		List<ExpressionValueDetails> exprValueList = new ArrayList<>();

		// Get runtime variable details
		getRuntimeVariables(variableValueMap, variableValueList);

		// merge customer variable values with default values from price plan
		if(pricePlan.getNumericVariables() != null)
		{
			String varName = null;
			Double varValue = null;
			Map<String, Double> customerVariableValueMap = customer.getVariableMap();
			customerVariableValueMap = (customerVariableValueMap == null) ? new HashMap<>() : customerVariableValueMap;

			// loop through price plan variables, for variables for which value
			// is not defined at
			// customer level use default value from price plan
			for(CustomerPricePlanVariable variable : pricePlan.getNumericVariables())
			{
				varName = variable.getName();
				varValue = customerVariableValueMap.get(varName);

				if(varValue == null)
				{
					// if default value of variable is null throw error
					if(variable.getDefaultValue() == null)
					{
						logger.error("Required variables '{}' value not provided for price-plan '{}' in customer {}", variable.getName(), pricePlan.getName(), customer.getId());
						throw new InvalidStateException("Required variable '{}' value not provided for price-plan '{}' in customer {}", variable.getName(), pricePlan.getName(), customer.getId());
					}

					varValue = variable.getDefaultValue();
				}

				variableValueMap.put(varName, varValue);
				variableValueList.add(new VariableValueDetails(varName, variable.getLabel(), varValue));
			}
		}

		double totalCost = 0;
		Expression expression = null;
		double expValue = 0;

		// init total variable as zero value
		variableValueMap.put(DEF_VAR_TOTAL_VAL, totalCost);

		// iterate over each price plan expression in order to evaluate total
		// cost
		for(CustomerPricePlanExpression pricePlanExp : pricePlan.getExpressions())
		{
			expression = new Expression(pricePlanExp.getExpression());

			// add variables to expression
			for(String varName : variableValueMap.keySet())
			{
				expression.setVariable(varName, new BigDecimal(variableValueMap.get(varName)));
			}

			// evaluate expression value
			expValue = expression.eval().doubleValue();
			totalCost = totalCost + expValue;

			// add expression value to end invoice
			exprValueList.add(new ExpressionValueDetails(pricePlanExp.getName(), pricePlanExp.getLabel(), expValue));

			// add current expression value to variableValueMap, so that it is
			// available to following expressions
			variableValueMap.put(pricePlanExp.getName(), expValue);

			// add current total value to variableValueMap, so that updated
			// value is available in following expressions
			variableValueMap.put(DEF_VAR_TOTAL_VAL, totalCost);
		}

		// set the final values on invoice
		invoiceDetails.setVariableValues(variableValueList);
		invoiceDetails.setExpressionValues(exprValueList);
		invoiceDetails.setTotalAmount(totalCost);
	}
}