package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_PAYMENT_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_PAYMENT_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_PAYMENT_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_PAYMENT_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_PAYMENT_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import com.agilepro.commons.models.customer.CustomerPaymentModel;
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
 * The Class PaymentHelper.
 */
public class PaymentHelper
{
	/**
	 * Save Customer payment made.
	 *
	 *@param context
	 *			context
	 * @param customerPayment
	 * 				 customerpayment
	 * @return the customerPayment save response
	 */
	public long save(ClientContext context, CustomerPaymentModel customerPayment)
	{

		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PAYMENT_SAVE, customerPayment, null);
		RestClient client = context.getRestClient();

		RestResult<BasicSaveResponse> saveResult = client.invokeJsonRequest(request, BasicSaveResponse.class);
		BasicSaveResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while saving payment", saveResult.getStatusCode(), response);
		}

		return response.getId();
	}

	/**
	 * Read Customer payment made.
	 * 
	 * @param context
	 * 			context
	 * @param id
	 * 			id
	 * @return the customerPayment save response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CustomerPaymentModel read(ClientContext context, Long id)
	{

		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PAYMENT_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BasicReadResponse<CustomerPaymentModel>> result = (RestResult) client.invokeJsonRequest(request, BasicReadResponse.class, CustomerPaymentModel.class);
		return result.getValue().getModel();
	}

	/**
	 * Update Customer payment made.
	 * @param context
	 *            context
	 * @param customerPayment
	 *               customerPayment
	 */
	public void update(ClientContext context, CustomerPaymentModel customerPayment)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PAYMENT_UPDATE, customerPayment, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> updateResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = updateResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while updating payment", updateResult.getStatusCode(), response);
		}
	}

	/**
	 * Delete Customer payment made.
	 * @param context
	 *            context
	 * @param id 
	 * 			id
	 */
	public void delete(ClientContext context, Long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PAYMENT_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred  while deleting payment", deleteResult.getStatusCode(), response);
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
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PAYMENT_DELETE_ALL, null, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting payment", deleteResult.getStatusCode(), response);
		}
	}
}
