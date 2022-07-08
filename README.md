# snj-selenium-java
This is a test automation framework for web applications on different browsers like Google Chrome, Mozilla Firefox, Microsoft Edge, Internet Explorer and Safari in real-time. It provides rich features like Test Execution, Test Reporting, and Test details sharing via mail. The automation framework includes 170+ pre-built keywords using which an Automation Engineer can easily perform the web automation.

<h3>Salient features of snj-selenium-java</h3>
<li>Automation support for web applications (in Windows, MAC and Linux platforms).
<br><li>Automation support for Google chrome, Mozilla Firefox, Microsoft Edge, Internet Explorer and Safari browsers.
<br><li>Automation support for headless execution of test scripts.
<br><li>Automation support for electron applications.
<br><li>Support for parallel execution on different browsers.
<br><li>Support for fully distributed remote executions (Selenium Grid 4).
<br><li>Support for Lighthouse audit on website with different categories such as performance, accessibility, seo, best-practices, pwa. 
<br><li>Support for accessibility testing on website to track the violations, violation impact, and help details to solve the violations.
<br><li>Support to collect the page load time for better performance tracking of an application.
<br><li>Support database validation.
<br><li>Support API testing and response validation.
<br><li>Support mock geolocation, simulate device mode, simulate network speed.
<br><li>Good reporting - framework generates HTML report.  
<br><li>Email collaboration - send an email with details of automation execution and HTML attachment. 
<br><li>Well defined keyword document, get from src/main/resources -> keywords folder of the project structure. 
	
**Steps to develop and execute automation scripts using snj-selenium-java**
<br><li>Set up Java, Eclipse/IntelliJ IDEA and dependent softwares.
<br><li>Import the **snj-selenium-java** framework into Eclipse/IntelliJ IDEA from this repository (master).	
<br><li>Configure details in **automation_framework_config** and **email_config** properties files inside the src/main/resources of the framework. 	
<br><li>Configure details in **automation_test_config** and **database_config** properties files inside the src/test/resources of the framework. 
<br><li>Manage TestRunner class inside the **snj.selenium.runner** package	
<br><li>Manage Test Suite Class inside the **snj.selenium.testcases** package and Test Helper class inside **snj.selenium.testhelpers** package.
<br><li>Manage Test Object Class inside the **snj.selenium.testobjects** package.
<br><li>Create **testng.xml** and map test suite classes to it.
<br><li>Run the **testng.xml** file and view the execution reports which generate in the **\Reports\Automation** folder of the project structure.	

**Manage TestRunner class**
<br>Once the TestRunner class ready under **snj.selenium.runner** package, you can use below code snippet in TestRunner class:
	
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
	
**Manage Test Suite Class**
<br>You can create multiple test suite classes under **snj.selenium.testcases** package, you can use below code snippet as sample test case in test suite class:
	
	public class SampleTest extends TestRunner {
	public SampleTest() throws AutomationException {
		super();
	}
	@Test(enabled = true)
	public void TC001_sampleTestCase() throws AutomationException {
		new WebActions().loadWebApplication(driver, "https://www.google.com");
		new UIActions().type(driver, SampleObjects.txt_searchbox, "JourneyOfQuality");
		new AutomationReport().trackSteps("Data Entered");
	}
	}
	
**Manage Test Helper Class**
<br>You can create multiple test helper classes under **snj.selenium.testhelpers** package. Test helper class can hold the actual automation step flow based on the application feature. Test helper methods should be re-usable in other test suite classes. Once you create the methods in test helper class, you can call those methods in test suite classes. 
	
**Manage Test Object Class**
<br>You can create multiple test object classes under **snj.selenium.testobjects** package. Test Object class can hold the object locator values like XPath, Id etc. These object creation based on your application page/screen. You can use the objects in test helper class to perform the actions on the object. Following is a sample snippet:
	
	public class SampleObjects {
		public static String txt_searchbox = "//input[@title='Search']";
	}
	
**Manage testng.xml**
<br>You can use the following code in **testng.xml** file:
	
	<?xml version="1.0" encoding="UTF-8"?>
        <!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
        <suite name="Suite">
	 <test thread-count="5" name="Execution for Test Suites">
		<parameter name="browserName" value="chrome" />
		<parameter name="gridIP" value="xxx.xxx.xxx.xxx" />
		<parameter name="gridPort" value="xxxx" />
		<classes>
		   <class name="snj.selenium.testcases.SampleTest" />
		</classes>
	 </test> <!-- Test -->
        </suite> <!-- Suite -->

**NOTE:** If you need run on different browsers, you can mention firefox or edge or ie or safari or headless for the **browserName** parameter in the above testng.xml code. If you need to run as Selenium Grid mode, just specify the values for **gridIP** and **gridPort**. For local execution, you just leave **gridIP** and **gridPort** as blank but need the **browserName** parameter value. For more about Selenium Grid setup, please visit https://journeyofquality.com/2022/01/26/a-variant-selenium-grid-4/   

**Perform Lighthouse Audit**
<br>This automation framework supports the lighthouse audit on the website with different categories such as performance, accessibility, SEO, best practices, and PWA. You can use **startLighthouseAudit** keyword of the UtilityActions class. Once the audit is completed the framework will generate the audit report in the **\Reports\Lighthouse_Audit** folder of the project structure. The prerequisites for the Lighthouse audit is to install the **lighthouse** node module package on your system. For more details about Lighthouse setup, please visit https://journeyofquality.com/2021/12/21/turn-on-your-lighthouse/. Below is the sample code for reference:
<br>`new UtilityActions().startLighthouseAudit(driver.getCurrentUrl(), "performance,seo", "no");`	

**Perform Accessibility Testing**
<br>This automation framework supports the accessibility testing on website to track the violations, violation impact, and help details to solve the violations. You can use **startAccessibilityAudit** keyword of the UtilityActions class in the test suite class to start the track violations. Once the execution is completed the framework will generate the accessibility test summary report in the **\Reports\Accessibility\Summary** folder of the project structure. In addition to the summary report, there are detailed text and JSON reports will generate in the **\Reports\Accessibility\Details** folder of the project structure.

**Collect Page Load Performance**
<br>This automation framework support to collect the page load time for a better performance tracking of an application. You can use **collectLoadTime** keyword of the UtilityActions class in the test suite class to collect the page load time. Once the execution is completed the framework will generate the performance test summary report in the **\Reports\Performance\Summary** folder of the project structure. 

I hope this automation framework will help to kickstart your automation scripting from the base level.	
	
Get your latest releases from https://github.com/sanoj-s/snj-selenium-java/releases	
	
_**make it perfect!**_
