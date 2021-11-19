package com.snj.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutomationEngine {

	public WebDriver driver;

	/**
	 * Start the web browser
	 * 
	 * @author sanojs
	 * @since 22-04-2021
	 * @param browserName
	 * @return
	 * @throws AutomationException
	 * @throws InterruptedException
	 */
	public WebDriver startBrowser(String browserName) throws AutomationException, InterruptedException {
		switch (browserName.toLowerCase()) {
		case "chrome":
		case "headless":
			startChrome(browserName);
			break;

		case "firefox":
			startFirefox();
			break;

		case "ie":
		case "internetexplorer":
			startInternetExplorer();
			break;

		case "edge":
			startEdge();
			break;

		case "safari":
			startSafari();
			break;

		default:
			System.out.println(AutomationConstants.CHECKBROWSER_MESSAGE);
			break;
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	/**
	 * Launch Safari browser
	 * 
	 * @author sanojs
	 * @throws InterruptedException
	 * @since 19-05-2021
	 */
	private void startSafari() throws InterruptedException {
		Process process;
		try {
			process = Runtime.getRuntime().exec("killall safaridriver");
			process.waitFor();
			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SafariOptions options = new SafariOptions();
		// options.useCleanSession(true);
		options.setAutomaticInspection(true);
		options.setUseTechnologyPreview(true);
		driver = new SafariDriver(options);
	}

	/**
	 * Launch Edge browser
	 * 
	 * @author sanojs
	 * @since 19-05-2021
	 */
	private void startEdge() throws AutomationException {
		try {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Launch Internet Explorer browser
	 * 
	 * @author sanojs
	 * @since 19-05-2021
	 * @throws AutomationException
	 */
	private void startInternetExplorer() throws AutomationException {
		try {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Launch Chrome browser
	 * 
	 * @author sanojs
	 * @throws AutomationException
	 * @since 13-04-2021
	 * @modified 16-03-2021
	 */
	private void startChrome(String browserName) throws AutomationException {
		try {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--test-type");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			if (browserName.equalsIgnoreCase("headless")) {
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--headless");
			}
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Launch Firefox browser
	 * 
	 * @author sanojs
	 * @throws AutomationException
	 * @since 13-04-2021
	 * @modified 16-03-2021
	 */
	private void startFirefox() throws AutomationException {
		try {
			WebDriverManager.firefoxdriver().setup();
			FirefoxProfile fp = new FirefoxProfile();
			fp.setPreference("intl.accept_languages", "no,en-us,en");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(FirefoxDriver.MARIONETTE, true);
			capabilities.setCapability(FirefoxDriver.PROFILE, fp);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			FirefoxOptions firefoxOptions = new FirefoxOptions(capabilities);
			driver = new FirefoxDriver(firefoxOptions);
			driver.manage().window().maximize();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Get the Exception message, to pass the message whenever an exception is
	 * encountered
	 * 
	 * @author sanojs
	 * @since 13-04-2021
	 */
	public static String getExceptionMessage() {
		StringBuffer message = new StringBuffer();

		try {
			message.append("Exception in ");
			message.append(Thread.currentThread().getStackTrace()[2].getClassName());
			message.append(".");
			message.append(Thread.currentThread().getStackTrace()[2].getMethodName());
			message.append("()");
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}

		return message.toString();
	}

	/**
	 * Set driver for Web applications
	 * 
	 * @author sanojs
	 * @since 03-02-2021
	 * @param driver
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Get current running driver session
	 * 
	 * @author sanojs
	 * @since 03-02-2021
	 */
	public WebDriver getDriver() {
		return this.driver;
	}
}
