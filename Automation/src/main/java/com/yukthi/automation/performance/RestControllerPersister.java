package com.yukthi.automation.performance;

import java.lang.reflect.Method;
import java.util.Properties;

import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.client.ClientContext;
import com.yukthi.webutils.client.ClientControllerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class RestControllerPersister.
 */
public class RestControllerPersister implements IBeanPersister
{

	/**
	 * clientContext.
	 */
	protected ClientContext clientContext;

	/**
	 * The client controller factory.
	 */
	protected ClientControllerFactory clientControllerFactory;

	/**
	 * baseUrl.
	 */
	protected String baseUrl;

	/**
	 * The controller type.
	 */
	private String controllerType;

	/**
	 * Instantiates a new rest controller persister.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public RestControllerPersister() throws Exception
	{
		this.initRestController();
	}

	/**
	 * Gets the controller type.
	 *
	 * @return the controller type
	 */
	public String getControllerType()
	{
		return controllerType;
	}

	/**
	 * Sets the controller type.
	 *
	 * @param controllerType
	 *            the new controller type
	 */
	public void setControllerType(String controllerType)
	{
		this.controllerType = controllerType;
	}

	/**
	 * Inits the rest controller.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void initRestController() throws Exception
	{
		Properties prop = new Properties();
		prop.load(RestControllerPersister.class.getResourceAsStream("/rest-env.properties"));
		String baseUrl = prop.getProperty("test.base.url");

		clientContext = new ClientContext(baseUrl);
		this.baseUrl = baseUrl;

		clientContext.authenticate(prop.getProperty("test.user"), prop.getProperty("test.password"));

		clientControllerFactory = new ClientControllerFactory(clientContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.automation.performance.IBeanPersister#persist(java.lang.
	 * Object)
	 */
	@Override
	public void persist(Object bean)
	{
		Class<?> cls = null;
		Object controller = null;

		try
		{
			cls = Class.forName(controllerType);
			controller = clientControllerFactory.getController(cls);
		} catch(

		Exception ex)
		{
			throw new InvalidStateException(ex, "error occured while creating controller", cls);
		}

		try
		{
			Method saveMethod = cls.getMethod("save", bean.getClass());
			saveMethod.invoke(controller, bean);
			System.out.println(saveMethod);
		} catch(Exception ex)
		{
			try
			{
				Method saveMethod = cls.getMethod("save", bean.getClass(), Object.class);
				saveMethod.invoke(controller, bean, null);
			} catch(Exception ex1)
			{
				System.out.println(ex1);
				throw new InvalidStateException(ex1, "error occured while controller save method ", controllerType);
			}
		}
	}
}
