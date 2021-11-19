package com.snj.dbhandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.snj.exception.AutomationException;

public class DatabaseActions extends DatabaseConnectionUtility {

	public DatabaseActions() throws AutomationException {
		super();
	}

	ResultSet res;

	/**
	 * Get Database connection statement
	 * 
	 * @author sanojs
	 * @since 09/07/2021
	 * @return
	 */
	public Statement getConnection() {
		Statement stmt = null;
		try {
			stmt = new DatabaseConnectionUtility().getConnectionStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stmt;
	}

	/**
	 * Retrieve data from a table
	 * 
	 * @author sanojs
	 * @since 09/07/2021
	 * @param query
	 * @return ResultSet
	 * @throws AutomationException
	 */
	public ResultSet getTableData(Statement stm, String query) throws AutomationException {

		ResultSet res;
		try {
			if (query == null || query.equals("")) {
				throw new AutomationException("");
			}
		} catch (Exception e) {
			e = new Exception("");
			throw new AutomationException(e);
		}
		try {
			res = stm.executeQuery(query);
		} catch (Exception e) {
			throw new AutomationException(e);
		}
		return res;
	}

	/**
	 * Retrieve integer data from a table based on the column number
	 * 
	 * @author sanojs
	 * @since 09/07/2021
	 * @param selectquery
	 * @param colNum
	 * @return list
	 */
	public ArrayList<Integer> getIntegerValuesbyColNum(Statement stm, String selectquery, int colNum)
			throws SQLException, AutomationException {
		ResultSet res = getTableData(stm, selectquery);
		ArrayList<Integer> list = new ArrayList<Integer>();
		while (res.next()) {
			list.add(res.getInt(colNum));
		}
		return list;
	}

	/**
	 * Retrieve String data from a table based on the column number
	 * 
	 * @author sanojs
	 * @since 09/07/2021
	 * @param selectquery
	 * @param index
	 * @return list
	 */
	public ArrayList<String> getStringValuesbyColNum(Statement stm, String selectquery, int colNum)
			throws SQLException, AutomationException {
		ResultSet res = getTableData(stm, selectquery);
		ArrayList<String> list = new ArrayList<String>();
		while (res.next()) {
			list.add(res.getString(colNum));
		}
		return list;
	}
}
