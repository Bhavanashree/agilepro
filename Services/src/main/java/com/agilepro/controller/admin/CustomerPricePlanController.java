package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_PRICEPLAN;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_INVOKE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_VAR;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_RUNTIME_VARIABLES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanExpression;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanVariable;
import com.agilepro.commons.models.customer.priceplan.Functions;
import com.agilepro.commons.models.customer.priceplan.PricePlanHelpModel;
import com.agilepro.commons.models.customer.priceplan.RuntimeVariables;
import com.agilepro.controller.Expression;
import com.agilepro.controller.Expression.Function;
import com.agilepro.services.admin.CustomerPricePlanService;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;
import com.yukthi.webutils.services.job.IJob;

/**
 * The Class PricePlanController is responsible for receiving the requests from
 * Client. Once received , it directs the request to the service class
 * (CustomerPricePlanService). It also takes care for sending the response back
 * to the client received from service class.
 */
@RestController
@ActionName(ACTION_PREFIX_PRICEPLAN)
@RequestMapping("/priceplan")
public class CustomerPricePlanController extends BaseController
{

	/**
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(CustomerPricePlanController.class);

	/**
	 * The price plan service.
	 */
	@Autowired
	private CustomerPricePlanService pricePlanService;

	/**
	 * The application context.
	 */
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * The job.
	 */
	private IJob job;

	/**
	 * Validate.
	 *
	 * @param model
	 *            the model
	 */
	private void validate(CustomerPricePlanModel model)
	{
		Set<String> varNames = new HashSet<>();
		Set<String> exprNames = new HashSet<>();

		// ensure variable names are not duplicated
		if(model.getNumericVariables() != null)
		{
			for(CustomerPricePlanVariable var : model.getNumericVariables())
			{
				if(varNames.contains(var.getName()))
				{
					throw new InvalidRequestParameterException("Multiple variables defined with same name - {}", var.getName());
				}

				varNames.add(var.getName());
			}

			// ensure expressions names are not duplicated and not overlapping
			// with var names
			for(CustomerPricePlanExpression expr : model.getExpressions())
			{
				if(varNames.contains(expr.getName()))
				{
					throw new InvalidRequestParameterException("Same name used for variable and expression - {}", expr.getName());
				}

				if(exprNames.contains(expr.getName()))
				{
					throw new InvalidRequestParameterException("Multiple expressions defined with same name - {}", expr.getName());
				}

				exprNames.add(expr.getName());
			}
		}
	}

	/**
	 * Save the customer price plan.
	 *
	 * @param model  
	 *            customer price plan
	 * @return the price plan save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid CustomerPricePlanModel model)
	{
		logger.trace("Received request to save price plan - {}", model.getName());

		validate(model);

		Long id = pricePlanService.save(model);

		return new BasicSaveResponse(id);
	}

	/**
	 * Read the customer price plan.
	 *
	 * @param id
	 *            id of customer price plan
	 * 
	 * @return the price plan save response
	 */
	@ActionName(ACTION_TYPE_READ)
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<CustomerPricePlanModel> read(@PathVariable(PARAM_ID) Long id)
	{
		logger.trace("Received request to read price plan tracked with ID ", id);

		CustomerPricePlanModel model = pricePlanService.fetchFullModel(id, CustomerPricePlanModel.class);

		return new BasicReadResponse<CustomerPricePlanModel>(model);
	}

	/**
	 * Controller method to fetch variable names for specified price plan id.
	 * 
	 * @param id
	 *            the customer price plan id
	 * @return customer price plan model
	 */
	@ActionName(ACTION_TYPE_READ_VAR)
	@RequestMapping(value = "/readVar/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<Set<CustomerPricePlanVariable>> readVariableNames(@PathVariable(PARAM_ID) Long id)
	{
		logger.trace("Sending request to read price plan tracked with ID ", id);

		CustomerPricePlanModel model = pricePlanService.fetchFullModel(id, CustomerPricePlanModel.class);

		if(model.getNumericVariables() == null || model.getNumericVariables().isEmpty())
		{
			return new BasicReadResponse<>(Collections.emptySet());
		}

		// build set of variable names
		return new BasicReadResponse<>(new TreeSet<>(model.getNumericVariables()));
	}

	/**
	 * Update the customer price plan.
	 *
	 * @param pricePlan
	 *            the price plan
	 * 
	 * @return the price plan save response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid CustomerPricePlanModel pricePlan)
	{
		logger.trace("Sending request to update price plan ", pricePlan.getName());

		if(pricePlan.getId() == null || pricePlan.getId() <= 0)
		{
			throw new InvalidRequestParameterException("Invalid id specified for update: " + pricePlan.getId());
		}

		validate(pricePlan);
		pricePlanService.update(pricePlan);

		return new BaseResponse();
	}

	/**
	 * Delete the customer price plan.
	 *
	 * @param id
	 *            the id of customer price plan
	 * 
	 * @return the price plan save response
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)

	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		logger.trace("Sending request to delete price plan tracked with ID - {}", id);

		pricePlanService.deleteById(id);

		return new BaseResponse();
	}

	/**
	 * Delete all customer price plan.
	 *
	 * @return the base response
	 */
	@ResponseBody
	@RequestMapping("/deleteAll")
	@ActionName("deleteAll")
	public BaseResponse deleteAll()
	{
		pricePlanService.deleteAll();
		return new BaseResponse();
	}

	/**
	 * Calculate due amount of customer.
	 *
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_INVOKE)
	@RequestMapping(value = "/invoke", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse calculateAmount()
	{

		try
		{
			job = (IJob) Class.forName("com.cloudbiller.jobs.CustomerBillingJob").newInstance();
		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
		applicationContext.getAutowireCapableBeanFactory().autowireBean(job);
		applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsBeforeInitialization(job, null);
		try
		{
			job.execute(null, null);
		} catch(JobExecutionException e)
		{

			e.printStackTrace();
		}
		return new BaseResponse();
	}

	/**
	 * Gets the runtime variables list.
	 *
	 * @param map
	 *            the map
	 * @return the runtime variables list
	 */
	private List<RuntimeVariables> getRuntimeVariablesList(Map<String, String> map)
	{
		List<RuntimeVariables> list = new ArrayList<RuntimeVariables>();
		Iterator<Entry<String, String>> set = map.entrySet().iterator();
		while(set.hasNext())
		{
			Entry<String, String> entry = set.next();
			list.add(new RuntimeVariables(entry.getKey(), entry.getValue()));
		}
		return list;
	}

	/**
	 * Gets the functions.
	 *
	 * @param function
	 *            the function
	 * @return the list of price plans functions
	 */
	private List<Functions> getfunctions(Collection<Function> function)
	{
		Iterator<Function> it = function.iterator();
		List<Functions> list = new ArrayList<Functions>();
		while(it.hasNext())
		{
			Function fun = it.next();
			list.add(new Functions(fun.getName(), fun.getDescription()));
		}
		return list;
	}

	/**
	 * Gets the price plan information.
	 *
	 * @return the price plan information
	 */
	@ActionName(ACTION_TYPE_RUNTIME_VARIABLES)
	@RequestMapping(value = "/readRuntimeVariables", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<PricePlanHelpModel> getPricePlanInformation()
	{
		PricePlanHelpModel model = new PricePlanHelpModel();
		Map<String, String> rmap = pricePlanService.getRuntimeVariables();
		model.setRuntimeVariables(getRuntimeVariablesList(rmap));
		Expression ex = new Expression("1+2");
		Collection<Function> func = ex.getFunctions();
		model.setFunctions(getfunctions(func));
		return new BasicReadResponse<PricePlanHelpModel>(model);
	}
}
