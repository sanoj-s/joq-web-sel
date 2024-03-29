package com.joq.keywords;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.joq.base.AutomationEngine;
import com.joq.exception.AutomationException;
import com.joq.utils.AutomationConstants;

public class UIActions extends AutomationEngine {

	Utilities utilityActionHelper = new Utilities();

	/**
	 * Click on an object
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @modified 04-07-2022
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void tap(WebDriver driver, String elementName) throws AutomationException {
		try {
			if (elementName != null) {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				element.click();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			try {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				Actions builder = new Actions(driver);
				Action action = builder.moveToElement(element).click().build();
				action.perform();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementName);
			} catch (Exception ex) {
				System.out.println("==========================================================");
				System.out.println("Failed to click on the " + elementName);
				throw new AutomationException(getExceptionMessage(), ex);
			}
		}
	}

	/**
	 * Click on an object based on RelativeLocator direction
	 * 
	 * @author sanoj
	 * @since 08-12-2021
	 * @modified 04-07-2022
	 * @param driver
	 * @param tagName
	 * @param direction:  left, right, above, below, near
	 * @param elementName
	 * @throws AutomationException
	 */
	public void tap(WebDriver driver, String tagName, String direction, String elementName) throws AutomationException {
		try {
			if (elementName != null) {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				WebElement elementToDoAction = new Utilities().getRelativeElement(driver, tagName, direction, element);
				elementToDoAction.click();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementToDoAction);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception lException) {
			try {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				WebElement elementToDoAction = new Utilities().getRelativeElement(driver, tagName, direction, element);
				Actions builder = new Actions(driver);
				Action action = builder.moveToElement(elementToDoAction).click().build();
				action.perform();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementToDoAction);
			} catch (Exception ex) {
				throw new AutomationException(getExceptionMessage(), ex);
			}
		}
	}

	/**
	 * Right click on an object
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void rightClick(WebDriver driver, String elementName) throws AutomationException {
		try {
			if (elementName != null) {
				Actions action = new Actions(driver);
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				action.contextClick(element).perform();
				System.out.println("==========================================================");
				System.out.println("Successfully right clicked on " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Double click on an object
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void doubleTap(WebDriver driver, String elementName) throws AutomationException {
		try {
			if (elementName != null) {
				Actions action = new Actions(driver);
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				action.doubleClick(element).perform();
				System.out.println("==========================================================");
				System.out.println("Successfully double clicked on " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			try {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
				executor.executeScript("arguments[0].click();", element);
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementName);
			} catch (Exception ex) {
				throw new AutomationException(getExceptionMessage(), ex);
			}

		}
	}

	/**
	 * Multiple click on an object
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param clickCount
	 * @throws AutomationException
	 */
	public void multiTap(WebDriver driver, String elementName, int clickCount) throws AutomationException {
		try {
			if (elementName != null) {
				DataHandler propertyObj = new DataHandler();
				long timeout = Long.parseLong(
						propertyObj.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				int i = 0;
				while (i < clickCount) {
					element.click();
					Thread.sleep(1000);
					wait.until(ExpectedConditions.elementToBeClickable(element));
				}
				System.out.println("==========================================================");
				System.out.println("Successfully multi-clicked on " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Click on an object based on the X and Y Coordinates of an object
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param xOffset
	 * @param yOffset
	 * @throws AutomationException
	 */
	public void tapUsingCoordinates(WebDriver driver, int xOffset, int yOffset) throws AutomationException {
		try {
			Actions action = new Actions(driver);
			action.moveByOffset(xOffset, yOffset).click().build().perform();
			System.out.println("==========================================================");
			System.out.println("Successfully clicked on " + xOffset + " and " + yOffset + " locations");
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Click on the object based on the locator type - xpath, id, classname,
	 * linktext, partial linktext, css selector, tag name
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param locatorType
	 * @param elementName
	 * @throws AutomationException
	 */
	public void tapUsingLocatorType(WebDriver driver, String locatorType, String elementName)
			throws AutomationException {
		try {
			By elementLocator = null;
			if (elementName != null) {
				switch (locatorType.toLowerCase()) {
				case "xpath":
					elementLocator = By.xpath(elementName);
					break;
				case "id":
					elementLocator = By.id(elementName);
					break;
				case "name":
					elementLocator = By.name(elementName);
					break;
				case "css":
				case "css selector":
				case "cssselector":
					elementLocator = By.cssSelector(elementName);
					break;
				case "class":
				case "classname":
				case "class name":
					elementLocator = By.className(elementName);
					break;
				case "link text":
				case "linktext":
					elementLocator = By.linkText(elementName);
					break;
				case "partial link text":
				case "partiallinktext":
				case "partial linktext":
					elementLocator = By.partialLinkText(elementName);
					break;
				case "tag":
				case "tagname":
				case "tag name":
					elementLocator = By.tagName(elementName);
					break;
				default:
					break;
				}
				WebElement element = utilityActionHelper.getWebElement(driver, elementLocator);
				element.click();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Tap TAB key from system keyboard
	 * 
	 * @author sanoj
	 * @since 13-03-2021
	 * @param numberOfTimes
	 * @throws AWTException
	 * @throws AutomationException
	 */
	public void tapTabKey(int numberOfTimes) throws AWTException, AutomationException {
		try {
			for (int i = 1; i <= numberOfTimes; i++) {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				new Utilities().delay(2);
			}
		} catch (Exception lException) {
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Tap TAB key on an object
	 * 
	 * @author sanoj
	 * @since 13-03-2021
	 * @param driver
	 * @param elementName
	 * @throws AWTException
	 * @throws AutomationException
	 */
	public void tapTabKey(WebDriver driver, String elementName) throws AWTException, AutomationException {
		try {
			if (driver != null) {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				if (element != null) {
					element.sendKeys(Keys.TAB);
				} else {
					throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
				}
			}
		} catch (Exception lException) {
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Tap TAB and ENTER key on an object
	 * 
	 * @author sanoj
	 * @since 13-03-2021
	 * @param driver
	 * @param elementName
	 * @throws AWTException
	 * @throws AutomationException
	 */
	public void tapTabAndEnter(WebDriver driver, String elementName) throws AWTException, AutomationException {
		try {
			if (driver != null) {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				if (element != null) {
					element.sendKeys(Keys.TAB);
					Thread.sleep(1500);
					element.sendKeys(Keys.ENTER);
				} else {
					throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
				}
			}
		} catch (Exception lException) {
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Enter values to an input field
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param typeValue
	 * @throws AutomationException
	 */
	public void type(WebDriver driver, String elementName, String typeValue) throws AutomationException {
		try {
			if (driver != null && elementName != null) {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				element.sendKeys(typeValue);
				System.out.println("==========================================================");
				System.out.println("Data " + typeValue + " successfully entered into " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			try {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0]. value='" + typeValue + "';", element);
				System.out.println("==========================================================");
				System.out.println("Data " + typeValue + " successfully entered into " + elementName);
			} catch (Exception ex) {
				System.out.println("==========================================================");
				System.out.println("Data " + typeValue + " failed to enter into " + elementName);
				throw new AutomationException(getExceptionMessage(), ex);
			}
		}
	}

	/**
	 * Enter values to an input field based on Keys
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param typeValue
	 * @throws AutomationException
	 */
	public void type(WebDriver driver, String elementName, Keys typeValue) throws AutomationException {
		try {
			if (driver != null && elementName != null) {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				element.sendKeys(typeValue);
				System.out.println("==========================================================");
				System.out.println("Key " + typeValue.name() + " applied on " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Enter values to an input field based on RelativeLocator direction
	 * 
	 * @author sanoj
	 * @since 08-12-2021
	 * @param driver
	 * @param tagName
	 * @param direction:  left, right, above, below, near
	 * @param elementName
	 * @param inputText
	 * @throws AutomationException
	 */
	public void type(WebDriver driver, String tagName, String direction, String elementName, String inputText)
			throws AutomationException {
		try {
			if (elementName != null) {
				final WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				WebElement elementToDoAction = new Utilities().getRelativeElement(driver, tagName, direction, element);
				elementToDoAction.sendKeys(inputText);
				System.out.println("==========================================================");
				System.out.println("Data " + inputText + " successfully entered into " + elementToDoAction);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (final Exception lException) {
			try {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				WebElement elementToDoAction = new Utilities().getRelativeElement(driver, tagName, direction, element);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0]. value='" + inputText + "';", elementToDoAction);
				System.out.println("==========================================================");
				System.out.println("Data " + inputText + " successfully entered into " + elementToDoAction);
			} catch (Exception ex) {
				throw new AutomationException(getExceptionMessage(), ex);
			}
		}
	}

	/**
	 * Clear an input field and enter values to that input field
	 * 
	 * @author sanojs
	 * @since 15-0-2021
	 * @param driver
	 * @param expressionPath
	 * @param typeValue
	 * @throws AutomationException
	 */
	public void clearAndType(WebDriver driver, String elementName, String typeValue) throws AutomationException {
		try {
			if (elementName != null) {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				element.clear();
				element.sendKeys(typeValue);
				System.out.println("==========================================================");
				System.out.println("Data " + typeValue + " successfully entered into " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			try {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				element.clear();
				executor.executeScript("arguments[0]. value='" + typeValue + "';", element);
				System.out.println("==========================================================");
				System.out.println("Data " + typeValue + " successfully entered into " + elementName);
			} catch (Exception ex) {
				throw new AutomationException(getExceptionMessage(), ex);
			}
		}
	}

	/**
	 * Enter values to an input field based on the locator type - xpath, id,
	 * classname, linktext, partial linktext, css selector, tag name
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param locatorType
	 * @param elementName
	 * @param typeValue
	 * @throws AutomationException
	 */
	public void typeUsingLocatorType(WebDriver driver, String locatorType, String elementName, String typeValue)
			throws AutomationException {
		try {
			By elementLocator = null;
			if (elementName != null) {
				switch (locatorType.toLowerCase()) {
				case "xpath":
					elementLocator = By.xpath(elementName);
					break;
				case "id":
					elementLocator = By.id(elementName);
					break;
				case "name":
					elementLocator = By.name(elementName);
					break;
				case "css":
				case "css selector":
				case "cssselector":
					elementLocator = By.cssSelector(elementName);
					break;
				case "class":
				case "classname":
				case "class name":
					elementLocator = By.className(elementName);
					break;
				case "link text":
				case "linktext":
					elementLocator = By.linkText(elementName);
					break;
				case "partial link text":
				case "partiallinktext":
				case "partial linktext":
					elementLocator = By.partialLinkText(elementName);
					break;
				case "tag":
				case "tagname":
				case "tag name":
					elementLocator = By.tagName(elementName);
					break;
				default:
					break;
				}
				WebElement element = utilityActionHelper.getWebElement(driver, elementLocator);
				element.sendKeys(typeValue);
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

}
