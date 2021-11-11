package com.snj.reporting;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.snj.action.UtilityActions;
import com.snj.base.AutomationEngine;
import com.snj.data.PropertyDataHandler;
import com.snj.utils.AutomationConstants;
import com.snj.utils.AutomationMail;

public class AutomationReport implements ITestListener {
	int successCount = 0, failureCount = 0, skippedCount = 0, testCount = 0;
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	String reportPath = System.getProperty("user.dir") + "/Reports/";

	/**
	 * Method to set up the Execution Report
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @throws Exception
	 */
	public void onStart(ITestContext testContext) {
		try {
			String testEnvironment = new PropertyDataHandler().getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG,
					AutomationConstants.TEST_ENVIRONMENT);
			sparkReporter = new ExtentSparkReporter(reportPath);
			XmlTest test = testContext.getCurrentXmlTest();
			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			extent.setSystemInfo("Browser Name", test.getParameter("browserName").toString());
			extent.setSystemInfo("Test Environment", testEnvironment);
			extent.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
			extent.setSystemInfo("IP address", InetAddress.getLocalHost().getHostAddress());

			sparkReporter.config().setDocumentTitle("Test Automation");
			sparkReporter.config().setReportName("Automation Execution Report");
			sparkReporter.config().setTheme(Theme.DARK);
			sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		} catch (Exception lException) {
			lException.printStackTrace();
		}
	}

	/**
	 * Method to collect the test names
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param result
	 */
	public void onTestStart(ITestResult result) {
		try {
			String testName = result.getMethod().getMethodName();
			test = extent.createTest(testName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to track the steps
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param stepAction
	 */
	public void trackSteps(String stepAction) {
		try {
			test.log(Status.INFO, MarkupHelper.createLabel(stepAction, ExtentColor.PURPLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to track the steps
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param categoryName
	 */
	public void assignCategory(String categoryName) {
		try {
			test.assignCategory(categoryName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get the pass result of the execution
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @throws IOException
	 */
	public void onTestSuccess(ITestResult result) {
		successCount++;
		try {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
			System.out.println("********************************************");
			System.out.println("TEST CASE: " + result.getName() + " IS PASS");
			System.out.println("********************************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get the fail result of the execution
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @throws IOException
	 */
	public void onTestFailure(ITestResult result) {
		failureCount++;
		try {
			Object currentClass = result.getInstance();
			WebDriver driver = ((AutomationEngine) currentClass).getDriver();

			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
					ExtentColor.RED));
			test.fail(result.getThrowable());
			test.fail("Please find the screenshot below");
			test.addScreenCaptureFromPath(new UtilityActions().takeScreenshot(driver, result.getName()),
					result.getName());
			System.out.println("********************************************");
			System.out.println("TEST CASE: " + result.getName() + " IS FAIL");
			System.out.println("********************************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get the skip result of the execution
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @throws IOException
	 */
	public void onTestSkipped(ITestResult result) {
		skippedCount++;
		try {
			test.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
			System.out.println("********************************************");
			System.out.println("TEST CASE: " + result.getName() + " IS SKIPPED");
			System.out.println("********************************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to tear down the report and to call the send mail method
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 */
	public void onFinish(ITestContext testContext) {
		try {
			extent.flush();
			String testEnvironment = new PropertyDataHandler().getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG,
					AutomationConstants.TEST_ENVIRONMENT);
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
			Date date = new Date();
			String filePathdate = dateFormat.format(date).toString();
			String actualReportPath = reportPath + "index.html";
			new File(actualReportPath).renameTo(new File(
					System.getProperty("user.dir") + "/Reports/" + "Automation_Report_" + filePathdate + ".html"));

			String isMailReportNeed = new PropertyDataHandler()
					.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "isMailReportNeed");
			if (isMailReportNeed.toLowerCase().equals("yes")) {
				new AutomationMail().sendMailReport();
			}
			System.out.println("********************************************");
			System.out.println("TEST CASE EXECUTION COMPLETED ON " + testEnvironment);
			System.out.println("********************************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
