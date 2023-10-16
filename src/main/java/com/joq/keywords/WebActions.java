package com.joq.keywords;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.joq.base.AutomationEngine;
import com.joq.exception.AutomationException;
import com.joq.utils.AutomationConstants;

public class WebActions extends AutomationEngine {

	Utilities utilsObj = new Utilities();

	/**
	 * Load the web application
	 * 
	 * @author sanoj.swaminathan
	 * @since 16-04-2021
	 * @param driver
	 * @param webApplicationUrl
	 * @throws AutomationException
	 */
	public void loadWebApplication(WebDriver driver, String webApplicationUrl) throws AutomationException {
		try {
			driver.get(webApplicationUrl);
		} catch (Exception lException) {
			throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_FOR_LOAD_URL);
		}
	}

	/**
	 * Navigate to the new web application URL
	 * 
	 * @author sanoj.swaminathan
	 * @since 16-04-2021
	 * @param driver
	 * @param webApplicationUrl
	 * @throws AutomationException
	 */
	public void navigateToWebApplication(WebDriver driver, String webApplicationUrl) throws AutomationException {
		try {
			driver.navigate().to(webApplicationUrl);
		} catch (Exception e) {
			throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_FOR_LOAD_URL);
		}
	}

	/**
	 * Get the current web URL
	 * 
	 * @author sanoj.swaminathan
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public String getWebUrl(WebDriver driver) throws AutomationException {
		String currentURL = "";
		try {
			currentURL = driver.getCurrentUrl();
		} catch (final Exception lException) {
			throw new AutomationException(getExceptionMessage(), lException);
		}
		return currentURL;
	}

	/**
	 * Get the title of the web page
	 * 
	 * @author sanoj.swaminathan
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public String getWebApplicationTitle(WebDriver driver) throws AutomationException {
		String title = "";
		try {
			title = driver.getTitle();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return title;
	}

	/**
	 * Navigate back to the previous web page
	 * 
	 * @author sanoj.swaminathan
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
	 * Navigate forward to the next web page
	 * 
	 * @author sanoj.swaminathan
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void navigateForward(WebDriver driver) throws AutomationException {
		try {
			driver.navigate().forward();
			Thread.sleep(2000);
		} catch (Exception e) {
			throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_FOR_LOAD_URL);
		}
	}

	/**
	 * Refresh the web page
	 * 
	 * @author sanoj.swaminathan
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
	 * Get the list of window handles
	 * 
	 * @author sanoj.swaminathan
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
	 * Switch to browser tab
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-06-2021
	 * @param driver
	 * @param tabIndex
	 * @throws AutomationException
	 */
	public void switchToBrowserTab(WebDriver driver, int tabIndex) throws AutomationException {
		try {
			if (driver != null) {
				Object[] browserwindows = driver.getWindowHandles().toArray();
				driver.switchTo().window(browserwindows[tabIndex].toString());
				Thread.sleep(2000);
			}
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Switch to browser tab, then close it and switch back to parent tab
	 * 
	 * @author sanoj.swaminathan
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
	 * Switch to frame in the web page based on index
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-06-2021
	 * @param driver
	 * @param tabIndex
	 * @throws AutomationException
	 */
	public void switchToFrame(WebDriver driver, int index) throws AutomationException {
		try {
			if (driver != null) {
				driver.switchTo().frame(index);
				Thread.sleep(2000);
			}
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Switch to frame in the web page based on frame name or Id
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-06-2021
	 * @param driver
	 * @param frameNameOrId
	 * @throws AutomationException
	 */
	public void switchToFrame(WebDriver driver, String frameNameOrId) throws AutomationException {
		try {
			if (driver != null) {
				driver.switchTo().frame(frameNameOrId);
				Thread.sleep(2000);
			}
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Switch to frame in the web page based on WebElement, you can use
	 * getWebElement() of UtilityActions to get the WebElement value
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-06-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void switchToFrame(WebDriver driver, WebElement elementName) throws AutomationException {
		try {
			if (driver != null) {
				driver.switchTo().frame(elementName);
				Thread.sleep(2000);
			}
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Switch out from the frame
	 *
	 * @author sanoj.swaminathan
	 * @since 15-06-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void switchOutFromFrame(WebDriver driver) throws AutomationException {
		try {
			if (driver != null) {
				driver.switchTo().defaultContent();
				Thread.sleep(2000);
			}
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Accept the browser alert
	 * 
	 * @author sanoj.swaminathan
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
	 * Dismiss the browser alert
	 * 
	 * @author sanoj.swaminathan
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
	 * Get the text or label from the browser alert
	 * 
	 * @author sanoj.swaminathan
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
	 * Close the browser window
	 * 
	 * @author sanoj.swaminathan
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
	 * Quit the browser session
	 * 
	 * @author sanoj.swaminathan
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
	 * Upload the file from your system to a web page
	 * 
	 * @author sanoj.swaminathan
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @param filePath
	 * @throws AutomationException
	 */
	public void uploadFile(WebDriver driver, String elementName, String filePath) throws AutomationException {
		try {
			if (driver != null) {
				WebElement element = new Utilities().getWebElement(driver, elementName);
				if (element != null) {
					String operatingSystem = System.getProperty("os.name");
					Robot robot = new Robot();
					String actualFilepath = fileUploadDir(new File(filePath).getAbsolutePath());
					new UIActions().tap(driver, elementName);
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
	 * Get correct directory name to help upload file
	 * 
	 * @author sanoj.swaminathan
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
