package snj.selenium.testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.snj.exception.AutomationException;
import com.snj.keywords.UIActions;
import com.snj.keywords.Utilities;
import com.snj.keywords.WebActions;

import snj.selenium.runner.TestRunner;
import snj.selenium.testobjects.SampleObjects;

public class SampleTest extends TestRunner {
	public SampleTest() throws AutomationException {
		super();
	}

	@Test(enabled = true)
	public void TC001_sampleTestCase() throws Exception {
		new Utilities().startRecording("Test");
		new WebActions().loadWebApplication(driver, "https://www.google.com");
		new UIActions().type(driver, SampleObjects.txt_searchbox, "JourneyOfQuality");
//		new ValidationActions().verifyEquals(new ValidationActions().compareImages("D:\\Test\\TestImage\\actual.png",
//				"D:\\Test\\TestImage\\expected.png"), "100%", "Image doesn't match");
//		System.out.println(new ValidationActions().compareImages(driver, "Validated google page",
//				"D:\\Test\\TestImage\\expected.png", "Test1"));
//		System.out.println(new ValidationActions().compareImages(driver, "Validate google search box",
//				"D:\\Test\\TestImage\\expected1.png", "Test1"));
//		new AutomationReport().trackSteps("Data Entered");
//		new UtilityActions().stopRecording();
//		new WebActions().loadWebApplication(driver, "https://automationbookstore.dev/");
//		new UtilityActions().collectLoadTime(driver, "//h2[@id='pid1_title']", 2, "Home", "AutomationBook");
//		new Utilities().startLighthouseAudit(driver.getCurrentUrl(), "performance,seo", "no");
//		new UtilityActions().startAccessibilityAudit(driver, "Home");
	}

	@Test(enabled = false)
	public void TC002_sampleTestCase() throws AutomationException, IOException, InterruptedException {

//		new UIActions().click(driver, "li", "left", SampleObjects.btn_book);
//		new UIActions().click(driver, "li", "right", SampleObjects.btn_book1);
//		new UIActions().click(driver, "li", "above", SampleObjects.btn_book);
//		new UIActions().click(driver, "li", "below", SampleObjects.btn_book3);
//		new UIActions().click(driver, "li", "near", SampleObjects.btn_book2);
	}

}
