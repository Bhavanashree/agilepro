package com.yukthi.automation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;

import com.yukthi.utils.exceptions.InvalidStateException;

/**
 * Automation Context information. 
 * @author akiran
 */
public class AutomationContext
{
	/**
	 * Map to hold context attributes.
	 */
	private Map<String, Object> nameToAttr = new HashMap<String, Object>();
	
	/**
	 * Web driver which needs to be used for automation.
	 */
	private WebDriver webDriver;
	
	/**
	 * Current web driver type.
	 */
	private Class<? extends WebDriver> webDriverType;
	
	/**
	 * Defines different states and configuration of the target application.
	 */
	private StateConfiguration stateConfiguration;
	
	/**
	 * Application configuration.
	 */
	private IApplicationConfiguration appConfiguration;
	
	/**
	 * Completed test suite list.
	 */
	private Set<String> completedTestSuites = new HashSet<>();
	
	/**
	 * Failed test suite list.
	 */
	private Set<String> failedTestSuites = new HashSet<>();
	
	/**
	 * In progress test suite list.
	 */
	private Set<String> inProgressTestSuites = new HashSet<>();
	
	/**
	 * Constructor.
	 * @param webDriver Web driver to be used for automation
	 * @param stateConfiguration State configuration of the application
	 * @param appConfiguration Application configuration
	 */
	public AutomationContext(WebDriver webDriver, StateConfiguration stateConfiguration, IApplicationConfiguration appConfiguration)
	{
		this.webDriver = webDriver;
		this.webDriverType = webDriver.getClass();
		this.stateConfiguration = stateConfiguration;
		this.appConfiguration = appConfiguration;
	}

	/**
	 * Resets the underlying web driver.
	 */
	public void resetDriver()
	{
		try
		{
			this.webDriver = webDriverType.newInstance();
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while restting driver - {}", webDriverType.getName());
		}
	}
	
	/**
	 * Sets the specified attribute with specified value.
	 * @param name Name of the attribute
	 * @param value Value of the attribute
	 */
	public void setAttribute(String name, Object value)
	{
		nameToAttr.put(name, value);
	}
	
	/**
	 * Fetches the attribute value with specified name.
	 * @param name Name of attribute to fetch
	 * @return Attribute value
	 */
	public Object getAttribute(String name)
	{
		return nameToAttr.get(name);
	}
	
	/**
	 * Removes attribute with specified name.
	 * @param name Name of the attribute to remove.
	 * @return Current attribute value.
	 */
	public Object removeAttribute(String name)
	{
		return nameToAttr.remove(name);
	}
	
	/**
	 * Fetches the attributes on the context as map.
	 * @return Context attributes.
	 */
	public Map<String, Object> getAttributeMap()
	{
		return Collections.unmodifiableMap(nameToAttr);
	}
	
	/**
	 * Gets the web driver which needs to be used for automation.
	 *
	 * @return the web driver which needs to be used for automation
	 */
	public WebDriver getWebDriver()
	{
		return webDriver;
	}
	
	/**
	 * Gets the defines different states and configuration of the target application.
	 *
	 * @return the defines different states and configuration of the target application
	 */
	public StateConfiguration getStateConfiguration()
	{
		return stateConfiguration;
	}
	
	/**
	 * Gets the application configuration.
	 *
	 * @return the application configuration
	 */
	public IApplicationConfiguration getAppConfiguration()
	{
		return appConfiguration;
	}
	
	/**
	 * Marks specified test suite as in progress. 
	 * @param testSuite Test suite to be marked
	 */
	public void testSuiteInProgress(String testSuite)
	{
		this.inProgressTestSuites.add(testSuite);
	}
	
	/**
	 * Marks specified test suite as completed. 
	 * @param testSuite Test suite to be marked
	 */
	public void testSuiteCompleted(String testSuite)
	{
		this.inProgressTestSuites.remove(testSuite);
		this.completedTestSuites.add(testSuite);
	}
	
	/**
	 * Marks specified test suite as failed. 
	 * @param testSuite Test suite to be marked
	 */
	public void testSuiteFailed(String testSuite)
	{
		this.inProgressTestSuites.remove(testSuite);
		this.failedTestSuites.add(testSuite);
	}
	
	/**
	 * Checks if the specified test suite is completed.
	 * @param testSuite Test suite to check
	 * @return true if completed
	 */
	public boolean isTestSuiteCompleted(String testSuite)
	{
		return completedTestSuites.contains(testSuite);
	}
	
	/**
	 * Checks if the specified test suite is failed.
	 * @param testSuite Test suite to check
	 * @return true if failed
	 */
	public boolean isTestSuiteFailed(String testSuite)
	{
		return failedTestSuites.contains(testSuite);
	}
	
	/**
	 * Checks if the specified test suite is completed or failed.
	 * @param testSuite Test suite to check
	 * @return true if completed or failed
	 */
	public boolean isTestSuiteExecuted(String testSuite)
	{
		return completedTestSuites.contains(testSuite) || failedTestSuites.contains(testSuite);
	}
	
	/**
	 * Checks if the specified test suite is in-progress.
	 * @param testSuite Test suite to check
	 * @return true if in-progress
	 */
	public boolean isTestSuiteInProgress(String testSuite)
	{
		return inProgressTestSuites.contains(testSuite);
	}
}
