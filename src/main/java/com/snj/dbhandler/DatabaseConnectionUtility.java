package com.snj.dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Logger;

import com.snj.base.AutomationEngine;
import com.snj.data.PropertyDataHandler;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

public class DatabaseConnectionUtility extends AutomationEngine {

	private static Logger log;
	public String className, dbUrl, dbUsername, dbPassword;
	public Connection con = null;
	public Statement stmt = null;

	public DatabaseConnectionUtility() throws AutomationException {

		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DatabaseConnectionUtility.class.getName());
	}

	/**
	 * Method to create the statement
	 * 
	 * @author sanojs
	 * @since 07/27/2021
	 * @return Statement
	 * @throws AutomationException
	 */
	public Statement getConnectionStatement() throws AutomationException {
		try {
			PropertyDataHandler propHandler = new PropertyDataHandler();
			dbUrl = propHandler.getProperty(AutomationConstants.DB_CONFIG, "dbUrl");
			dbUsername = propHandler.getProperty(AutomationConstants.DB_CONFIG, "dbUsername");
			dbPassword = propHandler.getProperty(AutomationConstants.DB_CONFIG, "dbPassword");
			log.info("Connecting to the database");
			con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			log.info("Connected database is: " + con.getCatalog());
			log.info("Creating statement object");
			stmt = con.createStatement();
			log.info("Created the statement object");
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return stmt;
	}

	/**
	 * Method to create close the Database connection
	 * 
	 * @author sanojs
	 * @since 07/27/2021
	 * @return Statement
	 * @throws AutomationException
	 */
	public void closeDatabaseConnection() throws AutomationException {
		try {
			log.info("Closing database connection");
			con.close();
			log.info("Connection to " + con.getCatalog() + " is closed");
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}
}
