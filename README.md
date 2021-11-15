# snj-selenium-java
This is a test automation framework for the web applications on different browsers like Google chrome, Mozilla Firefox, Microsoft Edge, Internet Explorer and Safari in real time. It provides rich features like Test Execution, Test Reporting, and Test details sharing via mail. The  automation framework includes 110+ pre-built keywords using which an Automation Engineer can easily perform the web automation.

<h3>Salient features of snj-selenium-java:</h3>
<li>Automation support for web applications (Windows and MAC).
<br><li>Automation support for Google chrome, Mozilla Firefox, Microsoft Edge, Internet Explorer and Safari browsers.
<br><li>Automation support for headless execution of test scripts.
<br><li>Support for parallel execution on different browsers.
<br><li>Support database validation.
<br><li>Good reporting - framework generates HTML report. 
<br><li>Email collaboration - send an email with details of automation execution and HTML attachment. 
	
**Steps to develop and execute automation scripts using snj-selenium-java:**
<br><li>Set up Java, Eclipse/IntelliJ IDEA and dependent softwares.
<br><li>SImport the **snj-selenium-java** framework into Eclipse/IntelliJ IDEA from this repository (master).	
<br><li>Configure details in **automation_framework_config** and **email_config** properties files inside the src/main/resources of the framework. 	
<br><li>Configure details in **automation_test_config** and **database_config** properties files inside the src/test/resources of the framework. 
<br><li>Manage TestRunner class inside the **snj.selenium.runner** package	
<br><li>Manage Test Suite Class inside the **snj.selenium.testcases** package and Test Helper class inside **snj.selenium.testhelpers** package.
<br><li>Manage Test Object Class inside the **snj.selenium.testobjects** package.
<br><li>Create **testng.xml** and map test suite classes to it.
<br><li>Run the **testng.xml** file and view the execution reports which generate in the **Reports** folder of the project structure.	
