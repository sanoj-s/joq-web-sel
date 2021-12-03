package com.snj.action;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.snj.base.AutomationEngine;
import com.snj.data.PropertyDataHandler;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

public class UtilityActions extends AutomationEngine {

	public WebDriverWait wait;
	public Random random;

	/**
	 * Get an element based on the visibility and return WebElement
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @return element
	 * @throws AutomationException
	 */
	public WebElement getWebElement(WebDriver driver, String elementName) throws AutomationException {
		WebElement element = null;
		PropertyDataHandler objProertyData = new PropertyDataHandler();
		try {
			long timeout = Long.parseLong(objProertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG,
					AutomationConstants.SHORT_LOADING));
			wait = new WebDriverWait(driver, timeout);
			By actualElement = getElementByLocator(elementName);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(actualElement));
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(" + element.getLocation().x + "," + element.getLocation().y + ")");
		} catch (Exception e) {
			throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
		}
		return element;
	}

	/**
	 * Get an element based on the presence of element located and return WebElement
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementLocator
	 * @return element
	 * @throws AutomationException
	 */
	public WebElement getWebElement(WebDriver driver, By elementLocator) throws AutomationException {
		WebElement element = null;
		PropertyDataHandler objProertyData = new PropertyDataHandler();
		try {
			long timeout = Long.parseLong(
					objProertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
			wait = new WebDriverWait(driver, timeout);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
		} catch (Exception e) {
			throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementLocator + "'");
		}
		return element;
	}

	/**
	 * Get the By locator value
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param elementName
	 * @return
	 * @throws AutomationException
	 */
	public By getElementByLocator(String elementName) throws AutomationException {
		By byElement = null;
		try {
			if (elementName.startsWith("#") || elementName.startsWith("td[") || elementName.startsWith("tr[")
					|| elementName.startsWith("td ") || elementName.startsWith("tr ")
					|| elementName.startsWith("input[") || elementName.startsWith("span[")
					|| elementName.startsWith("div") || elementName.startsWith(".")) {
				byElement = By.cssSelector(elementName);
			} else if (elementName.startsWith("//") || elementName.startsWith(".//") || elementName.startsWith("(.//")
					|| elementName.startsWith("(//") || elementName.startsWith("((//")) {
				byElement = By.xpath(elementName);
			} else if (elementName.startsWith("name")) {
				byElement = By.name(elementName.split(">>")[1]);
			} else if (elementName.startsWith("id")) {
				byElement = By.id(elementName.split(">>")[1]);
			} else if (elementName.startsWith("className")) {
				byElement = By.className(elementName.split(">>")[1]);
			} else if (elementName.startsWith("linkText")) {
				byElement = By.linkText(elementName.split(">>")[1]);
			} else if (elementName.startsWith("partialLinkText")) {
				byElement = By.partialLinkText(elementName.split(">>")[1]);
			} else {
				byElement = By.tagName(elementName);
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return byElement;
	}

	/**
	 * Wait for an element based on the visibility and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @return isElementVisible
	 * @throws AutomationException
	 */
	public boolean waitForElement(WebDriver driver, String elementName) throws AutomationException {
		boolean isElementVisible = false;
		PropertyDataHandler objPropertyData = new PropertyDataHandler();
		try {
			long timeout = Long.parseLong(
					objPropertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
			wait = new WebDriverWait(driver, timeout);
			By actualElement = getElementByLocator(elementName);
			wait.until(ExpectedConditions.visibilityOfElementLocated(actualElement));
			isElementVisible = true;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return isElementVisible;
	}

	/**
	 * Wait for an element based on the invisibility and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @return isElementInVisible
	 * @throws AutomationException
	 */
	public boolean waitForElementInVisible(WebDriver driver, String elementName) throws AutomationException {
		boolean isElementInVisible = false;
		PropertyDataHandler objPropertyData = new PropertyDataHandler();
		try {
			long timeout = Long.parseLong(
					objPropertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
			wait = new WebDriverWait(driver, timeout);
			By actualElement = getElementByLocator(elementName);
			isElementInVisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(actualElement));
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return isElementInVisible;
	}

	/**
	 * Wait for text or label to be present and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @return element
	 * @throws AutomationException
	 */
	public boolean waitForTextPresent(WebDriver driver, String elementName, String expectedText)
			throws AutomationException {
		boolean isTextPresent = false;
		PropertyDataHandler objPropertyData = new PropertyDataHandler();
		try {
			long timeout = Long.parseLong(
					objPropertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
			wait = new WebDriverWait(driver, timeout);
			By actualElement = getElementByLocator(elementName);
			isTextPresent = wait.until(ExpectedConditions.textToBePresentInElementLocated(actualElement, expectedText));
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return isTextPresent;
	}

	/**
	 * Wait for the elements based on the presence of element located and return
	 * lists of WebElement
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @return elements
	 * @throws AutomationException
	 */
	public List<WebElement> waitForElements(WebDriver driver, String elementName) throws AutomationException {
		List<WebElement> elements;
		PropertyDataHandler objProertyData = new PropertyDataHandler();
		try {
			if (elementName != null) {
				long timeout = Long.parseLong(
						objProertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
				wait = new WebDriverWait(driver, timeout);
				By actualElement = getElementByLocator(elementName);
				wait.until(ExpectedConditions.presenceOfElementLocated(actualElement));
				elements = driver.findElements(actualElement);
			} else
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
		} catch (Exception e) {
			throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
		}
		return elements;
	}

	/**
	 * Wait for the page load complete
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
	 * Set delay between test steps
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param driver
	 * @param delayInSeconds
	 * @param doubleValue
	 * @throws AutomationException
	 */
	public void delay(int delayInSeconds) throws AutomationException {
		try {
			Thread.sleep(delayInSeconds * 1000);
		} catch (Exception lException) {
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Set delay between test steps based on driver session
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param driver
	 * @param delayInSeconds
	 * @param doubleValue
	 * @throws AutomationException
	 */
	public void delay(WebDriver driver, int delayInSeconds) throws AutomationException {
		try {
			driver.manage().timeouts().implicitlyWait(Long.valueOf(delayInSeconds), TimeUnit.SECONDS);
		} catch (Exception lException) {
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Get the text or label of the WebElement
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public String getElementText(WebDriver driver, String elementName) throws AutomationException {
		String elementText = "";
		try {
			WebElement element = getWebElement(driver, elementName);
			elementText = element.getText();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementText;
	}

	/**
	 * Get the attribute value of the WebElement
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @param attributeName
	 * @throws AutomationException
	 */
	public String getElementAttributeValue(WebDriver driver, String elementName, String attributeName)
			throws AutomationException {
		String elementAttribute = "";
		try {
			WebElement element = getWebElement(driver, elementName);
			elementAttribute = element.getAttribute(attributeName);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementAttribute;
	}

	/**
	 * Get the X co-ordinate of the WebElement
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public int getElementXcoordinate(WebDriver driver, String elementName) throws AutomationException {
		try {
			WebElement element = getWebElement(driver, elementName);
			Point point = element.getLocation();
			return point.getX();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Get the y co-ordinate of the WebElement
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public int getElementycoordinate(WebDriver driver, String elementName) throws AutomationException {
		try {
			WebElement element = getWebElement(driver, elementName);
			Point point = element.getLocation();
			return point.getY();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Get the Count of the elements from the WebElement list
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public int getCountOfElements(WebDriver driver, String elementName) throws AutomationException {
		try {
			List<WebElement> elements = waitForElements(driver, elementName);
			return elements.size();
		} catch (Exception e) {
			return 1;
		}
	}

	/**
	 * Drag and drop an element from source to destination
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param sourceElementName
	 * @param destinationElementName
	 * @return element
	 * @throws AutomationException
	 */
	public void dragAndDrop(WebDriver driver, String sourceElementName, String destinationElementName)
			throws AutomationException {
		try {
			WebElement sourceElement = getWebElement(driver, sourceElementName);
			WebElement destinationElement = getWebElement(driver, destinationElementName);
			Actions action = new Actions(driver);
			action.dragAndDrop(sourceElement, destinationElement);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Scroll to an element
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public void scrollToElement(WebDriver driver, String elementName) throws AutomationException {
		try {
			WebElement element = getWebElement(driver, elementName);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Scroll to the bottom of the web page
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void scrollToBottom(WebDriver driver) throws AutomationException {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Scroll to the top of the web page
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void scrollToTop(WebDriver driver) throws AutomationException {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * 
	 * Scroll the page up in web applications
	 * 
	 * @author sanoj
	 * @since 13-03-2021
	 * @param numberOfTimes
	 * @param
	 * @throws AutomationException
	 * 
	 */

	public void scrollWebPageUp(int numberOfTimes) throws AutomationException {
		int x = 1;

		try {
			Robot robot = new Robot();
			new UtilityActions().delay(2);
			while (x <= numberOfTimes) {
				// Simulate a mouse click
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

				// Simulate a key press
				robot.keyPress(KeyEvent.VK_PAGE_UP);
				robot.keyRelease(KeyEvent.VK_PAGE_UP);
				new UtilityActions().delay(2);
				x++;
			}
		} catch (AWTException e) {
			throw new AutomationException(e);
		}

	}

	/**
	 * 
	 * Scroll the page down in web applications
	 * 
	 * @author sanoj
	 * @since 13-03-2021
	 * @param numberOfTimes
	 * @param
	 * @throws AutomationException
	 * 
	 */

	public void scrollWebPageDown(int numberOfTimes) throws AutomationException {
		int x = 1;

		try {
			Robot robot = new Robot();
			new UtilityActions().delay(2);
			while (x <= numberOfTimes) {
				// Simulate a mouse click
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

				// Simulate a key press
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
				new UtilityActions().delay(2);
				x++;
			}
		} catch (AWTException e) {
			throw new AutomationException(e);
		}
	}

	/**
	 * Mouse move to an element
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void moveToElement(WebDriver driver, String elementName) throws AutomationException {
		try {
			Actions actions = new Actions(driver);
			WebElement element = getWebElement(driver, elementName);
			actions.moveToElement(element).perform();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Mouse move to an element based on the X and Y co-ordinates
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @param xOffset
	 * @param yOffset
	 * @throws AutomationException
	 */
	public void moveToElement(WebDriver driver, String elementName, int xOffset, int yOffset)
			throws AutomationException {
		try {
			Actions actions = new Actions(driver);
			WebElement element = getWebElement(driver, elementName);
			actions.moveToElement(element, xOffset, yOffset).perform();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Clear an input field
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void clearInputField(WebDriver driver, String elementName) throws AutomationException {
		try {
			WebElement element = getWebElement(driver, elementName);
			element.clear();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Copy and paste the value from one input field to another input field
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param sourceElementName
	 * @param destinationElementName
	 * @throws AutomationException
	 */
	public void copyAndPasteFromOneInputFieldToAnother(WebDriver driver, String sourceElementName,
			String destinationElementName) throws AutomationException {
		try {
			WebElement sourceElement = getWebElement(driver, sourceElementName);
			sourceElement.sendKeys(Keys.CONTROL + "a");
			sourceElement.sendKeys(Keys.CONTROL + "c");

			WebElement destinationElement = getWebElement(driver, destinationElementName);
			destinationElement.clear();
			destinationElement.sendKeys(Keys.CONTROL + "v");
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Select all and delete the value from input field
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void selectAndDeleteInputFieldValue(WebDriver driver, String elementName) throws AutomationException {
		try {
			WebElement sourceElement = getWebElement(driver, elementName);
			sourceElement.sendKeys(Keys.CONTROL + "a");
			sourceElement.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Select the value from the drop down using the visible text
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @param visibleText
	 * @throws AutomationException
	 */
	public void selectDropDownValueByVisibleText(WebDriver driver, String elementName, String visibleText)
			throws AutomationException {
		try {
			Select select = new Select(getWebElement(driver, elementName));
			select.selectByVisibleText(visibleText);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Select the value from the drop down using the index
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @param index
	 * @throws AutomationException
	 */
	public void selectDropDownValueByIndex(WebDriver driver, String elementName, int index) throws AutomationException {
		try {
			Select select = new Select(getWebElement(driver, elementName));
			select.selectByIndex(index);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Select the value from the drop down using value
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementName
	 * @param valueToSelect
	 * @throws AutomationException
	 */
	public void selectDropDownValueByValue(WebDriver driver, String elementName, String valueToSelect)
			throws AutomationException {
		try {
			Select select = new Select(getWebElement(driver, elementName));
			select.selectByValue(valueToSelect);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Release the pressed left mouse button at the current mouse location
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public void releaseMouse(WebDriver driver) throws AutomationException {
		try {
			Actions actions = new Actions(driver);
			actions.release();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Take a screenshot of the current screen and store into screenshots folder in
	 * the project structure
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @throws AutomationException
	 */
	public String takeScreenshot(WebDriver driver, String fileName) throws AutomationException {
		try {
			TakesScreenshot screenShot = ((TakesScreenshot) driver);
			File sourceFile = screenShot.getScreenshotAs(OutputType.FILE);
			String destination = System.getProperty("user.dir") + "/screenshots/" + fileName + "_" + getCurrentDate()
					+ "_" + "screenshot.jpg";
			File destinationFile = new File(destination);
			FileUtils.copyFile(sourceFile, destinationFile);
			return destination;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * 
	 * Set the size of the current window. This will change the outer window
	 * dimension, not just the view port
	 * 
	 * @author sanoj
	 * @since 13-03-2021
	 * @param driver
	 * @param width
	 * @param height
	 * @return isScreenRezised
	 * @throws AutomationException
	 * 
	 */

	public boolean setBrowserResolution(WebDriver driver, int width, int height) throws AutomationException {
		boolean isScreenRezised = false;
		try {
			if (driver != null) {
				driver.manage().window().setSize(new Dimension(width, height));
				isScreenRezised = true;
			}
		} catch (Exception lException) {
			throw new AutomationException(getExceptionMessage(), lException);
		}
		return isScreenRezised;
	}

	/**
	 * Get a random number between the lower-bound and upper-bound
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param lowerBound
	 * @param upperBound
	 * @throws AutomationException
	 */
	public int getRandomNumber(int lowerBound, int upperBound) throws AutomationException {
		try {
			random = new Random();
			int randomNum = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
			return randomNum;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Get a random number with the a number length mentioned
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param numberLength
	 * @throws AutomationException
	 */
	public int getRandomNumber(int numberLength) throws AutomationException {
		try {
			random = new Random();
			int randomNum = 0;
			boolean loop = true;
			while (loop) {
				randomNum = random.nextInt();
				if (Integer.toString(randomNum).length() == numberLength
						&& !Integer.toString(randomNum).startsWith("-")) {
					loop = false;
				}
			}
			return randomNum;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Get a random alphanumeric value with the string length mentioned
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param stringLength
	 * @throws AutomationException
	 */
	public String getRandomAlphanumeric(int stringLength) throws AutomationException {
		try {
			String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
			StringBuilder sb = new StringBuilder(stringLength);
			for (int i = 0; i < stringLength; i++) {
				int index = (int) (AlphaNumericString.length() * Math.random());
				sb.append(AlphaNumericString.charAt(index));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Get a random alphabets with the string length mentioned
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param stringLength
	 * @throws AutomationException
	 */
	public String getRandomAlphabets(int stringLength) throws AutomationException {
		try {
			String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz";
			StringBuilder sb = new StringBuilder(stringLength);
			for (int i = 0; i < stringLength; i++) {
				int index = (int) (AlphaNumericString.length() * Math.random());
				sb.append(AlphaNumericString.charAt(index));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Get current date
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @throws AutomationException
	 */
	public String getCurrentDate() throws AutomationException {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
			Date date = new Date();
			String filePathdate = dateFormat.format(date).toString();
			return filePathdate;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Get current date in the date format ddMMMyyyy
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @throws AutomationException
	 */
	public String getCurrentDateInFormatddMMMyyyy() throws AutomationException {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
			Date date = new Date();
			String filePathdate = dateFormat.format(date).toString();
			return filePathdate;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Get current date in the date format dd-MM-yyyy
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @throws AutomationException
	 */
	public String getCurrentDateInFormatddMMyyyy() throws AutomationException {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			String filePathdate = dateFormat.format(date).toString();
			return filePathdate;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Get the day from the current date
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @throws AutomationException
	 */
	public String getDayFromCurrentDate() throws AutomationException {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
			Date date = new Date();
			String filePathdate = dateFormat.format(date).toString();
			String day = filePathdate.substring(0, 2);
			return day;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Convert a double value to an Integer
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param doubleValue
	 * @throws AutomationException
	 */
	public int convertDoubleToInt(double doubleValue) throws AutomationException {
		try {
			int intValue = (int) doubleValue;
			return intValue;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Convert a float value to an Integer
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param floatValue
	 * @throws AutomationException
	 */
	public int convertFloatToInt(float floatValue) throws AutomationException {
		try {
			int intValue = (int) floatValue;
			return intValue;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Convert a string value to an Integer
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param stringValue
	 * @throws AutomationException
	 */
	public int convertStringToInt(String stringValue) throws AutomationException {
		try {
			int intValue = Integer.parseInt(stringValue);
			return intValue;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Convert a string value to a double value
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param stringValue
	 * @throws AutomationException
	 */
	public double convertStringToDouble(String stringValue) throws AutomationException {
		try {
			double doubleValue = Double.parseDouble(stringValue);
			return doubleValue;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Convert an Integer to a string value
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param intValue
	 * @throws AutomationException
	 */
	public String convertIntToString(int intValue) throws AutomationException {
		try {
			String stringValue = String.valueOf(intValue);
			return stringValue;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Convert a double value to a string value
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param doubleValue
	 * @throws AutomationException
	 */
	public String convertDoubleToString(double doubleValue) throws AutomationException {
		try {
			String stringValue = String.valueOf(doubleValue);
			return stringValue;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Convert a string value to a long value
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param doubleValue
	 * @throws AutomationException
	 */
	public long convertStringToLong(String stringValue) throws AutomationException {
		try {
			long longValue = Long.parseLong(stringValue);
			return longValue;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

}
