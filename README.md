# snj-selenium-java
This is a test automation framework for the web applications on different browsers like Google Chrome, Mozilla Firefox, Microsoft Edge, Internet Explorer and Safari in real-time. It provides rich features like Test Execution, Test Reporting, and Test details sharing via mail. The  automation framework includes 110+ pre-built keywords using which an Automation Engineer can easily perform the web automation.

<h3>Salient features of snj-selenium-java</h3>
<li>Automation support for web applications ( in Windows, MAC and Linux platforms).
<br><li>Automation support for Google chrome, Mozilla Firefox, Microsoft Edge, Internet Explorer and Safari browsers.
<br><li>Automation support for headless execution of test scripts.
<br><li>Support for parallel execution on different browsers.
<br><li>Support database validation.
<br><li>Good reporting - framework generates HTML report. 
<br><li>Email collaboration - send an email with details of automation execution and HTML attachment. 
	
**Steps to develop and execute automation scripts using snj-selenium-java**
<br><li>Set up Java, Eclipse/IntelliJ IDEA and dependent softwares.
<br><li>SImport the **snj-selenium-java** framework into Eclipse/IntelliJ IDEA from this repository (master).	
<br><li>Configure details in **automation_framework_config** and **email_config** properties files inside the src/main/resources of the framework. 	
<br><li>Configure details in **automation_test_config** and **database_config** properties files inside the src/test/resources of the framework. 
<br><li>Manage TestRunner class inside the **snj.selenium.runner** package	
<br><li>Manage Test Suite Class inside the **snj.selenium.testcases** package and Test Helper class inside **snj.selenium.testhelpers** package.
<br><li>Manage Test Object Class inside the **snj.selenium.testobjects** package.
<br><li>Create **testng.xml** and map test suite classes to it.
<br><li>Run the **testng.xml** file and view the execution reports which generate in the **Reports** folder of the project structure.	

**Manage TestRunner class**
<br>Once the TestRunner class ready under **snj.selenium.runner** package, you can use below code snippet in TestRunner class:
	
	@Listeners(AutomationReport.class)
	public class TestRunner extends AutomationEngine {
	@BeforeClass
	@Parameters("browserName")
	public void setup(String browserName) throws Exception {
		startBrowser(browserName);
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
			<classes>
				<class name="snj.selenium.testcases.SampleTest" />
			</classes>
		</test> <!-- Test -->
	</suite> <!-- Suite -->

NOTE: If you need run on different browsers, you can mention firefox or edge or ie or headless for the **browserName** parameter in the above testng.xml code. 

I hope this automation framework will help to kickstart your automation scripting from the base level.	
	
_**make it perfect!**_
