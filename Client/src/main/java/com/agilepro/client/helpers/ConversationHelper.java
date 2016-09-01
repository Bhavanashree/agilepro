package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_CONVERSATION_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_CONVERSATION_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_CONVERSATION_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_CONVERSATION_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_CONVERSATION_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import com.agilepro.commons.models.project.ConversationMessageModel;
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
 * The Class ConversationHelper.
 */
public class ConversationHelper
{

	/**
	 * Save.
	 *
	 * @param context
	 *            the context
	 * @param conversation
	 *            the conversation
	 * @return the long
	 */
	public long save(ClientContext context, ConversationMessageModel conversation)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CONVERSATION_SAVE, conversation, null);
		RestClient client = context.getRestClient();

		RestResult<BasicSaveResponse> saveResult = client.invokeJsonRequest(request, BasicSaveResponse.class);
		BasicSaveResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while saving conversation", saveResult.getStatusCode(), response);
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
	 * @return the conversation model
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ConversationMessageModel read(ClientContext context, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CONVERSATION_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BasicReadResponse<ConversationMessageModel>> readResult = (RestResult) client.invokeJsonRequest(request, BasicReadResponse.class, ConversationMessageModel.class);
		BasicReadResponse<ConversationMessageModel> response = readResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while reading conversation", readResult.getStatusCode(), response);
		}

		return (ConversationMessageModel) response.getModel();
	}

	/**
	 * Update.
	 *
	 * @param context
	 *            the context
	 * @param user
	 *            the user
	 */
	public void update(ClientContext context, ConversationMessageModel user)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CONVERSATION_UPDATE, user, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> updateResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = updateResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while updating  conversation", updateResult.getStatusCode(), response);
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
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CONVERSATION_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
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
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_CONVERSATION_DELETE_ALL, null, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting all conversation", deleteResult.getStatusCode(), response);
		}
	}
}
