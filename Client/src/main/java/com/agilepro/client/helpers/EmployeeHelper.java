package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_EMPLOYEE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_EMPLOYEE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_EMPLOYEE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_EMPLOYEE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_EMPLOYEE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import com.agilepro.commons.models.admin.EmployeeModel;
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
 * The Class EmployeeHelper is responsible for sending the request to the
 * controller and receiving the response back from the controller.
 */
public class EmployeeHelper
{
	/**
	 * Save employee.
	 *
	 * @param context
	 *            the context
	 * @param model
	 *            Saving employeeModel
	 * @return the long id
	 */
	public long save(ClientContext context, EmployeeModel model)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_EMPLOYEE_SAVE, model, null);
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
	 * Read the employee.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id of employee
	 * @return the employee model
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EmployeeModel read(ClientContext context, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_EMPLOYEE_READ, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BasicReadResponse<EmployeeModel>> result = (RestResult) client.invokeJsonRequest(request, BasicReadResponse.class, EmployeeModel.class);
		return result.getValue().getModel();
	}

	/**
	 * Update the employee.
	 *
	 * @param context
	 *            the context
	 * @param model
	 *            the employee
	 */
	public void update(ClientContext context, EmployeeModel model)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_EMPLOYEE_UPDATE, model, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while updating employee ", saveResult.getStatusCode(), response);
		}
	}

	/**
	 * Delete the employee.
	 *
	 * @param context
	 *            the context
	 * @param id
	 *            the id of employee
	 */
	public void delete(ClientContext context, long id)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_EMPLOYEE_DELETE, null, CommonUtils.toMap(PARAM_ID, "" + id));
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> saveResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting employee ", saveResult.getStatusCode(), response);
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
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_EMPLOYEE_DELETE_ALL, null, null);
		RestClient client = context.getRestClient();

		RestResult<BaseResponse> deleteResult = client.invokeJsonRequest(request, BaseResponse.class);
		BaseResponse response = deleteResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while deleting employee", deleteResult.getStatusCode(), response);
		}
	}
}
