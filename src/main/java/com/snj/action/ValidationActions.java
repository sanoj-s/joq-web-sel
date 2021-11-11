package com.snj.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.snj.base.AutomationEngine;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

public class ValidationActions extends AutomationEngine {

	UtilityActions utilityActionHelper = new UtilityActions();

	/**
	 * Method to verify the element is present
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean isElementPresent(WebDriver driver, String elementName) throws AutomationException {
		boolean elementPresent = false;
		try {
			if (elementName != null) {
				if (utilityActionHelper.waitForElements(driver, elementName).size() != 0) {
					elementPresent = true;
				} else {
					elementPresent = false;
				}
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			elementPresent = false;
		}
		return elementPresent;
	}

	/**
	 * Method to verify the element is visible
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean isElementVisible(WebDriver driver, String elementName) throws AutomationException {
		boolean elementVisible = false;
		try {
			if (elementName != null) {
				WebElement element = utilityActionHelper.waitForElement(driver, elementName);
				if (element.isDisplayed()) {
					elementVisible = true;
				} else {
					elementVisible = false;
				}
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			elementVisible = false;
		}
		return elementVisible;
	}

	/**
	 * Method to verify the element is selected
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean isElementSelected(WebDriver driver, String elementName) throws AutomationException {
		boolean elementSelected = false;
		try {
			if (elementName != null) {
				if (utilityActionHelper.waitForElement(driver, elementName).isSelected()) {
					elementSelected = true;
				} else {
					elementSelected = false;
				}
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			elementSelected = false;
		}
		return elementSelected;
	}

	/**
	 * Method to verify the element is enabled
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean isElementEnabled(WebDriver driver, String elementName) throws AutomationException {
		boolean elementEnabled = false;
		try {
			if (elementName != null) {
				if (utilityActionHelper.waitForElement(driver, elementName).isEnabled()) {
					elementEnabled = true;
				} else {
					elementEnabled = false;
				}
			} else {
				throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
			}
		} catch (Exception e) {
			elementEnabled = false;
		}
		return elementEnabled;
	}

	/**
	 * Method to verify the element is displayed
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean isElementDisplayed(WebDriver driver, String elementName) throws AutomationException {
		boolean elementDisplayed = false;
		try {
			if (utilityActionHelper.waitForElement(driver, elementName).isDisplayed()) {
				elementDisplayed = true;
			} else {
				elementDisplayed = false;
			}

		} catch (Exception e) {
			elementDisplayed = false;
		}
		return elementDisplayed;
	}

	/**
	 * Method to verify the checkbox selected or not
	 * 
	 * @author sanojs
	 * @since 05/18/2021
	 * @param driver
	 * @param elementName
	 * @return
	 * @throws AutomationException
	 */
	public boolean isCheckboxSelected(WebDriver driver, String elementName) throws AutomationException {
		boolean isCheckboxSelected = false;
		try {
			if (driver != null) {
				WebElement element = utilityActionHelper.waitForElement(driver, elementName);
				if (element != null) {
					if (element.getAttribute("checked") != null) {
						if (element.getAttribute("checked").equals("true")) {
							isCheckboxSelected = true;
						}
					} else {
						isCheckboxSelected = false;
					}
				} else {
					throw new AutomationException(AutomationConstants.OBJECT_NOT_FOUND + "'" + elementName + "'");
				}
			}
		} catch (Exception e) {
			isCheckboxSelected = false;
		}
		return isCheckboxSelected;
	}

	/**
	 * Method to verify that the URL contains the element Text
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean verifyURLContains(WebDriver driver, String expectedText) throws AutomationException {
		boolean urlConatinsText = false;
		try {
			String actualURL = driver.getCurrentUrl();
			if (actualURL.contains(expectedText)) {
				urlConatinsText = true;
			} else {
				urlConatinsText = false;
				throw new AutomationException(expectedText + AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(urlConatinsText);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return urlConatinsText;
	}

	/**
	 * Method to verify that the Element is present
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean verifyElementPresent(WebDriver driver, String elementExpression) throws AutomationException {
		boolean elementPresent = false;
		try {
			int count = utilityActionHelper.waitForElements(driver, elementExpression).size();
			if (count != 0) {
				elementPresent = true;
			} else {
				elementPresent = false;
				throw new AutomationException(elementExpression + AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(elementPresent);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementPresent;
	}

	/**
	 * Method to verify that the Element is not present
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean verifyElementNotPresent(WebDriver driver, String elementExpression) throws AutomationException {
		boolean elementNotPresent = false;
		try {
			int count = utilityActionHelper.waitForElements(driver, elementExpression).size();
			if (count != 0) {
				elementNotPresent = true;
			} else {
				elementNotPresent = false;
				throw new AutomationException(elementExpression + AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(elementNotPresent);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementNotPresent;
	}

	/**
	 * Method to verify that the Element is visible
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean verifyElementVisible(WebDriver driver, String elementExpression) throws AutomationException {
		boolean elementVisible = false;
		try {
			if (utilityActionHelper.waitForElement(driver, elementExpression).isDisplayed()) {
				elementVisible = true;
			} else {
				elementVisible = false;
				throw new AutomationException(elementExpression + AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(elementVisible);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementVisible;
	}

	/**
	 * Method to verify that the Element is not visible
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean verifyElementNotVisible(WebDriver driver, String elementExpression) throws AutomationException {
		boolean elementNotVisible = false;
		try {
			if (utilityActionHelper.waitForElement(driver, elementExpression).isDisplayed()) {
				elementNotVisible = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			} else {
				elementNotVisible = true;
			}
			Assert.assertTrue(elementNotVisible);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementNotVisible;
	}

	/**
	 * Method to verify that the Element is selected
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean verifyElementSelected(WebDriver driver, String elementExpression) throws AutomationException {
		boolean elementSelected = false;
		try {
			if (utilityActionHelper.waitForElement(driver, elementExpression).isSelected()) {
				elementSelected = true;
			} else {
				elementSelected = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(elementSelected);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementSelected;
	}

	/**
	 * Method to verify that the Element is enabled
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean verifyElementEnabled(WebDriver driver, String elementExpression) throws AutomationException {
		boolean elementEnabled = false;
		try {
			if (utilityActionHelper.waitForElement(driver, elementExpression).isEnabled()) {
				elementEnabled = true;
			} else {
				elementEnabled = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(elementEnabled);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementEnabled;
	}

	/**
	 * Method to verify that the Element is not enabled
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean verifyElementNotEnabled(WebDriver driver, String elementExpression) throws AutomationException {
		boolean elementNotEnabled = false;
		try {
			if (utilityActionHelper.waitForElement(driver, elementExpression).isEnabled()) {
				elementNotEnabled = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			} else {
				elementNotEnabled = true;
			}
			Assert.assertTrue(elementNotEnabled);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementNotEnabled;
	}

	/**
	 * Method to verify that the Element is displayed
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean verifyElementDisplayed(WebDriver driver, String elementExpression) throws AutomationException {
		boolean elementDisplayed = false;
		try {
			if (utilityActionHelper.waitForElement(driver, elementExpression).isDisplayed()) {
				elementDisplayed = true;
			} else {
				elementDisplayed = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(elementDisplayed);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementDisplayed;
	}

	/**
	 * Method to verify that the Element is not displayed
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @throws AutomationException
	 */
	public boolean verifyElementNotDisplayed(WebDriver driver, String elementExpression) throws AutomationException {
		boolean elementNotDisplayed = false;
		try {
			if (utilityActionHelper.waitForElement(driver, elementExpression).isDisplayed()) {
				elementNotDisplayed = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			} else {
				elementNotDisplayed = true;
			}
			Assert.assertTrue(elementNotDisplayed);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementNotDisplayed;
	}

	/**
	 * Method to verify that the Element Text has the expected text
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param expectedText
	 * @throws AutomationException
	 */
	public boolean verifyElementText(WebDriver driver, String elementExpression, String expectedText)
			throws AutomationException {
		boolean textVerified = false;
		try {
			WebElement element = utilityActionHelper.waitForElement(driver, elementExpression);
			String actualText = element.getText();
			if (actualText.contentEquals(expectedText)) {
				textVerified = true;
			} else {
				textVerified = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(textVerified);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return textVerified;
	}

	/**
	 * Method to verify that the Element Attribute has the expected text
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param attributeName
	 * @param expectedText
	 * @throws AutomationException
	 */
	public boolean verifyElementAttributeHasText(WebDriver driver, String elementExpression, String attributeName,
			String expectedText) throws AutomationException {
		boolean textVerified = false;
		try {
			WebElement element = utilityActionHelper.waitForElement(driver, elementExpression);
			String actualAttributeValue = element.getAttribute(attributeName);
			if (actualAttributeValue.contentEquals(expectedText)) {
				textVerified = true;
			} else {
				textVerified = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(textVerified);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return textVerified;
	}

	/**
	 * Method to verify that the Element CSS value has the expected text
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param attributeName
	 * @param expectedText
	 * @throws AutomationException
	 */
	public boolean verifyElementHasCssValue(WebDriver driver, String elementExpression, String attributeName,
			String expectedText) throws AutomationException {
		boolean textVerified = false;
		try {
			WebElement element = utilityActionHelper.waitForElement(driver, elementExpression);
			String actualAttributeValue = element.getCssValue(attributeName);
			if (actualAttributeValue.contentEquals(expectedText)) {
				textVerified = true;
			} else {
				textVerified = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(textVerified);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return textVerified;
	}

	/**
	 * Method to verify that the Element text contains the expected text
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param expectedText
	 * @throws AutomationException
	 */
	public boolean verifyElementContainsText(WebDriver driver, String elementExpression, String expectedText)
			throws AutomationException {
		boolean textContains = false;
		try {
			WebElement element = utilityActionHelper.waitForElement(driver, elementExpression);
			String actualText = element.getText();
			if (actualText.contains(expectedText)) {
				textContains = true;
			} else {
				textContains = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(textContains);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return textContains;
	}

	/**
	 * Method to verify that the expected text is not in the Element text
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementExpression
	 * @param expectedText
	 * @throws AutomationException
	 */
	public boolean verifyElementHasNotText(WebDriver driver, String elementExpression, String expectedText)
			throws AutomationException {
		boolean elementHasNotText = false;
		try {
			WebElement element = utilityActionHelper.waitForElement(driver, elementExpression);
			String actualText = element.getText();
			if (actualText.contentEquals(expectedText)) {
				elementHasNotText = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			} else {
				elementHasNotText = true;
			}
			Assert.assertTrue(elementHasNotText);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return elementHasNotText;
	}

	/**
	 * Method to verify that the actual number is greater than the expected text
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param actualNumber
	 * @param expectedNumber
	 * @throws AutomationException
	 */
	public boolean verifyGreaterThan(int actualNumber, int expectedNumber) throws AutomationException {
		boolean numberGreaterThan = false;
		try {
			if (actualNumber > expectedNumber) {
				numberGreaterThan = true;
			} else {
				numberGreaterThan = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(numberGreaterThan);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return numberGreaterThan;
	}

	/**
	 * Method to verify that the actual number is less than the expected text
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param actualNumber
	 * @param expectedNumber
	 * @throws AutomationException
	 */
	public boolean verifyLessThan(int actualNumber, int expectedNumber) throws AutomationException {
		boolean numberLessThan = false;
		try {
			if (actualNumber < expectedNumber) {
				numberLessThan = true;
			} else {
				numberLessThan = false;
				throw new AutomationException(AutomationConstants.VERIFICATION_FAILED);
			}
			Assert.assertTrue(numberLessThan);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
		return numberLessThan;
	}

	/**
	 * Checking boolean condition is working as expected or not, based on that it
	 * will show the message that given as parameter. This method skip the
	 * consecutive steps
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param condition
	 * @param message
	 * @throws AutomationException
	 */
	public void assertTrue(boolean condition, String message) throws AutomationException {
		try {
			Assert.assertTrue((boolean) condition, (String) message);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Checking boolean condition is working as expected or not, based on that it
	 * will show the message that given as parameter. This method skip the
	 * consecutive steps
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param condition
	 * @param message
	 * @throws AutomationException
	 */
	public void assertFalse(boolean condition, String message) throws AutomationException {
		try {
			Assert.assertFalse((boolean) condition, (String) message);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Check whether the actual and expected results are equal or not, based on that
	 * it will show the message that given as parameter. This method skip the
	 * consecutive steps
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param actual
	 * @param expected
	 * @param message
	 * @throws AutomationException
	 */
	public void assertEquals(String actual, String expected, String message) throws AutomationException {
		try {
			Assert.assertEquals((String) actual, (String) expected, (String) message);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Check whether the actual and expected results are equal or not for boolean,
	 * based on that it will show the message that given as parameter. This method
	 * skip the consecutive steps
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param actual
	 * @param expected
	 * @param message
	 * @throws AutomationException
	 */
	public void assertEquals(boolean actual, boolean expected, String message) throws AutomationException {
		try {
			Assert.assertEquals((boolean) actual, (boolean) expected, (String) message);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Check whether the actual and expected results are not equal or not for
	 * String, based on that it will show the message that given as parameter. This
	 * method skip the consecutive steps
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param actual
	 * @param expected
	 * @param message
	 * @throws AutomationException
	 */
	public void assertNotEquals(String actual, String expected, String message) throws AutomationException {
		try {
			Assert.assertNotEquals((String) actual, (String) expected, (String) message);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

}
