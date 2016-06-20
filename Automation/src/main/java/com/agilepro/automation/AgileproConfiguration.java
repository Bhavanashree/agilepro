package com.agilepro.automation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import com.yukthi.automation.AbstractApplicationConfiguration;
import com.yukthi.automation.validations.ISqlConfiguration;

/**
 * Configuration for cloud biller.
 * @author akiran
 */
public class AgileproConfiguration extends AbstractApplicationConfiguration implements ISqlConfiguration
{
	/**
	 * Host where app is deployed.
	 */
	private String host;
	
	/**
	 * Admin user name.
	 */
	private String user;
	
	/**
	 * Admin password.
	 */
	private String password;
	
	/**
	 * Application data source.
	 */
	private DataSource dataSource;

	/**
	 * Test suites the execution should limit to.
	 */
	private Set<String> limitedTestSuites = new HashSet<>();

	/**
	 * Gets the host where app is deployed.
	 *
	 * @return the host where app is deployed
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * Sets the host where app is deployed.
	 *
	 * @param host the new host where app is deployed
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * Gets the admin user name.
	 *
	 * @return the admin user name
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * Sets the admin user name.
	 *
	 * @param user the new admin user name
	 */
	public void setUser(String user)
	{
		this.user = user;
	}

	/**
	 * Gets the admin password.
	 *
	 * @return the admin password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Sets the admin password.
	 *
	 * @param password the new admin password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Sets the application data source.
	 *
	 * @param dataSource the new application data source
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	/* (non-Javadoc)
	 * @see com.yukthi.automation.validations.ISqlConfiguration#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException
	{
		return dataSource.getConnection();
	}
	
	/**
	 * Adds the test suite to limit to.
	 * @param name Test suite name to execute.
	 */
	public void addLimitedTestSuite(String name)
	{
		this.limitedTestSuites.add(name);
	}

	/* (non-Javadoc)
	 * @see com.yukthi.automation.IApplicationConfiguration#getLimitedTestSuites()
	 */
	@Override
	public Set<String> getLimitedTestSuites()
	{
		return limitedTestSuites;
	}
}
