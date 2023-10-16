package com.joq.utils;

public class AutomationConstants {
	// =========> Generic
	public static final String OBJECT_NOT_FOUND = "Unable to locate ";
	public static final String PROJECT_NAME = "projectName";
	public static final String TEST_ENVIRONMENT = "testEnvironment";
	public static final String CAUSE = "Cause of the Exception : ";
	public static final String URLCHECK_LIST_FILE = "//Links_Verification_Results//";
	public static final String API_RESPONSE_DATA = "./src/test/resources/APITesting/ResponseData/";
	public static final String API_REQUEST_PAYLOAD = "./src/test/resources/APITesting/RequestPayload/";
	public static final String API_REQUEST_GRAPHQL = "./src/test/resources/APITesting/GraphQL/";
	public static final String REPORT_FOLDER_HTML = "//Reports//Automation_Execution_Reports//HTML_Reports//";
	public static final String REPORT_FOLDER_PDF = "//Reports//Automation_Execution_Reports//PDF_Reports";
	public static final String EMAIL_REPORT_FOLDER = "//test-output//Email_Reports//";
	public static final String NEED_NETWORK_LOGS = "needNetworkLogs";
	
	// ==========> Configuration files and variables
	public static final String AUTOMATION_FRAMEWORK_CONFIG = "framework_config";
	public static final String DB_CONFIG = "database_config";
	public static final String AUTOMATION_TEST_CONFIG = "automation_test_config";
	public static final String EMAIL_CONFIG = "email_config";
	public static final String CSV_FILE_PATH = "csvFilePath";
	public static final String LONG_LOADING = "LONG_LOADING";
	public static final String SHORT_LOADING = "SHORT_LOADING";
	public static final String NEED_MAIL_REPORT = "isMailReportNeed";

	// ============> Exception Messages
	public static final String CHECKBROWSER_MESSAGE = "Please check the browser name. Looks like the browser name is not valid";
	public static final String ELECTRON_APPLICATION_MISSING_ERROR_MESSAGE = "Electron application path is missing in the automation_test_config.properties file, please set the path and try again";
	public static final String GRIDNODEIP_PORTMISSING = "Grid nodeIP or nodePort missing in testng.xml";
	public static final String GRID_BROWSER_MISSING = "Browser name missing in testng.xml";
	public static final String EXCEPTION_MESSAGE_FOR_LOAD_URL = "Cannot navigate to invalid web URL, please specify valid web URL";
	public static final String EXCEPTION_MESSAGE_CSV_FILE_PATH = "Specify test data CSV file path in automation_test_config.properties file";
	public static final String EXCEPTION_MESSAGE_EXCEL_FILE_PATH = "Specify test data Excel file path in automation_test_config.properties file";
	public static final String EXCEPTIION_EXCEL_SHEETNAME = "Can't read data from specified sheet, check sheet name";
	public static final String EXCEPTIION_EXCEL_COLUMN_NO = "Specify column index greater than zero";
	public static final String EXCEPTIION_EXCEL_ROW_NO = "Specify row index greater than zero";
	public static final String EXCEPTIION_EXCEL_COLUMN_NAME = "Excel column with given name not found, check the name";
	public static final String EXCEPTIION_EXCEL_PATH = "Give excel file path as argument";
	public static final String EXCEPTIION_EXCEL_FILE = "Please provide excel file with .xlsx or .xls format";
	public static final String EXCEPTION_MESSAGE_FAILED_TO_GET_SCREEN = "Failed to get current screen";
	public static final String EXCEPTION_MESSAGE_INPUT_IMAGE_NOT_FOUND = "Specified expected image not found";
	public static final String EXCEPTION_MESSAGE_OUTPUT_IMAGE_NOT_FOUND = "Specified actual image not found";

	// ==============> Automation Reporting
	public static final String APP_ICON_PATH = "SupportFiles/app_icon.png";
	public static final String NOIMAGE_ICON_PATH = "SupportFiles/noImage.png";
	public static final String EXCEPTION_MESSAGE_APP_ICON_FAILS = "You have not added app icon in the SupportFiles folder of src/main/resources, please specify it if you need an app icon in the test report\n";
	public static final String PASSED_TESTS = "Passed Tests";
	public static final String FAILED_TESTS = "Failed Tests";
	public static final String SKIPPED_TESTS = "Skipped Tests";
	public static final String HEADING_METHOD = "\nTest Cases";
	public static final String HEADING_TIME = "Execution Duration \n(in seconds)";
	public static final String FAILEDTESTCASE = "Test Case Failed : ";
	public static final String FAILUREREASON = "Reason for Failure : ";
	public static final String FAILURE_DETAILS = "Failure Details";
	public static final String TOTAL_TIME = "Total Execution Time :";
	public static final String LABEL_FONT = "Arial Unicode MS";
	public static final String PASSED = "Passed";
	public static final String FAILED = "Failed";
	public static final String SKIPPED = "Skipped";
	public static final String TEST_ENVIRONMENTS = "Test Environment";
	public static final String EXECUTION_ENVIRONMENTS = "Execution Environment";
	public static final String PLATFORM_NAME = "Platform";
	public static final String BROWSER_NAME = "Browser Name";
	public static final String BROWSER_VERSION = "Browser Version";
}
