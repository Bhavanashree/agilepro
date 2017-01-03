package com.yukthi.automation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * The Class AbstractApplicationConfiguration.
 */
public abstract class AbstractApplicationConfiguration implements IApplicationConfiguration
{
	/**
	 * State configuration file which provides configuration about different states
	 * of the application.
	 */
	private String stateConfigurationFile;
	
	/**
	 * Folder containing test suite xmls.
	 */
	private String testSuiteFolder;
	
	/**
	 * Selenium drivers to use for automation.
	 */
	private String seleniumDrivers[] = {FirefoxDriver.class.getName()};
	
	/**
	 * Base packages to be scanned for steps and validators.
	 */
	private Set<String> basePackages = new HashSet<>();
	
	/**
	 * Application data sources.
	 */
	private Map<String, DataSource> dataSourceMap = new HashMap<>();

	/**
	 * Gets the state configuration file which provides configuration about different states of the application.
	 *
	 * @return the state configuration file which provides configuration about different states of the application
	 */
	@Override
	public String getStateConfigurationFile()
	{
		return stateConfigurationFile;
	}

	/**
	 * Sets the state configuration file which provides configuration about different states of the application.
	 *
	 * @param stateConfigurationFile the new state configuration file which provides configuration about different states of the application
	 */
	public void setStateConfigurationFile(String stateConfigurationFile)
	{
		this.stateConfigurationFile = stateConfigurationFile;
	}

	/**
	 * Gets the folder containing test suite xmls.
	 *
	 * @return the folder containing test suite xmls
	 */
	@Override
	public String getTestSuiteFolder()
	{
		return testSuiteFolder;
	}

	/**
	 * Sets the folder containing test suite xmls.
	 *
	 * @param testSuiteFolder the new folder containing test suite xmls
	 */
	public void setTestSuiteFolder(String testSuiteFolder)
	{
		this.testSuiteFolder = testSuiteFolder;
	}

	/* (non-Javadoc)
	 * @see com.yukthi.ui.automation.IApplicationConfiguration#getSeleniumDrivers()
	 */
	@Override
	public String[] getSeleniumDrivers()
	{
		return seleniumDrivers;
	}

	/**
	 * Sets the selenium drivers to use for automation.
	 *
	 * @param seleniumDrivers the new selenium drivers to use for automation
	 */
	public void setSeleniumDrivers(String[] seleniumDrivers)
	{
		this.seleniumDrivers = seleniumDrivers;
	}
	
	/**
	 * Adds specified base package for scanning.
	 * @param basePackage Base package to be added
	 */
	public void addBasePackage(String basePackage)
	{
		this.basePackages.add(basePackage);
	}
	
	/* (non-Javadoc)
	 * @see com.yukthi.automation.IApplicationConfiguration#getBasePackages()
	 */
	@Override
	public Set<String> getBasePackages()
	{
		return basePackages;
	}

	/**
	 * Adds specified data source with specified name.
	 * @param name Name of the data source.
	 * @param dataSource Data source to add.
	 */
	public void addDataSource(String name, DataSource dataSource)
	{
		this.dataSourceMap.put(name, dataSource);
	}
	
	/* (non-Javadoc)
	 * @see com.yukthi.automation.IApplicationConfiguration#getDataSource(java.lang.String)
	 */
	@Override
	public DataSource getDataSource(String name)
	{
		return dataSourceMap.get(name);
	}
}
