package com.snj.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.snj.base.AutomationEngine;
import com.snj.exception.AutomationException;

public class ValidationActions extends AutomationEngine {

	UtilityActions utilityActionHelper = new UtilityActions();

	/**
	 * Verify that the object is present and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public boolean verifyElementPresent(WebDriver driver, String elementName) throws AutomationException {
		boolean elementPresent = false;
		try {
			int count = utilityActionHelper.waitForElements(driver, elementName).size();
			if (count != 0) {
				elementPresent = true;
			} else {
				elementPresent = false;
			}
		} catch (Exception e) {
			elementPresent = false;
		}
		return elementPresent;
	}

	/**
	 * Verify that the object is present
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementPresent(WebDriver driver, String elementName, String messageOnFailure)
			throws AutomationException {
		boolean elementPresent = false;
		try {
			int count = utilityActionHelper.waitForElements(driver, elementName).size();
			if (count != 0) {
				elementPresent = true;
			} else {
				elementPresent = false;
			}
			Assert.assertTrue(elementPresent, messageOnFailure);
		} catch (Exception e) {
			Assert.assertTrue(elementPresent, messageOnFailure);
		}
	}

	/**
	 * Verify that the object is not present
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementNotPresent(WebDriver driver, String elementName, String messageOnFailure)
			throws AutomationException {
		boolean elementNotPresent = false;
		try {
			int count = utilityActionHelper.waitForElements(driver, elementName).size();
			if (count != 0) {
				elementNotPresent = true;
			} else {
				elementNotPresent = false;
			}
			Assert.assertFalse(elementNotPresent, messageOnFailure);
		} catch (Exception e) {
			Assert.assertFalse(elementNotPresent, messageOnFailure);
		}
	}

	/**
	 * Verify that the object is visible and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public boolean verifyElementVisible(WebDriver driver, String elementName) throws AutomationException {
		boolean elementVisible = false;
		try {
			if (utilityActionHelper.getWebElement(driver, elementName).isDisplayed()) {
				elementVisible = true;
			} else {
				elementVisible = false;
			}
		} catch (Exception e) {
			elementVisible = false;
		}
		return elementVisible;
	}

	/**
	 * Verify that the object is visible
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void verifyElementVisible(WebDriver driver, String elementName, String messageOnFailure)
			throws AutomationException {
		boolean elementVisible = false;
		try {
			if (utilityActionHelper.getWebElement(driver, elementName).isDisplayed()) {
				elementVisible = true;
			} else {
				elementVisible = false;
			}
			Assert.assertTrue(elementVisible, messageOnFailure);
		} catch (Exception e) {
			Assert.assertTrue(elementVisible, messageOnFailure);
		}
	}

	/**
	 * Verify that the object is not visible
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void verifyElementNotVisible(WebDriver driver, String elementName, String messageOnFailure)
			throws AutomationException {
		boolean elementNotVisible = false;
		try {
			if (utilityActionHelper.getWebElement(driver, elementName).isDisplayed()) {
				elementNotVisible = true;
			} else {
				elementNotVisible = false;
			}
			Assert.assertFalse(elementNotVisible, messageOnFailure);
		} catch (Exception e) {
			Assert.assertFalse(elementNotVisible, messageOnFailure);
		}
	}

	/**
	 * Verify that the object is enabled and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public boolean verifyElementEnabled(WebDriver driver, String elementName) throws AutomationException {
		boolean elementEnabled = false;
		try {
			if (utilityActionHelper.getWebElement(driver, elementName).isEnabled()) {
				elementEnabled = true;
			} else {
				elementEnabled = false;
			}
		} catch (Exception e) {
			elementEnabled = false;
		}
		return elementEnabled;
	}

	/**
	 * Verify that the object is enabled
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementEnabled(WebDriver driver, String elementName, String messageOnFailure)
			throws AutomationException {
		boolean elementEnabled = false;
		try {
			if (utilityActionHelper.getWebElement(driver, elementName).isEnabled()) {
				elementEnabled = true;
			} else {
				elementEnabled = false;
			}
			Assert.assertTrue(elementEnabled, messageOnFailure);
		} catch (Exception e) {
			Assert.assertTrue(elementEnabled, messageOnFailure);
		}
	}

	/**
	 * Verify that the object is not enabled
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementNotEnabled(WebDriver driver, String elementName, String messageOnFailure)
			throws AutomationException {
		boolean elementNotEnabled = false;
		try {
			if (utilityActionHelper.getWebElement(driver, elementName).isEnabled()) {
				elementNotEnabled = true;
			} else {
				elementNotEnabled = false;
			}
			Assert.assertFalse(elementNotEnabled, messageOnFailure);
		} catch (Exception e) {
			Assert.assertFalse(elementNotEnabled, messageOnFailure);
		}
	}

	/**
	 * Verify that the object is selected and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public boolean verifyElementSelected(WebDriver driver, String elementName) throws AutomationException {
		boolean elementSelected = false;
		try {
			if (utilityActionHelper.getWebElement(driver, elementName).isSelected()) {
				elementSelected = true;
			} else {
				elementSelected = false;
			}
		} catch (Exception e) {
			elementSelected = false;
		}
		return elementSelected;
	}

	/**
	 * Verify that the object is selected
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementSelected(WebDriver driver, String elementName, String messageOnFailure)
			throws AutomationException {
		boolean elementSelected = false;
		try {
			if (utilityActionHelper.getWebElement(driver, elementName).isSelected()) {
				elementSelected = true;
			} else {
				elementSelected = false;
			}
			Assert.assertTrue(elementSelected, messageOnFailure);
		} catch (Exception e) {
			Assert.assertTrue(elementSelected, messageOnFailure);
		}
	}

	/**
	 * Verify that the object is not selected
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementNotSelected(WebDriver driver, String elementName, String messageOnFailure)
			throws AutomationException {
		boolean elementNotSelected = false;
		try {
			if (utilityActionHelper.getWebElement(driver, elementName).isSelected()) {
				elementNotSelected = true;
			} else {
				elementNotSelected = false;
			}
			Assert.assertFalse(elementNotSelected, messageOnFailure);
		} catch (Exception e) {
			Assert.assertFalse(elementNotSelected, messageOnFailure);
		}
	}

	/**
	 * Verify that the checkbox selected and return true or false
	 * 
	 * @author sanojs
	 * @since 05/18/2021
	 * @param driver
	 * @param elementName
	 * @return
	 * @throws AutomationException
	 */
	public boolean verifyCheckboxSelected(WebDriver driver, String elementName) throws AutomationException {
		boolean isCheckboxSelected = false;
		try {
			if (driver != null) {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				if (element != null) {
					if (element.getAttribute("checked") != null) {
						if (element.getAttribute("checked").equals("true")) {
							isCheckboxSelected = true;
						}
					} else {
						isCheckboxSelected = false;
					}
				}
			}
		} catch (Exception e) {
			isCheckboxSelected = false;
		}
		return isCheckboxSelected;
	}

	/**
	 * Verify that the checkbox selected
	 * 
	 * @author sanojs
	 * @since 05/18/2021
	 * @param driver
	 * @param elementName
	 * @param messageOnFailure
	 * @return
	 * @throws AutomationException
	 */
	public void verifyCheckboxSelected(WebDriver driver, String elementName, String messageOnFailure)
			throws AutomationException {
		boolean isCheckboxSelected = false;
		try {
			if (driver != null) {
				WebElement element = utilityActionHelper.getWebElement(driver, elementName);
				if (element != null) {
					if (element.getAttribute("checked") != null) {
						if (element.getAttribute("checked").equals("true")) {
							isCheckboxSelected = true;
						}
					} else {
						isCheckboxSelected = false;
					}
					Assert.assertTrue(isCheckboxSelected, messageOnFailure);
				}
			}
		} catch (Exception e) {
			Assert.assertTrue(isCheckboxSelected, messageOnFailure);
		}
	}

	/**
	 * Verify that text or label present and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param expectedText
	 * @throws AutomationException
	 */
	public boolean verifyElementText(WebDriver driver, String elementName, String expectedText)
			throws AutomationException {
		boolean textVerified = false;
		try {
			WebElement element = utilityActionHelper.getWebElement(driver, elementName);
			String actualText = element.getText();
			if (actualText.contentEquals(expectedText)) {
				textVerified = true;
			} else {
				textVerified = false;
			}
		} catch (Exception e) {
			textVerified = false;
		}
		return textVerified;
	}

	/**
	 * Verify that text or label present
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param expectedText
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementText(WebDriver driver, String elementName, String expectedText, String messageOnFailure)
			throws AutomationException {
		boolean textVerified = false;
		try {
			WebElement element = utilityActionHelper.getWebElement(driver, elementName);
			String actualText = element.getText();
			if (actualText.contentEquals(expectedText)) {
				textVerified = true;
			} else {
				textVerified = false;
			}
			Assert.assertTrue(textVerified, messageOnFailure);
		} catch (Exception e) {
			Assert.assertTrue(textVerified, messageOnFailure);
		}
	}

	/**
	 * Verify that the object contains text or label and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param expectedText
	 * @throws AutomationException
	 */
	public boolean verifyElementContainsText(WebDriver driver, String elementName, String expectedText)
			throws AutomationException {
		boolean textContains = false;
		try {
			WebElement element = utilityActionHelper.getWebElement(driver, elementName);
			String actualText = element.getText();
			if (actualText.contains(expectedText)) {
				textContains = true;
			} else {
				textContains = false;
			}
		} catch (Exception e) {
			textContains = false;
		}
		return textContains;
	}

	/**
	 * Verify that the object contains text or label
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param expectedText
	 * @throws AutomationException
	 */
	public void verifyElementContainsText(WebDriver driver, String elementName, String expectedText,
			String messageOnFailure) throws AutomationException {
		boolean textContains = false;
		try {
			WebElement element = utilityActionHelper.getWebElement(driver, elementName);
			String actualText = element.getText();
			if (actualText.contains(expectedText)) {
				textContains = true;
			} else {
				textContains = false;
			}
			Assert.assertTrue(textContains, messageOnFailure);
		} catch (Exception e) {
			Assert.assertTrue(textContains, messageOnFailure);
		}
	}

	/**
	 * Verify that the object attribute has text or label present and return true or
	 * false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param attributeName
	 * @param expectedText
	 * @throws AutomationException
	 */
	public boolean verifyElementAttributeHasText(WebDriver driver, String elementName, String attributeName,
			String expectedText) throws AutomationException {
		boolean textVerified = false;
		try {
			WebElement element = utilityActionHelper.getWebElement(driver, elementName);
			String actualAttributeValue = element.getAttribute(attributeName);
			if (actualAttributeValue.contentEquals(expectedText)) {
				textVerified = true;
			} else {
				textVerified = false;
			}
		} catch (Exception e) {
			textVerified = false;
		}
		return textVerified;
	}

	/**
	 * Verify that the object attribute has text or label present
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param attributeName
	 * @param expectedText
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementAttributeHasText(WebDriver driver, String elementName, String attributeName,
			String expectedText, String messageOnFailure) throws AutomationException {
		boolean textVerified = false;
		try {
			WebElement element = utilityActionHelper.getWebElement(driver, elementName);
			String actualAttributeValue = element.getAttribute(attributeName);
			if (actualAttributeValue.contentEquals(expectedText)) {
				textVerified = true;
			} else {
				textVerified = false;
			}
			Assert.assertTrue(textVerified, messageOnFailure);
		} catch (Exception e) {
			Assert.assertTrue(textVerified, messageOnFailure);
		}
	}

	/**
	 * Verify that the URL contains text or label and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param expectedText
	 * @throws AutomationException
	 */
	public boolean verifyURLContainsText(WebDriver driver, String expectedText) throws AutomationException {
		boolean urlConatinsText = false;
		try {
			String actualURL = driver.getCurrentUrl();
			if (actualURL.contains(expectedText)) {
				urlConatinsText = true;
			} else {
				urlConatinsText = false;
			}
		} catch (Exception e) {
			urlConatinsText = false;
		}
		return urlConatinsText;
	}

	/**
	 * Verify that the URL contains text or label and return true or false
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param driver
	 * @param expectedText
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyURLContainsText(WebDriver driver, String expectedText, String messageOnFailure)
			throws AutomationException {
		boolean urlConatinsText = false;
		try {
			String actualURL = driver.getCurrentUrl();
			if (actualURL.contains(expectedText)) {
				urlConatinsText = true;
			} else {
				urlConatinsText = false;
			}
			Assert.assertTrue(urlConatinsText, messageOnFailure);
		} catch (Exception e) {
			Assert.assertTrue(urlConatinsText, messageOnFailure);
		}
	}

	/**
	 * Verify boolean condition is working as expected or not, based on that it will
	 * show the message that given as parameter
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param condition
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyTrue(boolean condition, String messageOnFailure) throws AutomationException {
		try {
			Assert.assertTrue((boolean) condition, (String) messageOnFailure);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Verify boolean condition is working as expected or not, based on that it will
	 * show the message that given as parameter
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param condition
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyFalse(boolean condition, String messageOnFailure) throws AutomationException {
		try {
			Assert.assertFalse((boolean) condition, (String) messageOnFailure);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Verify whether the actual and expected results are equal or not, based on
	 * that it will show the message that given as parameter
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param actual
	 * @param expected
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyEquals(String actual, String expected, String messageOnFailure) throws AutomationException {
		try {
			Assert.assertEquals((String) actual, (String) expected, (String) messageOnFailure);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Verify whether the actual and expected results are equal or not for boolean,
	 * based on that it will show the message that given as parameter
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param actual
	 * @param expected
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyEquals(boolean actual, boolean expected, String messageOnFailure) throws AutomationException {
		try {
			Assert.assertEquals((boolean) actual, (boolean) expected, (String) messageOnFailure);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Verify whether the actual and expected results are not equal or not for
	 * String, based on that it will show the message that given as parameter
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param actual
	 * @param expected
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyNotEquals(String actual, String expected, String messageOnFailure) throws AutomationException {
		try {
			Assert.assertNotEquals((String) actual, (String) expected, (String) messageOnFailure);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}

	/**
	 * Verify whether the actual and expected results are equal or not for boolean,
	 * based on that it will show the message that given as parameter
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param actual
	 * @param expected
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyNotEquals(boolean actual, boolean expected, String messageOnFailure) throws AutomationException {
		try {
			Assert.assertNotEquals((boolean) actual, (boolean) expected, (String) messageOnFailure);
		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);
		}
	}
}
