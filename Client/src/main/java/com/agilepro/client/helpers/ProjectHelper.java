package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_PROJECT_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_PROJECT_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_PROJECT_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_PROJECT_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_PROJECT_SAVE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import com.agilepro.commons.models.customer.ProjectModel;
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
 * The Class ProjectHelper is responsible for sending the request to the
 * controller and receiving the response back from the controller.
 * 
 * @author Pritam
 */
public class ProjectHelper
{
	/**
	 * Save.
	 *
	 * @param context
	 *            the context
	 * @param projectModel
	 *            the projects model
	 * @return the long
	 */
	public long save(ClientContext context, ProjectModel projectModel)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PROJECT_SAVE, projectModel, null);
		RestClient client = context.getRestClient();

		RestResult<BasicSaveResponse> saveResult = client.invokeJsonRequest(request, BasicSaveResponse.class);
		BasicSaveResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while saving model", saveResult.getStatusCode(), response);
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
	 * @return the projects model
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProjectModel read(ClientContext context, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PROJECT_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BasicReadResponse<ProjectModel>> result = (RestResult) client.invokeJsonRequest(request, BasicReadResponse.class, ProjectModel.class);
		return result.getValue().getModel();
	}

	/**
	 * Update.
	 *
	 * @param context
	 *            the context
	 * @param projectModel
	 *            the projects model
	 */
	public void update(ClientContext context, ProjectModel projectModel)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PROJECT_UPDATE, projectModel, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while updating projects ", saveResult.getStatusCode(), response);
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
	public void delete(ClientContext context, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PROJECT_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting projects ", saveResult.getStatusCode(), response);
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
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_PROJECT_DELETE_ALL, null, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting all projects", deleteResult.getStatusCode(), response);
		}
	}
}
