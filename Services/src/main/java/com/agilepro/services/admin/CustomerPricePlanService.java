package com.agilepro.services.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanVariable;
import com.agilepro.controller.Expression;
import com.agilepro.jobs.CustomerBillingJob;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.persistence.entity.admin.CustomerInvoiceDetailsEntity;
import com.agilepro.persistence.entity.admin.CustomerPricePlanEntity;
import com.agilepro.persistence.repository.admin.ICustomerInvoiceDetailsRepository;
import com.agilepro.persistence.repository.admin.ICustomerPricePlanRepository;
import com.agilepro.persistence.repository.admin.ICustomerRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.TransactionException;
import com.yukthi.utils.exceptions.InvalidArgumentException;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class PricePlanService is responsible to save,read,update and delete the
 * price plan. It also validates the expression list of available price plans
 */
@Service
public class CustomerPricePlanService extends BaseCrudService<CustomerPricePlanEntity, ICustomerPricePlanRepository>
{
	/**
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(CustomerPricePlanService.class);

	/**
	 * The def runtime variables.
	 */
	private static Map<String, String> DEF_RUNTIME_VARIABLES = new TreeMap<String, String>();

	/**
	 * The customer repository.
	 */
	private ICustomerRepository customerRepository;

	/**
	 * The customer invoice details repository.
	 */
	private ICustomerInvoiceDetailsRepository customerInvoiceDetailsRepository;

	static
	{
		DEF_RUNTIME_VARIABLES.put(CustomerBillingJob.DEF_VAR_STORAGE_IN_MB, "Storage used by customer");
		DEF_RUNTIME_VARIABLES.put(CustomerBillingJob.DEF_VAR_PROJECT_COUNT, "no. of projects used by customer");
		DEF_RUNTIME_VARIABLES.put(CustomerBillingJob.DEF_VAR_USER_COUNT, "no. of users of the customer");
		DEF_RUNTIME_VARIABLES.put(CustomerBillingJob.DEF_VAR_TOTAL_VAL, "Current total value");
	}

	/**
	 * Instantiates a new customer price plan service.
	 */
	public CustomerPricePlanService()
	{
		super(CustomerPricePlanEntity.class, ICustomerPricePlanRepository.class);
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init()
	{
		customerRepository = super.repositoryFactory.getRepository(ICustomerRepository.class);
		customerInvoiceDetailsRepository = super.repositoryFactory.getRepository(ICustomerInvoiceDetailsRepository.class);
	}

	/**
	 * Save the Customer Price Plan Entity.
	 *
	 * @param pricePlanModel
	 *            the price plan model
	 * @return the long ID generated
	 */

	public long save(CustomerPricePlanModel pricePlanModel) throws RuntimeException
	{
		logger.trace("Save is called on model - {}", pricePlanModel);
		expressionValidate(pricePlanModel);

		try
		{
			CustomerPricePlanEntity entity = super.save(pricePlanModel);

			return entity.getId();
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while saving price plan - {}", pricePlanModel);
		}
	}

	/**
	 * Update the Customer Price Plan Entity.
	 *
	 * @param pricePlanModel
	 *            the price plan model
	 */
	public void update(CustomerPricePlanModel pricePlanModel)
	{
		logger.trace("Update is called on model - {}", pricePlanModel);

		if(pricePlanModel == null)
		{
			logger.error("Customer Price Plan Model is null.");
			throw new NullPointerException("CustomerPricePlanModel is null.");
		}

		expressionValidate(pricePlanModel);

		try
		{
			super.update(pricePlanModel);
		} catch(Exception ex)
		{
			logger.error("An error occurred while updating price plan", ex);
			throw new InvalidStateException(ex, "An error occurred while updating price plan ", pricePlanModel);
		}
	}

	/**
	 * Expression validate.
	 *
	 * @param pricePlanModel
	 *            the price plan model
	 */
	private void expressionValidate(CustomerPricePlanModel pricePlanModel)
	{
		if(pricePlanModel.getExpressions() == null)
		{
			throw new InvalidRequestParameterException("Expressions are empty");
		}

		// maintains the expression names which are evaluated to current time
		Set<String> prevExprNames = new HashSet<>();

		for(CustomerPricePlanExpression expr : pricePlanModel.getExpressions())
		{
			try
			{
				if(!isExpressionValid(expr.getExpression(), pricePlanModel, prevExprNames))
				{
					logger.error("Error occured during validation.Expression is invalid - {}", expr.getExpression());
					throw new InvalidRequestParameterException("Expression is invalid. Please provide valid expression.");
				}

				prevExprNames.add(expr.getName());
			} catch(RuntimeException ex)
			{
				logger.error("Error occured while validating the expressions ", ex);
				throw new InvalidRequestParameterException(ex, "An error occurred while evaulating expression : {}\nError: {}", expr.getExpression(), ex.getMessage());
			}
		}
	}

	/**
	 * Checks if is expression valid.
	 *
	 * @param expression
	 *            the expression which is going to evaluate
	 * @param pricePlan
	 *            the price plan
	 * @param prevExprNames
	 *            the prev expr names
	 * @return true, if is expression valid
	 * @throws RuntimeException
	 *             if the expression has some error
	 */
	private boolean isExpressionValid(String expression, CustomerPricePlanModel pricePlan, Set<String> prevExprNames) throws RuntimeException
	{
		if(expression == null || expression.trim().length() == 0)
		{
			throw new InvalidArgumentException("Empty expression specified");
		}

		Expression ex = new Expression(expression);

		if(pricePlan.getNumericVariables() != null)
		{
			// add price plan variables to expression before validations
			for(CustomerPricePlanVariable variable : pricePlan.getNumericVariables())
			{
				ex.setVariable(variable.getName(), new BigDecimal(1));
			}
		}

		// add default runtime variables to expression before validation
		for(String runtimeVar : DEF_RUNTIME_VARIABLES.keySet())
		{
			ex.setVariable(runtimeVar, new BigDecimal(1));
		}

		// add prev expressions as variables
		for(String exprName : prevExprNames)
		{
			ex.setVariable(exprName, new BigDecimal(1));
		}

		ex.validateExpression();

		return true;
	}

	/**
	 * Adds the invoice details.
	 *
	 * @param dueAmount
	 *            the due amount of customer
	 * @param lastEvalDate
	 *            the last transaction date
	 * @param nextEvalDate
	 *            the next transaction date
	 * @param entity
	 *            the customer price plan entity
	 * @param invoiceLst
	 *            the customer invoice detail entity
	 */
	public void addInvoiceDetails(double dueAmount, Date lastEvalDate, Date nextEvalDate, CustomerEntity entity, List<CustomerInvoiceDetailsEntity> invoiceLst)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			if(!customerRepository.updateCustomer(dueAmount, lastEvalDate, nextEvalDate, entity.getId(), entity.getVersion()))
			{
				throw new InvalidStateException("Failed to update customer invoice details - {}", entity.getId());
			}

			// add invoice entries
			for(CustomerInvoiceDetailsEntity invoice : invoiceLst)
			{
				super.userService.populateTrackingFieldForCreate(invoice);

				if(!customerInvoiceDetailsRepository.save(invoice))
				{
					throw new InvalidStateException("Failed to update save invoice details - {}", invoice);
				}
			}

			transaction.commit();
		} catch(TransactionException ex)
		{
			throw new InvalidStateException(ex, "An error occurred while creating invoice details");
		}
	}

	/**
	 * Gets the runtime variables.
	 *
	 * @return the runtime variables
	 */
	public Map<String, String> getRuntimeVariables()
	{
		return DEF_RUNTIME_VARIABLES;
	}

	/**
	 * Delete all customer price plan.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
