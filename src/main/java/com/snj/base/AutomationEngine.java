package com.snj.base;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.snj.data.PropertyDataHandler;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

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
	public WebDriver startBrowser(String browserName, String gridIP, String gridPort)
			throws AutomationException, InterruptedException {
		switch (browserName.toLowerCase()) {
		case "chrome":
		case "headless":
			if (!gridIP.equalsIgnoreCase("")) {
				startExecutionInGrid(gridIP, gridPort, browserName);
			} else {
				startChrome(browserName);
			}
			break;

		case "firefox":
			if (!gridIP.equalsIgnoreCase("")) {
				startExecutionInGrid(gridIP, gridPort, browserName);
			} else {
				startFirefox();
			}
			break;

		case "ie":
		case "internetexplorer":
			if (!gridIP.equalsIgnoreCase("")) {
				startExecutionInGrid(gridIP, gridPort, browserName);
			} else {
				startInternetExplorer();
			}
			break;

		case "edge":
			if (!gridIP.equalsIgnoreCase("")) {
				startExecutionInGrid(gridIP, gridPort, browserName);
			} else {
				startEdge();
			}
			break;

		case "safari":
			if (!gridIP.equalsIgnoreCase("")) {
				startExecutionInGrid(gridIP, gridPort, browserName);
			} else {
				startSafari();
			}
			break;

		case "electron":
			startElectronApplication();
			break;

		default:
			System.out.println(AutomationConstants.CHECKBROWSER_MESSAGE);
			break;
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}

	/**
	 * To start the selenium grid execution in the distributed environment
	 * 
	 * @author sanojs
	 * @since 19-01-2022
	 * @param gridIP
	 * @param gridPort
	 * @param browserName
	 * @return
	 * @throws AutomationException
	 */
	private RemoteWebDriver startExecutionInGrid(String gridIP, String gridPort, String browserName)
			throws AutomationException {
		DesiredCapabilities gridCap = new DesiredCapabilities();
		String nodeURL = "", platformName = "";
		try {
			if (gridIP != "" && gridPort != null && gridIP != null && gridPort != "") {
				nodeURL = ("http://" + gridIP + ":" + gridPort).toString().toLowerCase().trim();
			} else {
				Exception ex = new AutomationException(AutomationConstants.GRIDNODEIP_PORTMISSING);
				throw new AutomationException(ex);
			}

			try {
				if (browserName != "" && browserName != null) {
					if ("firefox".equalsIgnoreCase(browserName))
						gridCap.setBrowserName("firefox");
					if (("internetExplorer".equalsIgnoreCase(browserName)) | ("IE".equalsIgnoreCase(browserName)))
						gridCap.setBrowserName("internet explorer");
					if ("chrome".equalsIgnoreCase(browserName))
						gridCap.setBrowserName("chrome");
					if ("safari".equalsIgnoreCase(browserName))
						gridCap.setBrowserName("safari");
					if ("edge".equalsIgnoreCase(browserName))
						gridCap.setBrowserName("MicrosoftEdge");
				} else {
					throw new AutomationException(AutomationConstants.GRID_BROWSER_MISSING);
				}

				platformName = System.getProperty("os.name");
				if (platformName != "" && platformName != null) {
					if (platformName.equalsIgnoreCase("WINDOWS"))
						gridCap.setPlatform(Platform.WINDOWS);
					if (platformName.equalsIgnoreCase("LINUX"))
						gridCap.setPlatform(Platform.LINUX);
					if (platformName.equalsIgnoreCase("MAC"))
						gridCap.setPlatform(Platform.MAC);
					if (platformName.equalsIgnoreCase("ANY"))
						gridCap.setPlatform(Platform.ANY);
				}
				driver = new RemoteWebDriver(new URL(nodeURL), gridCap);

			} catch (Exception e) {
				throw new AutomationException(e);
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return (RemoteWebDriver) driver;
	}

	/**
	 * Launch Electron application
	 * 
	 * @author sanojs
	 * @since 12-01-2022
	 * @throws AutomationException
	 */
	private void startElectronApplication() throws AutomationException {
		try {
			String pathToElectronApplication = new PropertyDataHandler()
					.getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG, "electronApplicationPath");
			if ((pathToElectronApplication.equals("")) || (pathToElectronApplication.equalsIgnoreCase(null))) {
				System.out.println(AutomationConstants.ELECTRON_APPLICATION_MISSING_ERROR_MESSAGE);
				System.exit(0);
			}
			ChromeOptions options = new ChromeOptions();
			options.setBinary(new File(pathToElectronApplication).getAbsolutePath());
			driver = new ChromeDriver(options);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
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
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
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
