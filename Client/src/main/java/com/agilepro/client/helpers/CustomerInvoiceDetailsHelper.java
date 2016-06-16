package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_CUSTOMERINVOICEDETAILS_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_CUSTOMERINVOICEDETAILS_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_CUSTOMERINVOICEDETAILS;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;
import com.agilepro.commons.models.customer.priceplan.InvoiceDetails;
import com.yukthi.utils.CommonUtils;
import com.yukthi.utils.rest.RestClient;
import com.yukthi.utils.rest.RestRequest;
import com.yukthi.utils.rest.RestResult;
import com.yukthi.webutils.client.ActionRequestBuilder;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.RestException;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;

/**
 * The Class CustomerInvoiceDetailsHelper.
 */
public class CustomerInvoiceDetailsHelper
{
	/**
	 * Read the customer invoice details.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id
	 * @return the invoice details
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public InvoiceDetails read(ClientContext context, Long id)
	{

		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CUSTOMERINVOICEDETAILS_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BasicReadResponse<InvoiceDetails>> result = (RestResult) client.invokeJsonRequest(request, BasicReadResponse.class, InvoiceDetails.class);
		return result.getValue().getModel();
	}

	/**
	 * Delete the invoice details.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id of customer invoice detail
	 */
	public void delete(ClientContext context, Long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CUSTOMERINVOICEDETAILS_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting customer invoice detail", deleteResult.getStatusCode(), response);
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
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PREFIX_CUSTOMERINVOICEDETAILS + "." + ACTION_TYPE_DELETE_ALL, null, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting all invoice details", deleteResult.getStatusCode(), response);
		}
	}
}
