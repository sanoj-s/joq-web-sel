package com.snj.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.snj.base.AutomationEngine;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

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
	 * Test broken links in a web page. To achieve, users must be in the web page to
	 * be tested and call this method
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
