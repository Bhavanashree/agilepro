package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_CUSTOMER_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_CUSTOMER_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_CUSTOMER_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_CUSTOMER_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_CUSTOMER_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import com.agilepro.commons.models.customer.CustomerModel;
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
 * The Class CustomerHelper is responsible for sending the request to the
 * controller and receiving the response back from the controller..
 * 
 * @author Pritam
 */
public class CustomerHelper
{
	/**
	 * Save customer.
	 *
	 * @param context
	 *            the context
	 * @param customer
	 *            the customer
	 * @return the long id of customer
	 */
	public long save(ClientContext context, CustomerModel customer)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CUSTOMER_SAVE, customer, null);
		RestClient client = context.getRestClient();

		RestResult<BasicSaveResponse> saveResult = client.invokeJsonRequest(request, BasicSaveResponse.class);
		BasicSaveResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while saving customer", saveResult.getStatusCode(), response);
		}

		return response.getId();
	}

	/**
	 * Read customer.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id
	 * @return the customer model
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CustomerModel read(ClientContext context, Long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CUSTOMER_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BasicReadResponse<CustomerModel>> readResult = (RestResult) client.invokeJsonRequest(request, BasicReadResponse.class, CustomerModel.class);
		BasicReadResponse<CustomerModel> response = readResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while reading customer", readResult.getStatusCode(), response);
		}

		return (CustomerModel) response.getModel();
	}

	/**
	 * Update customer.
	 *
	 * @param context
	 *            the context
	 * @param customer
	 *            the customer
	 */
	public void update(ClientContext context, CustomerModel customer)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CUSTOMER_UPDATE, customer, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> updateResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = updateResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while updating customer", updateResult.getStatusCode(), response);
		}
	}

	/**
	 * Delete customer.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id
	 */
	public void delete(ClientContext context, Long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CUSTOMER_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting customer", deleteResult.getStatusCode(), response);
		}
	}

	/**
	 * Delete all.
	 *
	 * @param context
	 *            the context
	 */
	public void deleteAll(ClientContext context)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CUSTOMER_DELETE_ALL, null, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting all customer", deleteResult.getStatusCode(), response);
		}
	}
}
