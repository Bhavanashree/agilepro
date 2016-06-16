package com.yukthi.automation;

import java.util.Set;

/**
 * Application configuration providing all required configuration for test automation. 
 * @author akiran
 */
public interface IApplicationConfiguration
{
	/**
	 * Gets the state configuration file.
	 * @return application state configuration file.
	 */
	public String getStateConfigurationFile();
	
	/**
	 * Gets the test cases folder where test suite files are present.
	 * @return folder path containing test suite files.
	 */
	public String getTestSuiteFolder();
	
	/**
	 * Fetches the selenium drivers to use for automation.
	 * @return list of selenium drivers to use.
	 */
	public String[] getSeleniumDrivers();
	
	/**
	 * Base package names where validations and steps needs to be scanned.
	 * @return Base package names
	 */
	public Set<String> getBasePackages();
	
	/**
	 * If specified, test suite execution will be limited only to this test cases.
	 * @return Test suites to limit to.
	 */
	public Set<String> getLimitedTestSuites();
}
