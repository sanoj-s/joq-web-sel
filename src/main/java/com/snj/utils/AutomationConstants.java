package com.snj.utils;

public class AutomationConstants {
	// =========> Generic
	public static final String OBJECT_NOT_FOUND = "Unable to locate ";
	public static final String TEST_ENVIRONMENT = "testEnvironment";
	public static final String CAUSE = "Cause of the Exception : ";
	public static final String URLCHECK_LIST_FILE = "//Links_Verification_Results//";	

	// ==========> Configuration files and variables
	public static final String AUTOMATION_FRAMEWORK_CONFIG = "automation_framework_config";
	public static final String DB_CONFIG = "database_config";
	public static final String AUTOMATION_TEST_CONFIG = "automation_test_config";
	public static final String EMAIL_CONFIG = "email_config";
	public static final String CSV_FILE_PATH = "csvFilePath";
	public static final String LONG_LOADING = "LONG_LOADING";
	public static final String SHORT_LOADING = "SHORT_LOADING";

	// ============> Exception Messages
	public static final String CHECKBROWSER_MESSAGE = "Please check the browser name. Looks like the browser name is not valid";
	public static final String EXCEPTION_MESSAGE_FOR_LOAD_URL = "Cannot navigate to invalid URL, please specify valid URL";
	public static final String EXCEPTION_MESSAGE_CSV_FILE_PATH = "Specify test data CSV file path in automation_test_config.properties file";
	public static final String EXCEPTION_MESSAGE_EXCEL_FILE_PATH = "Specify test data Excel file path in automation_test_config.properties file";
	public static final String EXCEPTIION_EXCEL_SHEETNAME = "Can't read data from specified sheet, check sheet name";
	public static final String EXCEPTIION_EXCEL_COLUMN_NO = "Specify column index greater than zero";
	public static final String EXCEPTIION_EXCEL_ROW_NO = "Specify row index greater than zero";
	public static final String EXCEPTIION_EXCEL_COLUMN_NAME = "Excel column with given name not found, check the name";
	public static final String EXCEPTIION_EXCEL_PATH = "Give excel file path as argument";
	public static final String EXCEPTIION_EXCEL_FILE = "Please provide excel file with .xlsx or .xls format";
}
