package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_TAGS_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_EMPLOYEE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TAGS_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_EMPLOYEE_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_TAGS_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TAGS_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_TAGS_DELETE;

import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.customer.TagsModel;
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

public class TagsHelper 
{
	public long save(ClientContext clientContext, TagsModel tagsModel)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(clientContext, ACTION_TAGS_SAVE, tagsModel, null);
		RestClient restClient = clientContext.getRestClient();
		
		RestResult<BasicSaveResponse> restResult = restClient.invokeJsonRequest(request, BasicSaveResponse.class);
		BasicSaveResponse basicSaveResponse = restResult.getValue();
	
		if(basicSaveResponse == null || basicSaveResponse.getCode() != 0)
		{
			throw new RestException("An error occurred while saving model", restResult.getStatusCode(), basicSaveResponse);
		}
		
		return basicSaveResponse.getId();
	}
	
	public TagsModel read(ClientContext clientContext, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(clientContext, ACTION_TAGS_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient restClient = clientContext.getRestClient();
		
		RestResult<BasicReadResponse<TagsModel>> restResult = (RestResult) restClient.invokeJsonRequest(request, BasicReadResponse.class, TagsModel.class);
		
		return restResult.getValue().getModel();
	}
	
	public void update(ClientContext context, TagsModel tagsModel)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_TAGS_UPDATE, tagsModel, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while updating tags ", saveResult.getStatusCode(), response);
		}
	}
	
	public void delete(ClientContext context, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_TAGS_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting tags ", saveResult.getStatusCode(), response);
		}
	}
	
	public void deleteAll(ClientContext context)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_TAGS_DELETE_ALL, null, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting all tags", deleteResult.getStatusCode(), response);
		}
	}
}
