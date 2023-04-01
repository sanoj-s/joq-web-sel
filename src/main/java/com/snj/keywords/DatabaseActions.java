package com.snj.keywords;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.snj.exception.AutomationException;
import com.snj.utils.DatabaseConnectionUtility;

public class DatabaseActions extends DatabaseConnectionUtility {
	ResultSet res;

	public DatabaseActions() throws AutomationException {
		stmt = new DatabaseConnectionUtility().getConnectionStatement();
	}

	/**
	 * Method to retrieve data from a table
	 * 
	 * @author sanoj.swaminathan
	 * @since 02-03-2023
	 * @return ResultSet
	 * @throws AutomationException
	 */
	public ResultSet getTableData(String query) throws AutomationException {
		try {
			if (query == null || query.equals("")) {
				throw new AutomationException("");
			}
		} catch (Exception e) {
			e = new Exception("");
			throw new AutomationException(e);
		}
		try {
			res = stmt.executeQuery(query);
		} catch (Exception e) {
			throw new AutomationException(e);
		}
		return res;
	}

	/**
	 * Method to retrieve integer data from a table based on the column number
	 * 
	 * @author sanoj.swaminathan
	 * @since 02-03-2023
	 * @param resultSet
	 * @param colIndex
	 * @return list
	 */
	public ArrayList<Integer> getIntegerValuesbyColNum(ResultSet resultSet, int colIndex)
			throws SQLException, AutomationException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		while (resultSet.next()) {
			list.add(resultSet.getInt(colIndex));
		}
		return list;
	}

	/**
	 * Method to retrieve String data from a table based on the column number
	 * 
	 * @author sanoj.swaminathan
	 * @since 02-03-2023
	 * @param query
	 * @param colIndex
	 * @return list
	 */
	public ArrayList<String> getStringValuesbyColNum(ResultSet resultSet, int colIndex)
			throws SQLException, AutomationException {
		ArrayList<String> list = new ArrayList<String>();
		while (resultSet.next()) {
			list.add(resultSet.getString(colIndex));
		}
		return list;
	}

	/**
	 * Method to execute query
	 * 
	 * @author sanoj.swaminathan
	 * @since 02-03-2023
	 * @param stm
	 * @param query
	 * @throws AutomationException
	 */
	public void executeQuery(Statement stm, String query) throws AutomationException {
		try {
			if (query == null || query.equals("")) {
				throw new AutomationException("");
			}
		} catch (Exception e) {
			e = new Exception("");
			throw new AutomationException(e);
		}
		try {
			stm.execute(query);
		} catch (Exception e) {
		}
	}
}
