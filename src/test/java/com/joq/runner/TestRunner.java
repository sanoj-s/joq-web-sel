package com.joq.runner;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.joq.base.AutomationEngine;
import com.joq.exception.AutomationException;
import com.joq.keywords.WebActions;
import com.joq.reporting.AutomationReport;

@Listeners(AutomationReport.class)
public class TestRunner extends AutomationEngine {
	@BeforeClass
	@Parameters({ "browserName", "browserVersion", "gridIP", "gridPort" })
	public void setup(String browserName, String browserVersion, String gridIP, String gridPort) throws Exception {
		startBrowser(browserName, browserVersion, gridIP, gridPort);
	}

	@AfterSuite
	public void tearDownMethod() throws AutomationException, InterruptedException {
		WebActions webObj = new WebActions();
		webObj.closeBrowser(driver);
	}
}
