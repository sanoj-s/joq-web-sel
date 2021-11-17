package com.snj.action;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.snj.base.AutomationEngine;
import com.snj.data.PropertyDataHandler;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

public class WebActions extends AutomationEngine {

	UtilityActions utilsObj = new UtilityActions();

	/**
	 * Method to get the current URL
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public String getCurrentWebUrl(WebDriver driver) throws AutomationException {
		String currentURL = "";
		try {
			currentURL = driver.getCurrentUrl();
		} catch (final Exception lException) {
			throw new AutomationException(getExceptionMessage(), lException);
		}
		return currentURL;
	}

	/**
	 * Method to switch to browser tab based on the index
	 * 
	 * @author sanojs
	 * @since 15-06-2021
	 * @param driver
	 * @param windowIndex
	 * @throws AutomationException
	 */
	public void switchToBrowserTab(WebDriver driver, int windowIndex) throws AutomationException {
		try {
			if (driver != null) {
				Object[] browserwindows = driver.getWindowHandles().toArray();
				driver.switchTo().window(browserwindows[windowIndex].toString());
				Thread.sleep(1200);
			}
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Method to switch to browser tab based on the index, then close it and switch
	 * back to parent tab
	 * 
	 * @author sanojs
	 * @since 15-06-2021
	 * @param driver
	 * @param windowIndex
	 * @throws AutomationException
	 */
	public void closeTabAndSwitchToParentTab(WebDriver driver, int windowIndex) throws AutomationException {
		try {
			if (driver != null) {
				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs.get(windowIndex).toString());
				driver.close();
				driver.switchTo().window(tabs.get(0));
				Thread.sleep(1500);
			}
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Method to Get the Title of the web page
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public String getTitle(WebDriver driver) throws AutomationException {
		String title = "";
		try {
			title = driver.getTitle();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return title;
	}

	/**
	 * Method to Accept the alert
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void acceptAlert(WebDriver driver) throws AutomationException {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Dismiss the alert
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void dismissAlert(WebDriver driver) throws AutomationException {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();

		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Get the Text from the Alert
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public String getAlertText(WebDriver driver) throws AutomationException {
		String alertText = "";
		try {
			Alert alert = driver.switchTo().alert();
			alertText = alert.getText();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return alertText;
	}

	/**
	 * Method to Get the List of Window Handles
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public ArrayList<String> getWindowHandles(WebDriver driver) throws AutomationException {
		try {
			ArrayList<String> windowHandles = new ArrayList<String>();
			windowHandles.addAll(driver.getWindowHandles());
			return windowHandles;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Navigate to the URL
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param url
	 * @throws AutomationException
	 */
	public void navigateToBrowser(WebDriver driver, String url) throws AutomationException {
		try {
			driver.navigate().to(url);
		} catch (Exception e) {
			throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_FOR_LOAD_URL);
		}
	}

	/**
	 * Method to Navigate Back to the previous web page
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void navigateBack(WebDriver driver) throws AutomationException {
		try {
			driver.navigate().back();
			Thread.sleep(2000);
		} catch (Exception e) {
			throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_FOR_LOAD_URL);
		}
	}

	/**
	 * Method to Navigate Forward to the previous web page
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void navigateForward(WebDriver driver) throws AutomationException {
		try {
			driver.navigate().forward();
		} catch (Exception e) {
			throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_FOR_LOAD_URL);
		}
	}

	/**
	 * Method to Refresh the web page
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void refreshPage(WebDriver driver) throws AutomationException {
		try {
			driver.navigate().refresh();
			Thread.sleep(3000);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to load the web application
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param Url
	 * @throws AutomationException
	 */
	public void loadWebApplication(WebDriver driver, String Url) throws AutomationException {
		try {
			driver.get(Url);
		} catch (Exception lException) {
			throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_FOR_LOAD_URL);
		}
	}

	/**
	 * Method to Close the Browser instance
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void closeBrowser(WebDriver driver) throws AutomationException {
		try {
			driver.close();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Quit the Web Browser
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void quitBrowser(WebDriver driver) throws AutomationException {
		try {
			driver.quit();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to wait for page load complete
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void waitForPageLoadComplete(WebDriver driver) throws NumberFormatException, Exception {
		try {
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				}
			};
			long timeout = Long.parseLong(new PropertyDataHandler()
					.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, AutomationConstants.SHORT_LOADING));
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(pageLoadCondition);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * This method is to test broken links in a Web page. To achieve, Users must be
	 * in the web page to be tested and call this method
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 * 
	 */
	public void verifyBrokenLinks(final WebDriver driver) throws AutomationException {
		List<WebElement> linkElements = null;
		int size = 0;
		int count = 0;
		String pageCurrentURL = "";
		FileOutputStream linkCheckerOutPut = null;
		byte[] firstRun = null;
		long startTime;
		Capabilities capabilities = null;
		if (driver != null) {
			final String currentURL = driver.getCurrentUrl();
			driver.get(currentURL);

			final long end = System.currentTimeMillis() + 5000;
			while (System.currentTimeMillis() < end) {
				linkElements = driver.findElements(By.tagName("a"));
			}
			size = linkElements.size();
			System.out.println("Number of links : " + size);
			capabilities = ((RemoteWebDriver) driver).getCapabilities();
		} else {
			System.out.println("Driver not initialised..");
			System.exit(0);
		}
		String browsername = "";
		try {
			browsername = (String) capabilities.getCapability("browserName");
		} catch (final NullPointerException e) {
			browsername = "UnknownBrowser";
		}
		String urls = null, attributeValue1 = null, attributeValue2 = null;
		final String[] hrefTags = new String[size];
		for (int i = 0; i < size; i++) {
			boolean resultDataLink = false;
			boolean resultHref = false;
			try {
				urls = linkElements.get(i).getAttribute("href");
				try {
					attributeValue1 = linkElements.get(i).getAttribute("data-link");
					resultDataLink = true;
				} catch (final Exception e) {
					resultDataLink = false;
				}
				try {
					attributeValue2 = linkElements.get(i).getAttribute("href");
					resultHref = true;

				} catch (final Exception e) {
					resultHref = false;
				}

				if (urls.equals(null) || (urls.equalsIgnoreCase("null"))) {
					count = count + 0;
				} else {
					hrefTags[count] = urls;
					count = count + 1;
				}
				if (attributeValue1 != null) {
					resultDataLink = true;
				} else if (attributeValue2 != null) {
					resultHref = true;
				}

				if (resultDataLink == true) {
					if (attributeValue1.equals(null) || (attributeValue1.equalsIgnoreCase("null"))) {
						count = count + 0;
					} else {
						hrefTags[count] = attributeValue1;
						count = count + 1;
					}
				} else if (resultHref == true) {
					if (attributeValue2.equals(null) || (attributeValue2.equalsIgnoreCase("null"))) {
						count = count + 0;
					} else {
						hrefTags[count] = attributeValue2;
						count = count + 1;
					}
				}
			} catch (final NullPointerException nuEx) {
				count = count + 0;
			}
		}
		final DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
		new File(new File(System.getProperty("user.dir")), AutomationConstants.URLCHECK_LIST_FILE).mkdirs();
		try {
			try {
				linkCheckerOutPut = new FileOutputStream(System.getProperty("user.dir")
						+ AutomationConstants.URLCHECK_LIST_FILE + "default_" + browsername + "_"
						+ dateFormat.format(new Date()) + "_" + +System.currentTimeMillis() + ".txt");

			} catch (final Exception lException) {
				lException.printStackTrace();
			}

		} catch (final Exception fnf) {
			fnf.printStackTrace();
		}
		try {
			firstRun = ("\n" + "Index" + "\t " + "Link Name" + "\t\t " + "Response Code" + "\n ").getBytes();
			linkCheckerOutPut.write(firstRun);
		} catch (final IOException e1) {
			e1.printStackTrace();
		}
		startTime = (System.currentTimeMillis() / 1000);

		driver.manage().timeouts().pageLoadTimeout(59, TimeUnit.SECONDS);

		for (int j = 0; j < hrefTags.length; j++) {
			try {
				if ((j < hrefTags.length) && (hrefTags[j] != null) && (!hrefTags[j].equals(""))) {
					if ((hrefTags[j].contains("javascript:"))) {
						System.out.println("URL contains javascript Method");
						firstRun = ("\n" + j + " \t" + hrefTags[j] + " \t" + " URL contains javascript Method ")
								.getBytes();
						try {
							linkCheckerOutPut.write(firstRun);
						} catch (final IOException e) {
						}
					} else {
						driver.get(hrefTags[j]);
						pageCurrentURL = driver.getCurrentUrl().toString().trim();
					}
					try {
						if (!pageCurrentURL.equals(hrefTags[j]) && (pageCurrentURL.equals(""))) {
							throw new Exception();
						}
						final URL url = new URL(hrefTags[j]);
						final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.connect();
						final int code = connection.getResponseCode();

						if (code == 200 || code == 201 || code == 202 || code == 203 || code == 204 || code == 205
								|| code == 206 || code == 207 || code == 208 || code == 226) {
							System.out.println("Server Response  : " + code);
							firstRun = ("\n" + j + " \t" + hrefTags[j] + " \t" + code + "\t\t - Success Code Returned")
									.getBytes();
							linkCheckerOutPut.write(firstRun);
						} else {
							System.out.println("URL not returned a Success response from server!!!");
							firstRun = ("\n" + j + " \t" + hrefTags[j] + " \t" + code
									+ "\t\t - Fail Response from server").getBytes();
							linkCheckerOutPut.write(firstRun);
							throw new Exception();
						}
					} catch (final Exception e) {
						System.out.println("Failed to load URL- Exception occured!!!");
					}
				}
			} catch (final Exception e) {
				continue;
			}
		}
		final long endTime = (System.currentTimeMillis() / 1000);
		final long elapsedTimeLong = endTime - startTime;
		final String elapsedTime = Long.toString(elapsedTimeLong);
		try {
			linkCheckerOutPut.write(("\n\n\n\t" + "Total Execution time : " + elapsedTime + " (Seconds)").getBytes());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to upload the file from your system
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param filePath
	 * @param elementName
	 * @throws AutomationException
	 */
	public void fileUpload(WebDriver driver, String filePath, String elementName) throws AutomationException {
		try {
			if (driver != null) {
				WebElement element = new UtilityActions().getWebElement(driver, elementName);
				if (element != null) {
					String operatingSystem = System.getProperty("os.name");
					Robot robot = new Robot();
					String actualFilepath = fileUploadDir(new File(filePath).getAbsolutePath());
					element.click();
					Thread.sleep(3000);
					StringSelection stringSelection;
					if (actualFilepath.contains("./")) {
						stringSelection = new StringSelection(actualFilepath.replace("./", ""));
					} else {
						stringSelection = new StringSelection(actualFilepath);
					}
					Thread.sleep(3000);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
					Thread.sleep(3000);
					try {
						if (operatingSystem.contains("Windows")) {
							Thread.sleep(1000);
							robot.keyPress(KeyEvent.VK_CONTROL);
							robot.keyPress(KeyEvent.VK_V);
							robot.keyRelease(KeyEvent.VK_V);
							robot.keyRelease(KeyEvent.VK_CONTROL);
							Thread.sleep(1000);
							robot.keyPress(KeyEvent.VK_ENTER);
							robot.keyRelease(KeyEvent.VK_ENTER);
						} else {
							// Cmd + Tab is needed since it launches a Java app
							// and
							// the browser
							// looses focus
							robot.delay(2000);
							robot.keyPress(KeyEvent.VK_META);
							robot.keyPress(KeyEvent.VK_TAB);
							robot.keyRelease(KeyEvent.VK_META);
							robot.keyRelease(KeyEvent.VK_TAB);
							robot.delay(1000);

							// Open Goto window
							robot.keyPress(KeyEvent.VK_META);
							robot.keyPress(KeyEvent.VK_SHIFT);
							robot.keyPress(KeyEvent.VK_G);
							robot.keyRelease(KeyEvent.VK_META);
							robot.keyRelease(KeyEvent.VK_SHIFT);
							robot.keyRelease(KeyEvent.VK_G);
							robot.delay(1000);

							// Paste the clipboard value
							robot.keyPress(KeyEvent.VK_META);
							robot.keyPress(KeyEvent.VK_V);
							robot.keyRelease(KeyEvent.VK_META);
							robot.keyRelease(KeyEvent.VK_V);

							// Press Enter key to close the Goto window and
							// Upload
							// window
							robot.keyPress(KeyEvent.VK_ENTER);
							robot.keyRelease(KeyEvent.VK_ENTER);
							robot.delay(1000);
							robot.keyPress(KeyEvent.VK_ENTER);
							robot.keyRelease(KeyEvent.VK_ENTER);

							// Switching the focus back to browser.
							robot.keyPress(KeyEvent.VK_META);
							robot.keyPress(KeyEvent.VK_TAB);
							robot.keyRelease(KeyEvent.VK_META);
							robot.keyRelease(KeyEvent.VK_TAB);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
				}
			}
		} catch (Exception lException) {
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Method to get correct directory name
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param fileLocation
	 * @throws AutomationException
	 */
	private String fileUploadDir(String fileLocation) {
		String replacecode = fileLocation.replace("\\", "//");
		File filepath = new File(replacecode);
		return filepath.toString();
	}
}
