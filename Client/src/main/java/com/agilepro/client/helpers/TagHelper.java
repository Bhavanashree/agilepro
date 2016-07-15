package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_TAG_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TAG_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TAG_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TAG_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_TAG_DELETE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;
import com.agilepro.commons.models.customer.TagModel;
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
 * The Class TagHelper.
 * 
 * @author Pritam
 */
public class TagHelper
{

	/**
	 * Save.
	 *
	 * @param clientContext
	 *            the client context
	 * @param tagsModel
	 *            the tags model
	 * @return the long
	 */
	public long save(ClientContext clientContext, TagModel tagsModel)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(clientContext, ACTION_TAG_SAVE, tagsModel, null);
		RestClient restClient = clientContext.getRestClient();

		RestResult<BasicSaveResponse> restResult = restClient.invokeJsonRequest(request, BasicSaveResponse.class);
		BasicSaveResponse basicSaveResponse = restResult.getValue();

		if(basicSaveResponse == null || basicSaveResponse.getCode() != 0)
		{
			throw new RestException("An error occurred while saving model", restResult.getStatusCode(), basicSaveResponse);
		}

		return basicSaveResponse.getId();
	}

	/**
	 * Read.
	 *
	 * @param clientContext
	 *            the client context
	 * @param id
	 *            the id
	 * @return the tag model
	 */
	public TagModel read(ClientContext clientContext, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(clientContext, ACTION_TAG_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient restClient = clientContext.getRestClient();

		@SuppressWarnings("unchecked")
		RestResult<BasicReadResponse<TagModel>> restResult = (RestResult) restClient.invokeJsonRequest(request, BasicReadResponse.class, TagModel.class);

		return restResult.getValue().getModel();
	}

	/**
	 * Update.
	 *
	 * @param context
	 *            the context
	 * @param tagsModel
	 *            the tags model
	 */
	public void update(ClientContext context, TagModel tagsModel)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_TAG_UPDATE, tagsModel, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while updating tags ", saveResult.getStatusCode(), response);
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
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_TAG_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting tags ", saveResult.getStatusCode(), response);
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
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_TAG_DELETE_ALL, null, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting all tags", deleteResult.getStatusCode(), response);
		}
	}
}
