package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_CUSTOMER;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_BY_EMAIL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.CUSTOMER_POC_LIMIT;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.admin.ICustomerController;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.CustomerPocModel;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanVariable;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.persistence.entity.admin.CustomerPricePlanEntity;
import com.agilepro.services.admin.CustomerPricePlanService;
import com.agilepro.services.admin.CustomerService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.annotations.AttachmentsExpected;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class CustomerController This class is responsible for receiving the
 * requests from the user.Once received , it directs the request to the service
 * class (CustomerService). It also takes care for sending the response back to
 * the user received from service class.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_CUSTOMER)
@RequestMapping("/customer")
public class CustomerController extends BaseController implements ICustomerController<MultipartHttpServletRequest>
{
	/**
	 * Customer Service.
	 */
	@Autowired
	private CustomerService customerService;

	/**
	 * Used to fetch prices plan variables.
	 */
	@Autowired
	private CustomerPricePlanService customerPricePlanService;

	/**
	 * Validates required price plan variables are provided on model.
	 *
	 * @param model
	 *            the model
	 */

	private void validatePricePlanData(CustomerModel model)
	{
		long pricePlanId = model.getCustomerPricePlanId();

		// ensure valid price plan id is specified
		CustomerPricePlanEntity pricePlanEntity = customerPricePlanService.fetch(pricePlanId);

		if(pricePlanEntity == null)
		{
			throw new InvalidRequestParameterException("Invalid price plan id specified - {}", pricePlanId);
		}

		// check if the specified price plan has variables
		List<CustomerPricePlanVariable> numericVariables = pricePlanEntity.getNumericVariables();

		if(numericVariables == null || numericVariables.isEmpty())
		{
			model.setVariableMap(null);
			return;
		}

		// if variables are required by prices plan, ensure variables are
		// available on model
		Map<String, Double> planVariableMap = model.getVariableMap();

		if(planVariableMap == null || planVariableMap.isEmpty())
		{
			throw new InvalidRequestParameterException("No price plan variables specified.");
		}

		// ensure all required variables are provided
		Map<String, Double> newPlanVarMap = new HashMap<>();

		for(CustomerPricePlanVariable variable : numericVariables)
		{
			if(planVariableMap.get(variable.getName()) == null)
			{
				if(variable.getDefaultValue() == null)
				{
					throw new InvalidRequestParameterException("No value specified for price plan variable - " + variable.getName());
				}

				continue;
			}

			newPlanVarMap.put(variable.getName(), planVariableMap.get(variable.getName()));
		}
	}

	/**
	 * Validates Customer Poc limit 20.
	 *
	 * @param model
	 *            the model
	 */

	private void validateCustomerPoc(CustomerModel model)
	{
		List<CustomerPocModel> customerPocList = model.getCustomerPocModelList();

		if(customerPocList == null)
		{
			return;
		}

		if(customerPocList.size() > CUSTOMER_POC_LIMIT)
		{
			throw new InvalidRequestParameterException("Maximum number of Poc is 15");
		}
	}

	/**
	 * Save.
	 *
	 * @param customer
	 *            the customer
	 * @param request
	 *            the request
	 * @return the basic save response
	 */
	@Override
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@AttachmentsExpected
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestPart(IWebUtilsCommonConstants.MULTIPART_DEFAULT_PART) @Valid CustomerModel customer, MultipartHttpServletRequest request)
	{
		validatePricePlanData(customer);

		validateCustomerPoc(customer);

		CustomerEntity entity = customerService.save(customer);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read customer.
	 *
	 * @param id
	 *            the id
	 * @return the customer read response
	 */
	@Override
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ActionName(ACTION_TYPE_READ)
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<CustomerModel> read(@PathVariable(PARAM_ID) Long id)
	{
		CustomerModel customerModel = customerService.fetchFullModel(id, CustomerModel.class);

		return new BasicReadResponse<CustomerModel>(customerModel);
	}
	
	/* (non-Javadoc)
	 * @see com.agilepro.commons.controllers.admin.ICustomerController#fetchCustomerByEmail(java.lang.String)
	 */
	@Override
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ActionName(ACTION_TYPE_READ_BY_EMAIL)
	@RequestMapping(value = "/readByEmailId", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<CustomerModel> fetchCustomerByEmail(@RequestParam(value = "email", required = true) String email)
	{
		CustomerModel customerModel = customerService.fetchCustomerByEmail(email);

		return new BasicReadResponse<CustomerModel>(customerModel);
	}
	
	/**
	 * Update.
	 *
	 * @param customer
	 *            the customer
	 * @param request
	 *            the request
	 * @return the base response
	 */
	@Override
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@AttachmentsExpected
	@ActionName(ACTION_TYPE_UPDATE)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestPart(IWebUtilsCommonConstants.MULTIPART_DEFAULT_PART) @Valid CustomerModel customer, MultipartHttpServletRequest request)
	{
		validatePricePlanData(customer);

		validateCustomerPoc(customer);

		customerService.update(customer);

		return new BaseResponse();
	}

	/**
	 * Delete customer.
	 *
	 * @param id
	 *            the id
	 * @return the customer delete response
	 */
	@Override
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ActionName(ACTION_TYPE_DELETE)
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		customerService.deleteById(id);

		return new BaseResponse();
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@Override
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		customerService.deleteAll();

		return new BaseResponse();
	}
}