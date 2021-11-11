package com.snj.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.snj.base.AutomationEngine;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

public class CSVDataHandler extends AutomationEngine {
	public CSVDataHandler() throws AutomationException {
		super();
	}

	static String csvFilePath = "";
	private static final String DELIMITER = ",";

	/**
	 * Returns an ArrayList containing all column data of specified column index
	 * from CSV
	 * 
	 * @author sanojs
	 * @since 08-05-2021
	 * @param columnInex
	 * @return datas
	 * @throws AutomationException
	 * 
	 */

	public ArrayList<String> getCSVData(int columnIndex) throws AutomationException {
		ArrayList<String> datas = new ArrayList<String>();

		csvFilePath = new PropertyDataHandler().getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG,
				AutomationConstants.CSV_FILE_PATH);
		if (csvFilePath == null || csvFilePath.trim().equals(""))
			throw (new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_CSV_FILE_PATH));
		File file = new File(csvFilePath);
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			while ((line = br.readLine()) != null) {
				String[] country = line.split(DELIMITER);
				datas.add(country[columnIndex - 1].trim());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw (new AutomationException(getExceptionMessage(), e));
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	/**
	 * Returns an ArrayList containing all column data of specified column name from
	 * CSV
	 * 
	 * @author sanojs
	 * @since 08-05-2021
	 * @param columnName
	 * @return datas
	 * @throws AutomationException
	 * 
	 */
	public ArrayList<String> getCSVData(String columnName) throws AutomationException {
		ArrayList<String> datas = new ArrayList<String>();

		csvFilePath = new PropertyDataHandler().getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG,
				AutomationConstants.CSV_FILE_PATH);

		if (csvFilePath == null || csvFilePath.trim().equals(""))
			throw (new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_CSV_FILE_PATH));
		File file = new File(csvFilePath);
		BufferedReader br = null;
		String line = "";
		int columnInex = 0;
		boolean flag = false;
		try {
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			if ((line = br.readLine()) != null) {
				String[] country = line.split(DELIMITER);
				for (int i = 0; i < country.length; i++) {
					if (country[i].equals(columnName)) {
						columnInex = i;
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				while ((line = br.readLine()) != null) {
					String[] country = line.split(DELIMITER, -1);
					datas.add(country[columnInex].trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (new AutomationException(getExceptionMessage(), e));
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	public static void main(String[] args) {
		Method[] m = CSVDataHandler.class.getDeclaredMethods();
		for (int i = 0; i < m.length; i++)
			System.out.println(i + 1 + " " + m[i].getName());
	}

}
