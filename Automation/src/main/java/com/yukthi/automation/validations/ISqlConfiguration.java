package com.yukthi.automation.validations;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Expected to be implemented by application configurations which wants to use SQL
 * in automation.
 * @author akiran
 */
public interface ISqlConfiguration
{
	/**
	 * Gets SQL connection for application database.
	 * @return Sql connection
	 */
	public Connection getConnection() throws SQLException;
}
