package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_DESIGNATION_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_DESIGNATION_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_DESIGNATION_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_DESIGNATION_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_DESIGNATION_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import com.agilepro.commons.models.admin.DesignationModel;
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
 * The Class DesignationHelper.
 */
public class DesignationHelper
{
	/**
	 * Save Designation created.
	 *
	 *@param context
	 *              context
	 * @param designation
	 * 				designations
	 * @return the designation save response
	 */
	public long save(ClientContext context, DesignationModel designation)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_DESIGNATION_SAVE, designation, null);
		RestClient client = context.getRestClient();

		RestResult<BasicSaveResponse> saveResult = client.invokeJsonRequest(request, BasicSaveResponse.class);
		BasicSaveResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while saving designation", saveResult.getStatusCode(), response);
		}

		return response.getId();
	}

	/**
	 * Read the customer designation.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id of designation
	 * @return the designation model
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DesignationModel read(ClientContext context, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_DESIGNATION_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BasicReadResponse<DesignationModel>> result = (RestResult) client.invokeJsonRequest(request, BasicReadResponse.class, DesignationModel.class);
		return result.getValue().getModel();
	}

	/**
	 * Update the designation.
	 *
	 * @param context
	 *            the context
	 * @param designation
	 *            the designation
	 */
	public void update(ClientContext context, DesignationModel designation)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_DESIGNATION_UPDATE, designation, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while updating designation ", saveResult.getStatusCode(), response);
		}
	}

	/**
	 * Delete the designation.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id of customer designation
	 */
	public void delete(ClientContext context, long id)
	{

		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_DESIGNATION_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();
		
		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting designation ", saveResult.getStatusCode(), response);
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
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_DESIGNATION_DELETE_ALL, null, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting designation", deleteResult.getStatusCode(), response);
		}
	}
}
