package com.joq.keywords;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;

import com.joq.base.AutomationEngine;
import com.joq.exception.AutomationException;
import com.joq.utils.AutomationConstants;

public class Validations extends AutomationEngine {

	Utilities utilityActionHelper = new Utilities();

	/**
	 * Verify the visibility of an element on the page
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementVisible(WebDriver driver, String elementName, String messageOnFailure)
			throws AutomationException {
		boolean isElementVisible = false;
		try {
			int count = utilityActionHelper.waitForElements(driver, elementName).size();
			if (count != 0) {
				isElementVisible = true;
			} else {
				isElementVisible = false;
			}
			Assert.assertTrue(isElementVisible, messageOnFailure);
		} catch (Exception e) {
			Assert.assertTrue(isElementVisible, messageOnFailure);
		}
	}

	/**
	 * Verify that the object is visible based on RelativeLocator direction
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-12-2021
	 * @param driver
	 * @param tagName
	 * @param direction:left,  right, above, below, near
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementVisible(WebDriver driver, String tagName, String direction, String elementName,
			String messageOnFailure) throws AutomationException {
		boolean elementVisible = false;
		try {
			WebElement element = utilityActionHelper.getWebElement(driver, elementName);
			WebElement elementToDoAction = new Utilities().getRelativeElement(driver, tagName, direction, element);
			if (elementToDoAction.isDisplayed()) {
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
	 * Verify the visibility of an element is not on the page
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementNotVisible(WebDriver driver, String elementName, String messageOnFailure)
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
	 * Verify that the the element is displayed on the page
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void verifyElementDisplayed(WebDriver driver, String elementName, String messageOnFailure)
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
	 * Verify that the element is not displayed on the page
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @throws AutomationException
	 */
	public void verifyElementNotDisplayed(WebDriver driver, String elementName, String messageOnFailure)
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
	 * Verify that the element is enabled
	 * 
	 * @author sanoj.swaminathan
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
	 * Verify that the object is enabled based on RelativeLocator direction
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-12-2021
	 * @param driver
	 * @param tagName
	 * @param direction:left,  right, above, below, near
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementEnabled(WebDriver driver, String tagName, String direction, String elementName,
			String messageOnFailure) throws AutomationException {
		boolean elementVisible = false;
		try {
			WebElement element = utilityActionHelper.getWebElement(driver, elementName);
			WebElement elementToDoAction = new Utilities().getRelativeElement(driver, tagName, direction, element);
			if (elementToDoAction.isEnabled()) {
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
	 * Verify that the element is not enabled
	 * 
	 * @author sanoj.swaminathan
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
	 * Verify that the element is selected
	 * 
	 * @author sanoj.swaminathan
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
	 * Verify that the object is selected based on RelativeLocator direction
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-12-2021
	 * @param driver
	 * @param tagName
	 * @param direction:left,  right, above, below, near
	 * @param elementName
	 * @param messageOnFailure
	 * @throws AutomationException
	 */
	public void verifyElementSelected(WebDriver driver, String tagName, String direction, String elementName,
			String messageOnFailure) throws AutomationException {
		boolean elementVisible = false;
		try {
			WebElement element = utilityActionHelper.getWebElement(driver, elementName);
			WebElement elementToDoAction = new Utilities().getRelativeElement(driver, tagName, direction, element);
			if (elementToDoAction.isSelected()) {
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
	 * Verify that the element is not selected
	 * 
	 * @author sanoj.swaminathan
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
	 * Verify that the checkbox selected
	 * 
	 * @author sanoj.swaminathan
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
	 * Verify that the element text or label is visible
	 * 
	 * @author sanoj.swaminathan
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
	 * Verify that the element contains text or label
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param driver
	 * @param elementName
	 * @param expectedText
	 * @throws AutomationException
	 */
	public void verifyElementTextContains(WebDriver driver, String elementName, String expectedText,
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
	 * Verify that the element attribute has text or label visible
	 * 
	 * @author sanoj.swaminathan
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
	 * Verify that the URL contains text or label
	 * 
	 * @author sanoj.swaminathan
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
	 * To compare and verify the JSON files
	 * 
	 * @author sanoj.swaminathan
	 * @since 10-10-2022
	 * @param expectedJSONPath
	 * @param actualJSONPath
	 */
	public void verifyJSONFileContent(String expectedJSONPath, String actualJSONPath) {
		try {
			String expectedJson = FileUtils.readFileToString(new File(expectedJSONPath), "utf-8");
			String actualJson = FileUtils.readFileToString(new File(actualJSONPath), "utf-8");
			JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verify the file downloaded or not
	 * 
	 * @author sanoj.swaminathan
	 * @since 19-08-2022
	 * @param downloadPath
	 * @param fileName
	 * @param messageOnFailure
	 * @return
	 */
	public void verifyFileDownloaded(String downloadPath, String fileName, String messageOnFailure) {
		boolean isFileDownloaded = false;
		try {
			File dir = new File(downloadPath);
			File[] dirContents = dir.listFiles();
			for (int i = 0; i < dirContents.length; i++) {
				if (dirContents[i].getName().contains(fileName)) {
					dirContents[i].delete();
					isFileDownloaded = true;
					Assert.assertTrue(isFileDownloaded, messageOnFailure);
				}
			}
		} catch (Exception e) {
			Assert.assertTrue(isFileDownloaded, messageOnFailure);
		}
	}

	/**
	 * Test broken links in a web page. To achieve, users must be in the web page to
	 * be tested and call this method
	 * 
	 * @author sanoj.swaminathan
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

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(59));

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
	 * Verify whether the actual and expected results are equal or not, based on
	 * that it will show the message that given as parameter
	 * 
	 * @author sanoj.swaminathan
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
	 * @author sanoj.swaminathan
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
	 * @author sanoj.swaminathan
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
	 * @author sanoj.swaminathan
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

	/**
	 * Capture the current screen and compare it with expected image file
	 * 
	 * @author sanoj.swaminathan
	 * @since 01-05-2023
	 * @param driver
	 * @param scenario
	 * @param expectedImageFilePath
	 * @param featureName
	 * @throws AutomationException
	 * 
	 */
	@SuppressWarnings("unused")
	public String compareImages(WebDriver driver, String scenario, String expectedImageFilePath, String featureName)
			throws AutomationException {
		String matchpercentage = null;
		try {
			BufferedImage image;
			int width = 0;
			int height = 0;
			int[][] clr;
			BufferedImage images;
			int widthe = 0;
			int heighte = 0;
			int[][] clre;
			double start = System.currentTimeMillis();
			utilityActionHelper.delay(5);

			File outputfile;
			try {
				outputfile = ((TakesScreenshot) new Augmenter().augment(driver)).getScreenshotAs(OutputType.FILE);
				if (!outputfile.canRead() || !outputfile.isFile()) {
					throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_FAILED_TO_GET_SCREEN);
				}
				image = ImageIO.read(outputfile);
				width = image.getWidth(null);
				height = image.getHeight(null);
				clr = new int[width][height];
			} catch (Exception e) {
				throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_FAILED_TO_GET_SCREEN);
			}

			try {
				File inFile = new File(expectedImageFilePath).getAbsoluteFile();
				if (!inFile.canRead() || !inFile.isFile()) {
					throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_INPUT_IMAGE_NOT_FOUND);
				}
				images = ImageIO.read(inFile);
				widthe = images.getWidth(null);
				heighte = images.getHeight(null);
				clre = new int[widthe][heighte];
			} catch (Exception e) {
				throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_INPUT_IMAGE_NOT_FOUND);
			}

			int smw = 0;
			int smh = 0;
			int p = 0;
			// Calculating the smallest value among width and height
			if (width > widthe) {
				smw = widthe;
			} else {
				smw = width;
			}
			if (height > heighte) {
				smh = heighte;
			} else {
				smh = height;
			}
			// Checking the number of pixels similarity
			for (int a = 0; a < smw; a++) {
				for (int b = 0; b < smh; b++) {
					clre[a][b] = images.getRGB(a, b);
					clr[a][b] = image.getRGB(a, b);
					if (clr[a][b] == clre[a][b]) {
						p = p + 1;
					}
				}
			}
			float w, h = 0;
			if (width > widthe) {
				w = width;
			} else {
				w = widthe;
			}
			if (height > heighte) {
				h = height;
			} else {
				h = heighte;
			}
			float s = (smw * smh);
			// Calculating the percentage
			float x = (100 * p) / s;
			matchpercentage = String.valueOf(new DecimalFormat("#").format(x)) + "%";

			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
			Date date = new Date();
			String dateTime = dateFormat.format(date).toString();

			double stop = System.currentTimeMillis();
			String timeTakenForComparison = String.valueOf((stop - start) / 1000);

			// Track the comparison details to the report
			trackDetailsToReport(scenario, expectedImageFilePath.toString(), matchpercentage, timeTakenForComparison,
					dateTime, featureName);

		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);

		}
		return matchpercentage;
	}

	/**
	 * Compares two images that send by the user and will return the percentage
	 * value for assertion
	 * 
	 * @author sanoj.swaminathan
	 * @since 01-05-2023
	 * @param expectedImage
	 * @param actualImage
	 * @throws AutomationException
	 * 
	 */
	@SuppressWarnings("unused")
	public String compareImages(String expectedImage, String actualImage) throws AutomationException {
		String matchpercentage = null;
		try {
			BufferedImage image;
			int width = 0;
			int height = 0;
			int[][] clr;
			BufferedImage images;
			int widthe = 0;
			int heighte = 0;
			int[][] clre;
			File outFile;
			try {
				outFile = new File(actualImage).getAbsoluteFile();
				;
				if (!outFile.canRead() || !outFile.isFile()) {
					throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_OUTPUT_IMAGE_NOT_FOUND);
				}
				image = ImageIO.read(outFile);
				width = image.getWidth(null);
				height = image.getHeight(null);
				clr = new int[width][height];
			} catch (Exception e) {
				throw (new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_OUTPUT_IMAGE_NOT_FOUND));
			}

			File inFile;
			try {
				inFile = new File(expectedImage).getAbsoluteFile();
				;
				if (!inFile.canRead() || !inFile.isFile()) {
					throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_INPUT_IMAGE_NOT_FOUND);
				}
				images = ImageIO.read(inFile);
				widthe = images.getWidth(null);
				heighte = images.getHeight(null);
				clre = new int[widthe][heighte];
			} catch (Exception e) {
				throw new AutomationException(AutomationConstants.EXCEPTION_MESSAGE_INPUT_IMAGE_NOT_FOUND);
			}

			int smw = 0;
			int smh = 0;
			int p = 0;
			// Calculating the smallest value among width and height
			if (width > widthe) {
				smw = widthe;
			} else {
				smw = width;
			}
			if (height > heighte) {
				smh = heighte;
			} else {
				smh = height;
			}
			// Checking the number of pixels similarity
			for (int a = 0; a < smw; a++) {
				for (int b = 0; b < smh; b++) {
					clre[a][b] = images.getRGB(a, b);
					clr[a][b] = image.getRGB(a, b);
					if (clr[a][b] == clre[a][b]) {
						p = p + 1;
					}
				}
			}

			float w, h = 0;
			if (width > widthe) {
				w = width;
			} else {
				w = widthe;
			}
			if (height > heighte) {
				h = height;
			} else {
				h = heighte;
			}
			float s = (smw * smh);
			// Calculating the percentage
			float x = (100 * p) / s;
			matchpercentage = String.valueOf(new DecimalFormat("#").format(x)) + "%";

		} catch (Exception lException) {
			lException.printStackTrace();
			throw new AutomationException(getExceptionMessage(), lException);

		}
		return matchpercentage;
	}

	/**
	 * Method to track the image comparison details and generate excel report
	 * 
	 * @since 12/01/2023
	 * @param scenario
	 * @param expectedImage
	 * @param percentMatch
	 * @param timeTakenForComparison
	 * @param dateTime
	 * @param featureName
	 * @throws IOException
	 */
	private void trackDetailsToReport(String scenario, String expectedImage, String percentMatch,
			String timeTakenForComparison, String dateTime, String featureName) throws IOException {

		// Creating file object of existing excel file
		if (!new File(System.getProperty("user.dir") + "\\Reports").exists()) {
			(new File(System.getProperty("user.dir") + "\\Reports")).mkdir();
		}
		if (!new File(System.getProperty("user.dir") + "\\Reports\\Image_Comparision\\").exists()) {
			new File(new File(System.getProperty("user.dir")), "\\Reports\\Image_Comparision\\").mkdirs();
		}
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		LocalDateTime dateNow = LocalDateTime.now();
		String dateValue = dateFormat.format(dateNow);
		String filePath = System.getProperty("user.dir") + "\\Reports\\Image_Comparision\\Image_Comparision_Report_"
				+ featureName + "_" + dateValue + ".xlsx";

		File excelFile = new File(filePath);
		OutputStream fileOut = null;
		Sheet sheet;
		File fileName;

		Workbook wb = new XSSFWorkbook();
		if (!excelFile.exists()) {
			fileName = new File(filePath);
			fileOut = new FileOutputStream(fileName);
			sheet = wb.createSheet("Sheet1");

			// Create headers and set style
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Scenario");
			header.createCell(1).setCellValue("Expected Image");
			header.createCell(2).setCellValue("% Match");
			header.createCell(3).setCellValue("Time Taken for Comparison (in seconds)");
			header.createCell(4).setCellValue("Date Time");
			header.createCell(5).setCellValue("Status");
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setBold(true);
			style.setFont(font);
			style.setWrapText(true);
			sheet.setColumnWidth(0, 70 * 256);
			sheet.setColumnWidth(1, 50 * 256);
			sheet.setColumnWidth(2, 15 * 256);
			sheet.setColumnWidth(3, 25 * 256);
			sheet.setColumnWidth(4, 15 * 256);
			sheet.setColumnWidth(5, 10 * 256);
			for (int j = 0; j <= 5; j++) {
				header.getCell(j).setCellStyle(style);
				CellStyle cellHeaderStyle = header.getCell(j).getCellStyle();
				cellHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
				header.getCell(j).setCellStyle(cellHeaderStyle);
			}
			wb.write(fileOut);
			System.out.println("Report has been created successfully.");
		}
		wb.close();

		// New records to update in the report
		Object[][] newdataLists = { { scenario, expectedImage, percentMatch, timeTakenForComparison, dateTime } };
		try {
			// Creating input stream
			FileInputStream inputStream = new FileInputStream(filePath);

			// Creating workbook from input stream
			Workbook workbook = WorkbookFactory.create(inputStream);

			// Reading first sheet of excel file
			sheet = workbook.getSheetAt(0);

			// Getting the count of existing records
			int rowCount = sheet.getLastRowNum();

			// Iterating new data to update
			for (Object[] data : newdataLists) {

				// Creating new row from the next row count
				Row row = sheet.createRow(++rowCount);

				int columnCount = 0;

				// Iterating data informations
				for (Object info : data) {

					// Creating new cell and setting the value
					Cell cell = row.createCell(columnCount++);
					if (info instanceof String) {
						cell.setCellValue((String) info);
					} else if (info instanceof Integer) {
						cell.setCellValue((Integer) info);
					}
				}
				// Setting the status and also setting the color style
				for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
					CellStyle style = workbook.createCellStyle();
					Font font = workbook.createFont();
					font.setBold(true);
					DataFormatter df = new DataFormatter();
					String actualPercentage = df.formatCellValue(sheet.getRow(i).getCell(2));
					Cell cell = row.createCell(5);
					if (!actualPercentage.equals("100%")) {
						font.setColor(HSSFColorPredefined.RED.getIndex());
						style.setFont(font);
						cell.setCellStyle(style);
						cell.setCellValue((String) "FAIL");
					} else {
						font.setColor(HSSFColorPredefined.GREEN.getIndex());
						style.setFont(font);
						cell.setCellStyle(style);
						cell.setCellValue((String) "PASS");
					}
				}
			}
			// Close input stream
			inputStream.close();

			// Crating output stream and writing the updated workbook
			FileOutputStream os = new FileOutputStream(filePath);
			workbook.write(os);

			// Close the workbook and output stream
			workbook.close();
			os.close();

			System.out.println("Report has been updated successfully.");

		} catch (EncryptedDocumentException | IOException e) {
			System.err.println("Exception while updating an existing report.");
			e.printStackTrace();
		}
	}
}
