package com.snj.runner;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.snj.base.AutomationEngine;
import com.snj.exception.AutomationException;
import com.snj.keywords.WebActions;
import com.snj.reporting.AutomationReport;

@Listeners(AutomationReport.class)
public class TestRunner extends AutomationEngine {
	@BeforeClass
	@Parameters({ "browserName", "gridIP", "gridPort" })
	public void setup(String browserName, String gridIP, String gridPort) throws Exception {
		startBrowser(browserName, gridIP, gridPort);
	}

	@AfterSuite
	public void tearDownMethod() throws AutomationException, InterruptedException {
		WebActions webObj = new WebActions();
		webObj.closeBrowser(driver);
	}
}
