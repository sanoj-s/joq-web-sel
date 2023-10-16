# joq-web-sel
**joq-web-sel** is a test automation framework for web applications on different browsers like Google Chrome, Mozilla Firefox, Microsoft Edge, Internet Explorer, and Safari in real-time. The framework supports Java programming language. It provides rich features like Test Execution, Test Reporting, and Test details sharing via mail. The automation framework includes 372 pre-built keywords using which an Automation Engineer can easily perform web automation.

<h3>Salient features of joq-web-sel</h3>
<li>Automation support for web applications (in Windows, MAC and Linux platforms).
<br><li>Automation support for Google Chrome, Mozilla Firefox, Microsoft Edge, Internet Explorer and Safari browsers.
<br><li>Automation support for headless execution of test scripts.
<br><li>Automation support for electron applications.
<br><li>Support for parallel execution on different browsers.
<br><li>Support for fully distributed remote executions (Selenium Grid 4).
<br><li>Support of Chrome for Testing (CfT) endpoints for chromedriver management and automated Chrome management.
<br><li>Support for Lighthouse audit on the website with different categories such as performance, accessibility, seo, best-practices, pwa. 
<br><li>Support for accessibility testing on the website to track the violations, violation impact, and help details to solve the violations.
<br><li>Support to collect the page load time for better performance tracking of an application.
<br><li>Support to generate the video report for the automation execution flow.
<br><li>Support to compare the images and generate image comparison reports. 
<br><li>Support of test data generator and it includes 159 built-in keywords to generate the test data.
<br><li>Support database validation.
<br><li>Support API testing, generate response time report and response validation. It also supports the automation of GraphQL APIs. 
<br><li>Support mock geolocation, simulate device mode, simulate network speed.
<br><li>Generate the Network logs while automation the execution of the web application.
<br><li>Good reporting - framework generates PDF and HTML reports.  
<br><li>Email collaboration - send an email with details of automation execution and HTML attachment. 
<br><li> Well-defined keyword document, get from src/main/resources -> keywords folder of the project structure. 
	
**Steps to develop and execute automation scripts using joq-web-sel**
<br><li>Set up Java, Eclipse/IntelliJ IDEA and dependent softwares.
<br><li>Import the **joq-web-sel** framework into Eclipse/IntelliJ IDEA from this repository (master).	
<br><li>Configure details in **framework_config** property file inside the src/main/resources of the framework. 	
<br><li>Configure details in **email_config**,**automation_test_config** and **database_config** properties files inside the src/test/resources of the framework. 
<br><li>Manage TestRunner class inside the **com.joq.runner** package	
<br><li>Manage Test Suite Class inside the **com.joq.testcases** package and Test Helper class inside **com.joq.testhelpers** package.
<br><li>Manage Test Object Class inside the **com.joq.testobjects** package.
<br><li>Create **testng.xml** and map test suite classes to it.
<br><li>Run the **testng.xml** file and view the execution reports which generate in the **\Reports\Automation** folder of the project structure.	

**Manage TestRunner class**
<br>Once the TestRunner class is ready under **com.joq.runner** package, you can use below code snippet in TestRunner class:
	
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
	
**Manage Test Suite Class**
<br>You can create multiple test suite classes under **com.joq.testcases** package, you can use below code snippet as a sample test case in the test suite class:
	
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
<br>You can create multiple test helper classes under **com.joq.testhelpers** package. Test helper class can hold the actual automation step flow based on the application feature. Test helper methods should be re-usable in other test suite classes. Once you create the methods in the test helper class, you can call those methods in test suite classes. 
	
**Manage Test Object Class**
<br>You can create multiple test object classes under **com.joq.testobjects** package. Test Object class can hold the object locator values like XPath, Id etc. These object creations are based on your application page/screen. You can use the objects in the test helper class to perform the actions on the object. Following is a sample snippet:
	
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
		<parameter name="browserVersion" value="118" />
		<parameter name="gridIP" value="xxx.xxx.xxx.xxx" />
		<parameter name="gridPort" value="xxxx" />
		<classes>
		   <class name="com.joq.testcases.SampleTest" />
		</classes>
	 </test> <!-- Test -->
        </suite> <!-- Suite -->

**NOTE:** If you need to run on different browsers, you can mention Firefox or edge or ie or Safari or headless for the **browserName** parameter in the above testng.xml code. You can also execute on different versions of Chrome browser, for that set value for **browserVersion**. If you need to run in Selenium Grid mode, just specify the values for **gridIP** and **gridPort**. For local execution, you just leave **gridIP** and **gridPort** blank but need the **browserName** parameter value. For more about Selenium Grid setup, please visit https://journeyofquality.com/2022/01/26/a-variant-selenium-grid-4/   

**Perform Lighthouse Audit**
<br>This automation framework supports the lighthouse audit on the website with different categories such as performance, accessibility, SEO, best practices, and PWA. You can use **startLighthouseAudit** keyword of the UtilityActions class. Once the audit is completed the framework will generate the audit report in the **\Reports\Lighthouse_Audit** folder of the project structure. The prerequisite for the Lighthouse audit is to install the **lighthouse** node module package on your system. For more details about the Lighthouse setup, please visit https://journeyofquality.com/2021/12/21/turn-on-your-lighthouse/. Below is the sample code for reference:
<br>`new UtilityActions().startLighthouseAudit(driver.getCurrentUrl(), "performance,seo", "no");`	

**Perform Accessibility Testing**
<br>This automation framework supports the accessibility testing on the website to track the violations and violation impact, and helpful details to solve the violations. You can use **startAccessibilityAudit** keyword of the UtilityActions class in the test suite class to start the track violations. Once the execution is completed the framework will generate the accessibility test summary report in the **\Reports\Accessibility_Audit\Summary** folder of the project structure. In addition to the summary report, there are detailed text and JSON reports will be generated in the **\Reports\Accessibility_Audit\Details** folder of the project structure.

**Collect Page Load Performance**
<br>This automation framework supports collecting the page load time for better performance tracking of an application. You can use **collectLoadTime** keyword of the UtilityActions class in the test suite class to collect the page load time. Once the execution is completed the framework will generate the performance test summary report in the **\Reports\Performance_Audit** folder of the project structure. 

**Image Comparison**
<br>This automation framework supports the comparison of the actual image against the expected image at runtime. You can use **compareImages** keyword of the ValidationActions class in the test suite class to compare the images. Once the execution is completed the framework will generate the image comparison report in the **\Reports\Image_Comparision** folder of the project structure. More details at https://journeyofquality.com/2023/01/13/image-comparison-during-automation/ 

**Video Recording**
<br>This automation framework supports generating the video recording of the automation workflow. You can use **startRecording** keyword of the UtilityActions class in the test suite class to start the recording and stop the recording by using the keyword **stopRecording** of the UtilityActions. Once the execution is completed the framework will generate the video report in the **\Reports\Automation_Videos** folder of the project structure. 

I hope this automation framework will help to kickstart your automation scripting from the base level.	
	
Get your latest releases from https://github.com/sanoj-s/joq-web-sel/releases
	
_**make it perfect!**_
