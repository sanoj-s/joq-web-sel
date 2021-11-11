package com.snj.action;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.snj.base.AutomationEngine;
import com.snj.data.PropertyDataHandler;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

public class UIActions extends AutomationEngine {

	UtilityActions utilityActionHelper = new UtilityActions();

	/**
	 * Method to CLICK on the object (which is referred by the user from the Object
	 * Repository)
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void click(WebDriver driver, String elementName) throws AutomationException {
		try {
			if (elementName != null) {
				WebElement element = utilityActionHelper.waitForElement(driver, elementName);
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
	 * Method to CLICK on the object using JS(which is referred by the user from the
	 * Object Repository)
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void clickUsingJS(WebDriver driver, String elementName) throws AutomationException {
		try {
			if (elementName != null) {
				WebElement element = utilityActionHelper.waitForElement(driver, elementName);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
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
	 * Method to Double Click on the object (which is referred by the user from the
	 * Object Repository)
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void doubleClick(WebDriver driver, String elementName) throws AutomationException {
		try {
			if (elementName != null) {
				Actions action = new Actions(driver);
				WebElement element = utilityActionHelper.waitForElement(driver, elementName);
				action.doubleClick(element).perform();
				System.out.println("==========================================================");
				System.out.println("Successfully double clicked on " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to Right Click on the object (which is referred by the user from the
	 * Object Repository)
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
				WebElement element = utilityActionHelper.waitForElement(driver, elementName);
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
	 * Method to MULTI CLICK on the object (which is referred by the user from the
	 * Object Repository)
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param clickCount
	 * @throws AutomationException
	 */
	public void multiClick(WebDriver driver, String elementName, int clickCount) throws AutomationException {
		try {
			if (elementName != null) {
				PropertyDataHandler propertyObj = new PropertyDataHandler();
				long timeout = Long.parseLong(
						propertyObj.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG, "SHORT_LOADING"));
				WebDriverWait wait = new WebDriverWait(driver, timeout);
				WebElement element = utilityActionHelper.waitForElement(driver, elementName);
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
	 * Method to CLICK on the object based on the X and Y Co-ordinates.
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param xOffset
	 * @param yOffset
	 * @throws AutomationException
	 */
	public void clickOnXandY(WebDriver driver, int xOffset, int yOffset) throws AutomationException {
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
	 * Method to CLICK on the object based on the locator - xpath.
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementXpath
	 * @throws AutomationException
	 */
	public void clickByXpath(WebDriver driver, String elementXpath) throws AutomationException {
		try {
			if (elementXpath != null) {
				By elementLocator = By.xpath(elementXpath);
				WebElement element = utilityActionHelper.waitForElement(driver, elementLocator);
				element.click();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementXpath);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementXpath + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to CLICK on the object based on the locator - name.
	 * 
	 * @author sanojs
	 * @since 15-0-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void clickByName(WebDriver driver, String elementName) throws AutomationException {
		try {
			if (elementName != null) {
				By elementLocator = By.name(elementName);
				WebElement element = utilityActionHelper.waitForElement(driver, elementLocator);
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
	 * Method to CLICK on the object based on the locator - id.
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementId
	 * @throws AutomationException
	 */
	public void clickById(WebDriver driver, String elementId) throws AutomationException {
		try {
			if (elementId != null) {
				By elementLocator = By.id(elementId);
				WebElement element = utilityActionHelper.waitForElement(driver, elementLocator);
				element.click();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementId);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementId + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to CLICK on the object based on the locator - linkText.
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementlinkText
	 * @throws AutomationException
	 */
	public void clickBylinkText(WebDriver driver, String elementlinkText) throws AutomationException {
		try {
			if (elementlinkText != null) {
				By elementLocator = By.linkText(elementlinkText);
				WebElement element = utilityActionHelper.waitForElement(driver, elementLocator);
				element.click();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementlinkText);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementlinkText + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to CLICK on the object based on the locator - PartialLinkText.
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementpartialLinkText
	 * @throws AutomationException
	 */
	public void clickByPartialLinkText(WebDriver driver, String elementpartialLinkText) throws AutomationException {
		try {
			if (elementpartialLinkText != null) {
				By elementLocator = By.partialLinkText(elementpartialLinkText);
				WebElement element = utilityActionHelper.waitForElement(driver, elementLocator);
				element.click();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementpartialLinkText);
			} else {
				throw new AutomationException(
						AutomationConstants.OBJECT_NOT_FOUND + "'" + elementpartialLinkText + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to CLICK on the object based on the locator - CSSselector.
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementcssSelector
	 * @throws AutomationException
	 */
	public void clickByCssSelector(WebDriver driver, String elementcssSelector) throws AutomationException {
		try {
			if (elementcssSelector != null) {
				By elementLocator = By.cssSelector(elementcssSelector);
				WebElement element = utilityActionHelper.waitForElement(driver, elementLocator);
				element.click();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementcssSelector);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementcssSelector + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to CLICK on the object based on the locator - TagName.
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementtagName
	 * @throws AutomationException
	 */
	public void clickByTagName(WebDriver driver, String elementtagName) throws AutomationException {
		try {
			if (elementtagName != null) {
				By elementLocator = By.tagName(elementtagName);
				WebElement element = utilityActionHelper.waitForElement(driver, elementLocator);
				element.click();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementtagName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementtagName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to CLICK on the object based on the locator - ClassName.
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementClassName
	 * @throws AutomationException
	 */
	public void clickByClassName(WebDriver driver, String elementClassName) throws AutomationException {
		try {
			if (elementClassName != null) {
				By elementLocator = By.className(elementClassName);
				WebElement element = utilityActionHelper.waitForElement(driver, elementLocator);
				element.click();
				System.out.println("==========================================================");
				System.out.println("Successfully clicked on " + elementClassName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementClassName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to set text in the input field (which is referred by the user from the
	 * Object Repository)
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param inputText
	 * @throws AutomationException
	 */
	public void type(WebDriver driver, String elementName, String inputText) throws AutomationException {
		try {
			if (driver != null && elementName != null) {
				WebElement element = utilityActionHelper.waitForElement(driver, elementName);
				element.sendKeys(inputText);
				System.out.println("==========================================================");
				System.out.println("Data " + inputText + " successfully entered into " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to set key in the input field (which is referred by the user from the
	 * Object Repository)
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param inputText
	 * @throws AutomationException
	 */
	public void type(WebDriver driver, String elementName, Keys inputText) throws AutomationException {
		try {
			if (driver != null && elementName != null) {
				WebElement element = utilityActionHelper.waitForElement(driver, elementName);
				element.sendKeys(inputText);
				System.out.println("==========================================================");
				System.out.println("Data " + inputText + " successfully entered into " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Method to clear the input field and then set text to the same input field
	 * (which is referred by the user from the Object Repository)
	 * 
	 * @author sanojs
	 * @since 15-0-2021
	 * @param driver
	 * @param expressionPath
	 * @param inputText
	 * @throws AutomationException
	 */
	public void clearAndType(WebDriver driver, String elementName, String inputText) throws AutomationException {
		try {
			if (elementName != null) {
				WebElement element = utilityActionHelper.waitForElement(driver, elementName);
				element.clear();
				element.sendKeys(inputText);
				System.out.println("==========================================================");
				System.out.println("Data " + inputText + " successfully entered into " + elementName);
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}
}
