package com.agilepro.client.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.agilepro.commons.IAgileproActions.ACTION_PRICEPLAN_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_PRICEPLAN_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_PRICEPLAN_UPDATE;

import static com.agilepro.commons.IAgileproActions.ACTION_PRICEPLAN_DELETE;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_PRICEPLAN;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;

import static com.agilepro.commons.IAgileproActions.ACTION_PRICEPLAN_INVOKE;

import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanModel;
import com.yukthi.utils.CommonUtils;
import com.yukthi.utils.rest.RestClient;
import com.yukthi.utils.rest.RestRequest;
import com.yukthi.utils.rest.RestResult;
import com.yukthi.webutils.client.ActionRequestBuilder;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.RestException;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Class PricePlanHelper is responsible for sending the request to the
 * controller and receiving the response back from the controller.
 */
public class CustomerPricePlanHelper
{

	/**
	 *  The logger. 
	 */
	private static Logger logger = LogManager.getLogger(CustomerPricePlanHelper.class);

	/**
	 * Save the customer price plan.
	 *
	 * @param context
	 *            the context
	 * @param pricePlan
	 *            the price plan
	 * @return the long id
	 */
	public long save(ClientContext context, CustomerPricePlanModel pricePlan)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PRICEPLAN_SAVE, pricePlan, null);
		RestClient client = context.getRestClient();

		RestResult<BasicSaveResponse> saveResult = client.invokeJsonRequest(request, BasicSaveResponse.class);
		BasicSaveResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while saving price plan", saveResult.getStatusCode(), response);
		}

		return response.getId();
	}

	/**
	 * Read the customer price plan.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id of price plan
	 * @return the customer price plan model
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CustomerPricePlanModel read(ClientContext context, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PRICEPLAN_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BasicReadResponse<CustomerPricePlanModel>> result = (RestResult) client.invokeJsonRequest(request, BasicReadResponse.class, CustomerPricePlanModel.class);
		return result.getValue().getModel();
	}

	/**
	 * Update the customer price plan.
	 *
	 * @param context
	 *            the context
	 * @param pricePlan
	 *            the price plan
	 */
	public void update(ClientContext context, CustomerPricePlanModel pricePlan)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PRICEPLAN_UPDATE, pricePlan, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while updating price plan", saveResult.getStatusCode(), response);
		}
	}

	/**
	 * Delete the customer price plan.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id of customer price plan
	 */
	public void delete(ClientContext context, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PRICEPLAN_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting price plan", saveResult.getStatusCode(), response);
		}
	}

	/**
	 * Delete all customer price plan.
	 *
	 * @param context
	 *            the context
	 */
	public void deleteAll(ClientContext context)
	{
		logger.trace("Trying to delete all extension fields");

		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PREFIX_PRICEPLAN + "." + ACTION_TYPE_DELETE_ALL, null, null);

		RestClient client = context.getRestClient();

		RestResult<BaseResponse> extResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = extResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			logger.error("An error occurred while deleting price plans. Response - {} ", response);
			throw new RestException("An error occurred while deletingprice plans", extResult.getStatusCode(), response);
		}

		logger.debug("Successfully deleted all price plans.");
	}

	/**
	 * Calculate due amount of customer.
	 *
	 * @param context
	 *            the context
	 */
	public void calculateAmount(ClientContext context)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PRICEPLAN_INVOKE, null, null);

		RestClient client = context.getRestClient();

		RestResult<BaseResponse> extResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = extResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			logger.error("An error occurred while calculating customer due amount. Response - {} ", response);
			throw new RestException("An error occurred while calculating amount", extResult.getStatusCode(), response);
		}

		logger.debug("Successfully calculated due amount.");
	}
}
