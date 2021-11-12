package com.snj.action;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.snj.base.AutomationEngine;
import com.snj.data.PropertyDataHandler;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;
import com.snj.utils.ObjectRepositoryHandler;

public class UtilityActions extends AutomationEngine {

	public WebDriverWait wait;
	public Random random;

	/**
	 * Method to wait for the element using the object mentioned in the Object
	 * Repository
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @return element
	 * @throws AutomationException
	 */
	public WebElement waitForElement(WebDriver driver, String elementKey) throws AutomationException {
		WebElement element = null;
		ObjectRepositoryHandler objRepository = new ObjectRepositoryHandler();
		PropertyDataHandler objProertyData = new PropertyDataHandler();
		try {
			long timeout = Long.parseLong(objProertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG,
					AutomationConstants.SHORT_LOADING));
			wait = new WebDriverWait(driver, timeout);
			By actualElement = objRepository.getElementByLocator(elementKey);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(actualElement));
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(" + element.getLocation().x + "," + element.getLocation().y + ")");
		} catch (Exception e) {
			throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementKey + "'");
		}
		return element;
	}

	/**
	 * Method to wait for the elements using the object mentioned in the Object
	 * Repository
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @return elements
	 * @throws AutomationException
	 */
	public List<WebElement> waitForElements(WebDriver driver, String elementKey) throws AutomationException {
		List<WebElement> elements;
		ObjectRepositoryHandler objRepository = new ObjectRepositoryHandler();
		PropertyDataHandler objProertyData = new PropertyDataHandler();
		try {
			if (elementKey != null) {
				long timeout = Long.parseLong(
						objProertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
				wait = new WebDriverWait(driver, timeout);
				By actualElement = objRepository.getElementByLocator(elementKey);
				wait.until(ExpectedConditions.presenceOfElementLocated(actualElement));
				elements = driver.findElements(actualElement);
			} else
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementKey + "'");
		} catch (Exception e) {
			throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementKey + "'");
		}
		return elements;
	}

	/**
	 * Method to wait for the element using the By elementlocator
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementLocator
	 * @return element
	 * @throws AutomationException
	 */
	public WebElement waitForElement(WebDriver driver, By elementLocator) throws AutomationException {
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
	 * Method to Wait for the element to get Visible
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementLocator
	 * @return isElementVisible
	 * @throws AutomationException
	 */
	public boolean waitForElementVisible(WebDriver driver, String elementKey) throws AutomationException {
		boolean isElementVisible = false;
		PropertyDataHandler objPropertyData = new PropertyDataHandler();
		ObjectRepositoryHandler objRepository = new ObjectRepositoryHandler();
		try {
			long timeout = Long.parseLong(
					objPropertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
			wait = new WebDriverWait(driver, timeout);
			By actualElement = objRepository.getElementByLocator(elementKey);
			wait.until(ExpectedConditions.visibilityOfElementLocated(actualElement));
			isElementVisible = true;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return isElementVisible;
	}

	/**
	 * Method to Wait for the element to get Invisible
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementKey
	 * @return isElementInVisible
	 * @throws AutomationException
	 */
	public boolean waitForElementInVisible(WebDriver driver, String elementKey) throws AutomationException {
		boolean isElementInVisible = false;
		PropertyDataHandler objPropertyData = new PropertyDataHandler();
		ObjectRepositoryHandler objRepository = new ObjectRepositoryHandler();
		try {
			long timeout = Long.parseLong(
					objPropertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
			wait = new WebDriverWait(driver, timeout);
			By actualElement = objRepository.getElementByLocator(elementKey);
			isElementInVisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(actualElement));
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return isElementInVisible;
	}

	/**
	 * Method to Wait for the element Text to be present
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementKey
	 * @return element
	 * @throws AutomationException
	 */
	public boolean waitForTextPresent(WebDriver driver, String elementKey, String expectedText)
			throws AutomationException {
		boolean isTextPresent = false;
		PropertyDataHandler objPropertyData = new PropertyDataHandler();
		ObjectRepositoryHandler objRepository = new ObjectRepositoryHandler();
		try {
			long timeout = Long.parseLong(
					objPropertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
			wait = new WebDriverWait(driver, timeout);
			By actualElement = objRepository.getElementByLocator(elementKey);
			isTextPresent = wait.until(ExpectedConditions.textToBePresentInElementLocated(actualElement, expectedText));
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return isTextPresent;
	}

	/**
	 * Method to get WebElement
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementKey
	 * @return element
	 * @throws AutomationException
	 */
	public WebElement getWebElement(String elementName) throws AutomationException {
		WebElement webElement = null;
		By byElement = null;
		try {
			PropertyDataHandler objPropertyData = new PropertyDataHandler();
			WebDriver driver = new AutomationEngine().getDriver();
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

			long timeout = Long.parseLong(
					objPropertyData.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
			wait = new WebDriverWait(driver, timeout);
			webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
			((JavascriptExecutor) driver).executeScript(
					"window.scrollTo(" + webElement.getLocation().x + "," + webElement.getLocation().y + ")");
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return webElement;
	}

	/**
	 * Method to Drag and Drop an element from source to destination
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementKey
	 * @return element
	 * @throws AutomationException
	 */
	public void dragAndDrop(WebDriver driver, String sourceElementExpression, String destinationElementExpression)
			throws AutomationException {
		try {
			WebElement sourceElement = waitForElement(driver, sourceElementExpression);
			WebElement destinationElement = waitForElement(driver, destinationElementExpression);
			Actions action = new Actions(driver);
			action.dragAndDrop(sourceElement, destinationElement);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Take a Screenshot
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
	 * Method to Scroll to the Element
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public void scrollToElement(WebDriver driver, String elementExpression) throws AutomationException {
		try {
			WebElement element = waitForElement(driver, elementExpression);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Scroll to the Bottom of the web page
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
	 * Method to Scroll to the Top of the web page
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
	 * Method to Move to the Web element
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public void moveToElement(WebDriver driver, String elementExpression) throws AutomationException {
		try {
			Actions actions = new Actions(driver);
			WebElement element = waitForElement(driver, elementExpression);
			actions.moveToElement(element).perform();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Move to the Web element based on the provided X and Y co-ordinates
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param xOffset
	 * @param yOffset
	 * @throws AutomationException
	 */
	public void moveToElementByXandY(WebDriver driver, String elementExpression, int xOffset, int yOffset)
			throws AutomationException {
		try {
			Actions actions = new Actions(driver);
			WebElement element = waitForElement(driver, elementExpression);
			actions.moveToElement(element, xOffset, yOffset).perform();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Get the X co-ordinate of the Web element
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public int getXcoordinate(WebDriver driver, String elementExpression) throws AutomationException {
		try {
			WebElement element = waitForElement(driver, elementExpression);
			Point point = element.getLocation();
			return point.getX();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Get the Y co-ordinate of the Web element
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public int getYcoordinate(WebDriver driver, String elementExpression) throws AutomationException {
		try {
			WebElement element = waitForElement(driver, elementExpression);
			Point point = element.getLocation();
			return point.getY();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Get the Count of the Elements from the Web Element List
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public int countOfElementsFromList(WebDriver driver, String elementExpression) throws AutomationException {
		try {
			List<WebElement> elements = waitForElements(driver, elementExpression);
			return elements.size();
		} catch (Exception e) {
			return 1;
		}
	}

	/**
	 * Method to Get the Count of the Elements from the Web Element List using Xpath
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public int countOfElementsFromListUsingXpath(WebDriver driver, String xpathExpression) throws AutomationException {
		try {
			List<WebElement> elements = driver.findElements(By.xpath(xpathExpression));
			return elements.size();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Method to Clear the input field (which is referred by the user from the
	 * Object Repository)
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public void clearInputField(WebDriver driver, String elementExpression) throws AutomationException {
		try {
			WebElement element = waitForElement(driver, elementExpression);
			element.clear();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Copy and Paste the value from one input field to another input
	 * field
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
			WebElement sourceElement = waitForElement(driver, sourceElementName);
			sourceElement.sendKeys(Keys.CONTROL + "a");
			sourceElement.sendKeys(Keys.CONTROL + "c");

			WebElement destinationElement = waitForElement(driver, destinationElementName);
			destinationElement.clear();
			destinationElement.sendKeys(Keys.CONTROL + "v");
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Delete the value from input field (which is referred by the user
	 * from the Object Repository)
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public void deleteInputFieldText(WebDriver driver, String elementExpression) throws AutomationException {
		try {
			WebElement sourceElement = waitForElement(driver, elementExpression);
			sourceElement.sendKeys(Keys.CONTROL + "a");
			sourceElement.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Get the Text of the Web Element
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public String getElementText(WebDriver driver, String elementExpression) throws AutomationException {
		String elementText = "";
		try {
			WebElement element = waitForElement(driver, elementExpression);
			elementText = element.getText();
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementText;
	}

	/**
	 * Method to Get the Attribute value of the web element
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param attributeName
	 * @throws AutomationException
	 */
	public String getElementAttributeValue(WebDriver driver, String elementExpression, String attributeName)
			throws AutomationException {
		String elementAttribute = "";
		try {
			WebElement element = waitForElement(driver, elementExpression);
			elementAttribute = element.getAttribute(attributeName);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementAttribute;
	}

	/**
	 * Method to Select the value from the drop down using the visible Text
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param visibleText
	 * @throws AutomationException
	 */
	public void selectDropDownValueByVisibleText(WebDriver driver, String elementExpression, String visibleText)
			throws AutomationException {
		try {
			Select select = new Select(waitForElement(driver, elementExpression));
			select.selectByVisibleText(visibleText);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Select the value from the drop down using the index
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param index
	 * @throws AutomationException
	 */
	public void selectDropDownValueByIndex(WebDriver driver, String elementExpression, int index)
			throws AutomationException {
		try {
			Select select = new Select(waitForElement(driver, elementExpression));
			select.selectByIndex(index);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Select the value from the drop down using value
	 * 
	 * @author sanojs
	 * @since 16-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param valueToSelect
	 * @throws AutomationException
	 */
	public void selectDropDownValueByValue(WebDriver driver, String elementExpression, String valueToSelect)
			throws AutomationException {
		try {
			Select select = new Select(waitForElement(driver, elementExpression));
			select.selectByValue(valueToSelect);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Release the depressed left mouse button at the current mouse
	 * location
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
	 * Method to GET a random number between the lowerBound and upperBound
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
	 * Method to GET a random number with the a number length mentioned
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param numberLength
	 * @throws AutomationException
	 */
	public String getRandomNumber(int numberLength) throws AutomationException {
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
			String randomNumber = Integer.toString(randomNum);
			return randomNumber;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Method to GET a random string value with the string length mentioned
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param stringLength
	 * @throws AutomationException
	 */
	public String getRandomString(int stringLength) throws AutomationException {
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
	 * Method to GET a random string which has only alphabets with the string length
	 * mentioned
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param stringLength
	 * @throws AutomationException
	 */
	public String getRandomStringOnlyAlphabets(int stringLength) throws AutomationException {
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
	 * Method to GET the current date
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
	 * Method to GET the current date in the date format ddMMMyyyy
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
	 * Method to GET a current date in the date format ddMMyyyy
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
	 * Method to GET the day from the current date
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
	 * Method to CONVERT a double value to an Integer
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
	 * Method to CONVERT a float value to an Integer
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
	 * Method to CONVERT a string value to an Integer
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
	 * Method to CONVERT a string value to a double value
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
	 * Method to CONVERT an Integer to a string value
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
	 * Method to CONVERT a double value to a string value
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
	 * Method to CONVERT a string value to a long value
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
