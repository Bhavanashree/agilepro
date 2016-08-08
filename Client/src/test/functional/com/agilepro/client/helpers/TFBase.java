package com.agilepro.client.helpers;

import java.util.Properties;

import org.testng.annotations.BeforeClass;

import com.agilepro.commons.IAgileproCommonConstants;
import com.yukthi.utils.CommonUtils;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.ClientControllerFactory;

/**
 * Base class for test cases.
 * 
 * @author akiran
 */
public abstract class TFBase
{
	
	/**
	 * baseUrl.
	 */
	protected String baseUrl;
	/**
	 * clientContext.
	 */
	protected ClientContext clientContext;

	/**
	 * The client controller factory.
	 */
	protected ClientControllerFactory clientControllerFactory;

	/**
	 * setting baseUrl and checking username and Password.
	 * 
	 * @throws Exception
	 * 
	 */
	@BeforeClass
	public final void init() throws Exception
	{
		Properties prop = new Properties();
		prop.load(TFBase.class.getResourceAsStream("/test-env.properties"));

		String baseUrl = prop.getProperty("test.base.url");

		clientContext = new ClientContext(baseUrl);
		this.baseUrl = baseUrl;

		clientContext.authenticate(prop.getProperty("test.user"), prop.getProperty("test.password"));

		clientControllerFactory = new ClientControllerFactory(clientContext);
	}

	/**
	 * authenicating customer.
	 * 
	 * @param userName
	 *            userName
	 * @param password
	 *            password
	 * @param customerId
	 *            customerId
	 * @return clientContext
	 */
	public ClientContext newClientContext(String userName, String password, long customerId)
	{
		ClientContext clientContext = new ClientContext(baseUrl);
		clientContext.authenticate(userName, password, CommonUtils.toMap(IAgileproCommonConstants.REQ_PARAM_CUSTOMER_ID, "" + customerId));

		return clientContext;
	}
}
