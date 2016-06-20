package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_ADMINUSER_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_ADMINUSER_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_ADMINUSER_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_ADMINUSER_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_ADMINUSER_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import com.agilepro.commons.models.admin.AdminUserModel;
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
 * The Class AdminUserHelper.
 */
public class AdminUserHelper
{

	/**
	 * Save.
	 *
	 * @param context
	 *            the context
	 * @param user
	 *            the user
	 * @return the long
	 */
	public long save(ClientContext context, AdminUserModel user)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_ADMINUSER_SAVE, user, null);
		RestClient client = context.getRestClient();

		RestResult<BasicSaveResponse> saveResult = client.invokeJsonRequest(request, BasicSaveResponse.class);
		BasicSaveResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while saving adminuser", saveResult.getStatusCode(), response);
		}

		return response.getId();
	}

	/**
	 * Read.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id
	 * @return the admin user model
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AdminUserModel read(ClientContext context, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_ADMINUSER_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BasicReadResponse<AdminUserModel>> readResult = (RestResult) client.invokeJsonRequest(request, BasicReadResponse.class, AdminUserModel.class);
		BasicReadResponse<AdminUserModel> response = readResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while reading useradmin", readResult.getStatusCode(), response);
		}

		return (AdminUserModel) response.getModel();
	}

	/**
	 * Update.
	 *
	 * @param context
	 *            the context
	 * @param user
	 *            the user
	 */
	public void update(ClientContext context, AdminUserModel user)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_ADMINUSER_UPDATE, user, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> updateResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = updateResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while updating useradmin", updateResult.getStatusCode(), response);
		}
	}

	/**
	 * Delete.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id
	 */
	public void delete(ClientContext context, Long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_ADMINUSER_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting ", deleteResult.getStatusCode(), response);
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
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_ADMINUSER_DELETE_ALL, null, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting all adminuser", deleteResult.getStatusCode(), response);
		}
	}
}
