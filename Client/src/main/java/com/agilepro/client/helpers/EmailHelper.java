package com.agilepro.client.helpers;

import static com.agilepro.commons.IAgileproActions.ACTION_EMAIL_SEND;
import com.agilepro.commons.models.admin.EmailModel;
import com.yukthi.utils.rest.RestClient;
import com.yukthi.utils.rest.RestRequest;
import com.yukthi.utils.rest.RestResult;
import com.yukthi.webutils.client.ActionRequestBuilder;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.RestException;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Class EmailHelper.
 */
public class EmailHelper
{

	/**
	 * Send.
	 *
	 * @param context the context
	 * @param emailModel the email model
	 * @return the boolean
	 */
	public Boolean send(ClientContext context, EmailModel emailModel)
	{
		RestRequest<?> request = ActionRequestBuilder.buildRequest(context, ACTION_EMAIL_SEND, emailModel, null);
		RestClient client = context.getRestClient();

		RestResult<BasicSaveResponse> saveResult = client.invokeJsonRequest(request, BasicSaveResponse.class);
		BasicSaveResponse response = saveResult.getValue();

		if(response == null || response.getCode() != 0)
		{
			throw new RestException("An error occurred while sending mail", saveResult.getStatusCode(), response);
		}

		return true;
	}
}
