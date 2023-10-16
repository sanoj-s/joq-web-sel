package com.joq.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Logger;

import com.joq.base.AutomationEngine;
import com.joq.exception.AutomationException;
import com.joq.keywords.DataHandler;

public class DatabaseConnectionUtility extends AutomationEngine {

	private static Logger log;
	public String dbUrl, dbUsername, dbPassword;
	public Connection con = null;
	public Statement stmt = null;

	public DatabaseConnectionUtility() throws AutomationException {

		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DatabaseConnectionUtility.class.getName());
	}

	/**
	 * Create the statement
	 * 
	 * @author sanojs
	 * @since 07/27/2021
	 * @return Statement
	 * @throws AutomationException
	 */
	public Statement getConnectionStatement() throws AutomationException {
		try {
			DataHandler propHandler = new DataHandler();
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
	 * Create close the Database connection
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
