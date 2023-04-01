package com.snj.reporting;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ui.Align;
import org.jfree.data.general.DefaultPieDataset;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.snj.base.AutomationEngine;
import com.snj.exception.AutomationException;
import com.snj.keywords.DataHandler;
import com.snj.keywords.Utilities;
import com.snj.utils.AutomationConstants;
import com.snj.utils.AutomationMail;

public class AutomationReport implements ITestListener, IExecutionListener {
	private Document document = null;
	PdfWriter writer = null;
	PdfWriter emailwriter = null;
	PdfPTable successTable = null, failTable = null, skipTable = null, chartTable = null, exceptionTable = null,
			platformValuesTable = null;
	BaseColor mainHeaderColor = new BaseColor(9, 0, 132);
	BaseColor subHeaderColor = new BaseColor(245, 249, 253);
	BaseColor tableBorderColor = new BaseColor(89, 89, 89);
	float tableBorderWidth = 1f;

	int successCount = 0, failureCount = 0, skippedCount = 0, testCount = 0;
	public static int mailCountIncrement = 0;
	double executionTime = 0;
	Image noImage = null;
	String sendMail = "";
	public String testExecutionEnvironments;
	Future<?> future;
	public WebDriver driverForClient = null;
	boolean platformvalue = false;
	public static String suiteFileName = "";
	public static ArrayList<String> testClassNameWithDate = new ArrayList<>();
	public static ArrayList<String> testName = new ArrayList<String>();
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	DataHandler dataHandler = new DataHandler();

	public AutomationReport() {
		this.document = new Document();
	}

	/**
	 * This method will invoke when an execution begins (if we include
	 * AutomationReporter.class) Also collects list of 'Tests' from TestNG xml file.
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @modified 01-04-2023
	 * @param context
	 * 
	 */
	public void onStart(ITestContext context) {
		try {
			sendMail = dataHandler.getProperty(AutomationConstants.AUTOMATION_FRAMEWORK_CONFIG,
					AutomationConstants.NEED_MAIL_REPORT);
		} catch (AutomationException lException) {

			lException.printStackTrace();
		}
		try {
			XmlSuite suiteName = context.getCurrentXmlTest().getSuite();
			List<XmlTest> testNames = suiteName.getTests();
			testCount = testNames.size();
			mailCountIncrement = mailCountIncrement + 1;
			suiteFileName = suiteName.getFileName();
		} catch (Exception lException) {
			lException.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
		try {
			new File(new File(System.getProperty("user.dir")), AutomationConstants.EMAIL_REPORT_FOLDER).mkdirs();
			new File(new File(System.getProperty("user.dir")), AutomationConstants.REPORT_FOLDER_PDF).mkdirs();

			File dir = new File(new File(System.getProperty("user.dir")), AutomationConstants.EMAIL_REPORT_FOLDER);
			for (File file : dir.listFiles()) {
				file.delete();
			}

			DateFormat dateFormatt = new SimpleDateFormat("dd_MM_yyyy");
			DateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
			Date date = new Date();
			String currdate = dateFormatt.format(date);
			String currtime = timeFormat.format(date);
			String finaldatetime = currdate + "_" + currtime;

			emailwriter = PdfWriter.getInstance(this.document, new FileOutputStream(System.getProperty("user.dir")
					+ AutomationConstants.EMAIL_REPORT_FOLDER + context.getName() + "_" + finaldatetime + ".pdf"));

			String currentTestNameWithDate = ((ITestContext) context).getCurrentXmlTest().getName() + "_"
					+ dateFormat.format(new Date()) + ".pdf";

			testClassNameWithDate.add(currentTestNameWithDate);
			String currentTestName = ((ITestContext) context).getCurrentXmlTest().getName();
			testName.add(currentTestName);

			// Setting up HTML Report
			String projectName, testExecutionEnvironment, currentTestNameWithDatehtml;
			projectName = dataHandler.getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG,
					AutomationConstants.PROJECT_NAME);

			currentTestNameWithDatehtml = context.getName() + "_" + finaldatetime + ".html";

			testExecutionEnvironment = dataHandler.getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG,
					AutomationConstants.TEST_ENVIRONMENT);
			sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")
					+ AutomationConstants.REPORT_FOLDER_HTML + currentTestNameWithDatehtml);
			XmlTest xmlTest = context.getCurrentXmlTest();
			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			if (testExecutionEnvironment.equals("")) {
				testExecutionEnvironment = "QA";
			}
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			if (xmlTest.getParameter("browserName") != null) {
				extent.setSystemInfo("Browser Name", xmlTest.getParameter("browserName").toString());
			}
			extent.setSystemInfo("Test Environment", testExecutionEnvironment);
			extent.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
			extent.setSystemInfo("IP address", InetAddress.getLocalHost().getHostAddress());
			sparkReporter.config().setDocumentTitle("Test Automation");
			sparkReporter.config().setReportName("Automation Report for " + projectName);
			sparkReporter.config().setTheme(Theme.DARK);
			sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Setting up PDF report
		document = createHeaderWithLogos(this.document);
		try {
			document = createHeaderWithProjectNameAndDate(this.document);
		} catch (AutomationException e) {
			e.printStackTrace();
		}
		System.out.println("Welcome to Automation Execution......");
	}

	/**
	 * This method will invoke for each test starts
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @param result
	 * 
	 */
	@Override
	public void onTestStart(ITestResult result) {

		// Adding test case name to HTML report
		String testName = result.getMethod().getMethodName();
		test = extent.createTest(testName);
	}

	/**
	 * This method will invoke when test case pass
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @modified 01-04-2023
	 * @param result
	 * 
	 */
	public void onTestSuccess(ITestResult result) {
		successCount++;
		if (future != null) {
			future.cancel(true);
		}
		if (successTable == null) {
			successTable = createHeaderTableTemplate(successTable, AutomationConstants.PASSED_TESTS);
		}
		addCellValueInEachTable(result, successTable);
		if (platformvalue != true) {
			getEnvironmentDetailsForPDFReport(result);
		}
		// Adding PASS test cases to HTML report
		test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
		System.out.println("********************************************");
		System.out.println("TEST CASE: " + result.getName() + " IS PASS");
		System.out.println("********************************************");
	}

	/**
	 * This method will invoke when test case fails
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @modified 01-04-2023
	 * @param result
	 * 
	 */

	public void onTestFailure(ITestResult result) {
		// Adding FAIL cases with reason to HTML report
		WebDriver driver = null;
		Object currentClass = result.getInstance();
		try {
			driver = ((AutomationEngine) currentClass).getDriver();
		} catch (Exception e) {
		}
		test.log(Status.FAIL,
				MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
		test.fail(result.getThrowable());
		test.fail("Please find the screenshot below");

		String failureScreen = null;
		if (driver != null) {
			try {
				failureScreen = new Utilities().takeScreenshot(driver, result.getName());
				test.addScreenCaptureFromPath(failureScreen, result.getName());
			} catch (AutomationException e) {
			}
		}
		System.out.println("********************************************");
		System.out.println("TEST CASE: " + result.getName() + " IS FAIL");
		System.out.println("********************************************");

		failureCount++;
		if (future != null) {
			future.cancel(true);
		}
		if (this.failTable == null) {
			failTable = createHeaderTableTemplate(failTable, AutomationConstants.FAILED_TESTS);
		}
		PdfPCell cell = addCellValueInEachTable(result, failTable);
		if (platformvalue != true) {
			getEnvironmentDetailsForPDFReport(result);
		}

		// Capture the defect screenshot and add to PDF report
		if (driver != null) {
			driver = captureExceptionAndScreenshotInPDFReport(result, cell, failureScreen);
		}
	}

	/**
	 * This method will invoke when test case skips
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @modified 01-04-2023
	 * @param result
	 * 
	 */

	public void onTestSkipped(ITestResult result) {
		// Adding skipped test into HTML report
		test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
		test.skip(result.getThrowable());
		System.out.println("********************************************");
		System.out.println("TEST CASE: " + result.getName() + " IS SKIPPED");
		System.out.println("********************************************");

		skippedCount++;

		if (future != null) {
			future.cancel(true);
		}
		if (this.skipTable == null) {
			skipTable = createHeaderTableTemplate(skipTable, AutomationConstants.SKIPPED_TESTS);
		}
		addCellValueInEachTable(result, skipTable);
		if (platformvalue != true) {
			getEnvironmentDetailsForPDFReport(result);
		}
	}

	/**
	 * This method will invoke when an execution ends
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @param context
	 * 
	 */
	public void onFinish(ITestContext context) {

		// Create execution details and graph for PDF report
		createExecutionDetailsAndGraphSection();

		File srcFile = new File(new File(System.getProperty("user.dir")), AutomationConstants.EMAIL_REPORT_FOLDER);
		File destFile = new File(new File(System.getProperty("user.dir")), AutomationConstants.REPORT_FOLDER_PDF);
		try {
			FileUtils.copyDirectory(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Flushing the HTML report
		extent.flush();
		DateFormat dateFormatt = new SimpleDateFormat("dd_MM_yyyy");
		DateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
		Date date = new Date();
		String currdate = dateFormatt.format(date);
		String currtime = timeFormat.format(date);
		String finaldatetime = currdate + "_" + currtime;
		String currentTestNameWithDatehtml = context.getName() + "_" + finaldatetime + ".html";
		String reportPath = System.getProperty("user.dir") + AutomationConstants.REPORT_FOLDER_HTML;
		String actualReportPath = reportPath + "index.html";
		new File(actualReportPath).renameTo(new File(reportPath + "Automation_Report_" + currentTestNameWithDatehtml));
		System.out.println("********************************************");
		System.out.println("TEST CASE EXECUTION COMPLETED");
		System.out.println("********************************************");
	}

	/**
	 * This method will invoke on every test execution finish
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * 
	 */

	@Override
	public void onExecutionFinish() {
		deleteCorruptedFile(AutomationConstants.REPORT_FOLDER_PDF);
		System.out.println("info: [Forwarding newSession on session to delete corrupted files]");

		try {
			if (sendMail.equalsIgnoreCase("")) {
				sendMail = "No";
			}
			if (sendMail != null && !sendMail.equals("") && sendMail.equalsIgnoreCase("YES")) {
				if (mailCountIncrement == testCount) {
					new AutomationMail().sendMailReport();
				}
			}
		} catch (Exception ex) {
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	@Override
	public void onExecutionStart() {

	}

	/**
	 * Method to create header logos in the PDF report
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @modified 01-04-2023
	 * @param document
	 * @return
	 * 
	 */

	private Document createHeaderWithLogos(Document document) {
		this.document.open();
		try {
			PdfPTable tb = new PdfPTable(new float[] { .75f, .75f });
			tb.setWidthPercentage(100);
			tb.setSpacingBefore(15f);
			Image app_icon = null;
			try {
				app_icon = Image.getInstance(ClassLoader.getSystemResource(AutomationConstants.APP_ICON_PATH));

			} catch (Exception lException) {
				System.out.println(AutomationConstants.EXCEPTION_MESSAGE_APP_ICON_FAILS);
			}
			PdfPCell cell = new PdfPCell();
			if (app_icon != null)
				cell = new PdfPCell(app_icon);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			tb.addCell(cell);
			this.document.add(tb);
			tb.setSpacingAfter(10f);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * Method to add project name and execution date at header section of PDF report
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @param document
	 * @return
	 * @throws AutomationException
	 * 
	 */

	private Document createHeaderWithProjectNameAndDate(Document document) throws AutomationException {
		try {
			String projectName = null;
			try {
				projectName = dataHandler.getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG,
						AutomationConstants.PROJECT_NAME);

				if (projectName == null || projectName.equals("")) {
					this.document.add(new Paragraph("Test Summary",
							FontFactory.getFont(FontFactory.TIMES_ROMAN, 26, Font.BOLD, new BaseColor(9, 0, 132))));
				} else {
					this.document.add(new Paragraph("Test Summary for " + projectName,
							FontFactory.getFont(FontFactory.TIMES_ROMAN, 26, Font.BOLD, new BaseColor(132, 91, 166))));
				}
				DateFormat dff = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm:ss z");
				this.document.add(new Paragraph(dff.format(new Date()).toString(),
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(128, 128, 128))));
			} catch (AutomationException lException) {
				new AutomationException("Exception in PDF Header section");
			}

		} catch (DocumentException e1) {
			System.out.println("Document exception while adding new paragrapghs" + e1);
		}
		return document;
	}

	/**
	 * Method to create execution details and graph in the PDF report
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 */
	private void createExecutionDetailsAndGraphSection() {
		try {
			Font blue = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD, new BaseColor(9, 0, 132));
			Chunk summaryTest = new Chunk("Execution Details", blue);
			Font black = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(128, 128, 128));
			Chunk totalexecutiontime = new Chunk(AutomationConstants.TOTAL_TIME, black);
			Chunk totalTestsLabel = new Chunk("Total Tests :", black);
			Chunk passLabel = new Chunk("Passed Tests :", black);
			Chunk failLabel = new Chunk("Failed Tests :", black);
			Chunk skipLabel = new Chunk("Skipped Tests : ", black);
			Chunk passpercentlabel = new Chunk("Pass % : ", black);
			Chunk failpercentlabel = new Chunk("Fail % : ", black);
			Chunk successCnt = new Chunk(successCount + "", black);
			Chunk failureTxt = new Chunk(failureCount + "", black);
			Chunk skipTxt = new Chunk(skippedCount + "", black);

			Paragraph pp = new Paragraph(AutomationConstants.TEST_ENVIRONMENTS, blue);
			this.document.add(pp);

			if (this.platformValuesTable != null) {
				this.platformValuesTable.setSpacingBefore(15f);
				this.document.add(this.platformValuesTable);
				this.platformValuesTable.setSpacingAfter(15f);
			}

			Paragraph p = new Paragraph();
			p.add(summaryTest);
			p.setSpacingBefore(15f);
			this.document.add(p);

			PdfPTable tbl = new PdfPTable(new float[] { .2f, .6f });
			tbl.setWidthPercentage(100);
			tbl.setSpacingBefore(15f);

			Font summaryFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL,
					new BaseColor(128, 128, 128));
			Font passTestFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL,
					new BaseColor(51, 153, 71));
			Font failTestFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL,
					new BaseColor(227, 30, 38));

			PdfPCell cell = new PdfPCell(new Paragraph(totalexecutiontime.toString(), summaryFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			cell = new PdfPCell(
					new Paragraph(new DecimalFormat("##.##").format(executionTime) + " seconds", summaryFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			tbl.addCell(cell);

			int totalcount = successCount + failureCount + skippedCount;

			cell = new PdfPCell(new Paragraph(totalTestsLabel.toString(), summaryFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			cell = new PdfPCell(new Paragraph((totalcount) + "", summaryFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			tbl.addCell(cell);

			cell = new PdfPCell(new Paragraph(passpercentlabel.toString(), passTestFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			double passpercentvalue = (double) successCount / (double) totalcount;

			cell = new PdfPCell(new Paragraph((int) (passpercentvalue * 100) + "%", passTestFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			cell = new PdfPCell(new Paragraph(failpercentlabel.toString(), failTestFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			double failpercentvalue = (double) failureCount / (double) totalcount;

			cell = new PdfPCell(new Paragraph((int) (failpercentvalue * 100) + "%", failTestFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			cell = new PdfPCell(new Paragraph(passLabel.toString(), passTestFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			cell = new PdfPCell(new Paragraph(successCnt.toString(), passTestFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			cell = new PdfPCell(new Paragraph(failLabel.toString(), failTestFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			cell = new PdfPCell(new Paragraph(failureTxt.toString(), failTestFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			Font skipTestFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL,
					new BaseColor(102, 102, 102));
			cell = new PdfPCell(new Paragraph(skipLabel.toString(), skipTestFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			cell = new PdfPCell(new Paragraph(skipTxt.toString(), skipTestFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			cell = new PdfPCell();
			cell.disableBorderSide(Rectangle.BOX);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tbl.addCell(cell);

			this.document.add(tbl);
			tbl.setSpacingAfter(10f);

			this.chartTable = new PdfPTable(new float[] { 1.5f });
			chartTable.setWidthPercentage(50);
			chartTable.setHorizontalAlignment(Align.LEFT);

			DefaultPieDataset<String> pieSet = new DefaultPieDataset<String>();
			pieSet.setValue(AutomationConstants.PASSED, successCount);
			pieSet.setValue(AutomationConstants.FAILED, failureCount);
			pieSet.setValue(AutomationConstants.SKIPPED, skippedCount);
			JFreeChart pieChart = ChartFactory.createPieChart("", pieSet, true, true, true);
			pieChart.setBorderVisible(false);

			PiePlot<?> ColorConfigurator = (PiePlot<?>) pieChart.getPlot();
			System.setProperty("passColor", "#339947");
			Color passColor = Color.getColor("passColor");
			ColorConfigurator.setSectionPaint(AutomationConstants.PASSED, passColor);
			System.setProperty("failColor", "#E31E26");
			Color failColor = Color.getColor("failColor");
			ColorConfigurator.setSectionPaint(AutomationConstants.FAILED, failColor);
			System.setProperty("skipColor", "#FBBC3D");
			Color skipColor = Color.getColor("skipColor");
			ColorConfigurator.setSectionPaint(AutomationConstants.SKIPPED, skipColor);
			System.setProperty("rectbackColor", "#F8F8F8");
			Color rectbackColor = Color.getColor("rectbackColor");
			ColorConfigurator.setBackgroundPaint(new GradientPaint(0, 0, rectbackColor, 200, 200, rectbackColor, true));
			ColorConfigurator.setBackgroundAlpha(0.5f);
			ColorConfigurator.setIgnoreZeroValues(true);
			ColorConfigurator.setLabelFont(new java.awt.Font(AutomationConstants.LABEL_FONT, 0, 10));
			ColorConfigurator.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({1})"));

			PdfContentByte contentByte = emailwriter.getDirectContent();
			PdfTemplate template = contentByte.createTemplate(300, 170);
			Graphics2D graphics2d = template.createGraphics(300, 170, new DefaultFontMapper());
			Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, 300, 170);
			pieChart.draw(graphics2d, rectangle2d);
			graphics2d.dispose();
			Image pieImage = Image.getInstance(template);

			cell = new PdfPCell(pieImage);
			cell.disableBorderSide(Rectangle.BOX);
			chartTable.addCell(cell);

			this.chartTable.setSpacingBefore(15f);
			this.document.add(this.chartTable);
			this.chartTable.setSpacingBefore(15f);

			if (this.successTable != null) {
				this.successTable.setSpacingBefore(15f);
				this.document.add(this.successTable);
				this.successTable.setSpacingBefore(15f);
			}

			if (this.failTable != null) {
				this.failTable.setSpacingBefore(15f);
				this.document.add(this.failTable);
				this.failTable.setSpacingAfter(15f);
			}

			if (this.skipTable != null) {
				this.skipTable.setSpacingBefore(15f);
				this.document.add(this.skipTable);
				this.skipTable.setSpacingBefore(15f);
			}

			if (this.exceptionTable != null) {
				Paragraph excepHead = new Paragraph(AutomationConstants.FAILURE_DETAILS,
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new BaseColor(9, 0, 132)));
				excepHead.setSpacingBefore(15f);
				this.document.add(excepHead);
				excepHead.setSpacingAfter(15f);

				this.exceptionTable.setSpacingBefore(15f);
				this.document.add(this.exceptionTable);
				this.exceptionTable.setSpacingBefore(15f);
			}

		} catch (DocumentException e) {
			// Commented line for file size check
			// e.printStackTrace();
		}
		this.document.close();

	}

	/**
	 * Method to create the header template for table in PDF document
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @modified 01-04-2023
	 * @param tableType
	 * @param tableHeading
	 * @return
	 * 
	 **/

	private PdfPTable createHeaderTableTemplate(PdfPTable tableType, String tableHeading) {
		if (tableType == null) {
			tableType = new PdfPTable(new float[] { .6f, .3f, .3f, .3f });
			tableType.setWidthPercentage(100);
			BaseColor actualmainHeaderColor = new BaseColor(255, 255, 255);
			Paragraph p = new Paragraph(tableHeading,
					FontFactory.getFont(FontFactory.TIMES_BOLD, 14, actualmainHeaderColor));
			p.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(p);
			cell.setColspan(5);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);

			cell.setBackgroundColor(mainHeaderColor);
			cell.setFixedHeight(20f);
			cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tableType.addCell(cell);

			Font headerFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, new BaseColor(0, 0, 0));

			cell = new PdfPCell(new Paragraph(AutomationConstants.HEADING_METHOD, headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(subHeaderColor);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tableType.addCell(cell);

			cell = new PdfPCell(new Paragraph("Start Time\n(HH:mm:ss)", headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(subHeaderColor);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tableType.addCell(cell);

			cell = new PdfPCell(new Paragraph("End Time\n(HH:mm:ss)", headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(subHeaderColor);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tableType.addCell(cell);

			cell = new PdfPCell(new Paragraph(AutomationConstants.HEADING_TIME, headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(subHeaderColor);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			tableType.addCell(cell);
		}
		return tableType;
	}

	/**
	 * Method to add table cells in each table in the PDF report
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @param result
	 * @param tableType
	 * @return
	 * 
	 **/

	private PdfPCell addCellValueInEachTable(ITestResult result, PdfPTable tableType) {
		Font testValueFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, new BaseColor(0, 0, 0));
		PdfPCell cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName(), testValueFont));
		try {
			cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName(), testValueFont));
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			cell.setBackgroundColor(new BaseColor(245, 249, 253));
			cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
			tableType.addCell(cell);

			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			cal.setTimeInMillis(result.getStartMillis());
			cell = new PdfPCell(new Paragraph("" + df.format(cal.getTime()), testValueFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			cell.setBackgroundColor(new BaseColor(245, 249, 253));
			tableType.addCell(cell);

			cal.setTimeInMillis(result.getEndMillis());
			cell = new PdfPCell(new Paragraph("" + df.format(cal.getTime()), testValueFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			cell.setBackgroundColor(new BaseColor(245, 249, 253));
			tableType.addCell(cell);

			double time_seconds = (result.getEndMillis() - result.getStartMillis()) / 1000.0;
			executionTime += (result.getEndMillis() - result.getStartMillis()) / 1000.0;
			cal.setTimeInMillis(result.getEndMillis() - result.getStartMillis());
			cell = new PdfPCell(new Paragraph("" + new DecimalFormat("##.##").format(time_seconds), testValueFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(tableBorderColor);
			cell.setBorderWidth(tableBorderWidth);
			cell.setBackgroundColor(new BaseColor(245, 249, 253));
			tableType.addCell(cell);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cell;
	}

	/**
	 * This method will capture the screenshot if an exception or error occurs and
	 * add to PDF report
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @modified 01-04-2023
	 * @param result
	 * @param cell
	 * @return
	 * 
	 */
	private WebDriver captureExceptionAndScreenshotInPDFReport(ITestResult result, PdfPCell cell,
			String fullScreenshot) {
		Object currentClass = result.getInstance();
		WebDriver driver = null;
		Throwable throwable = null;
		Image failureScreen = null;
		try {
			driver = ((AutomationEngine) currentClass).getDriver();
		} catch (Exception e1) {
		}
		try {
			throwable = result.getThrowable().getCause();
		} catch (Exception e) {

		}
		if (throwable == null) {
			throwable = result.getThrowable();
		}
		try {
			if (driver != null) {
				if (throwable != null) {
					StringBuffer exceptions = new StringBuffer();
					exceptions.append("\n");
					exceptions.append(AutomationConstants.FAILEDTESTCASE + result.getMethod().getMethodName());
					if (throwable.getCause() != null) {
						if (throwable.getCause().toString().length() >= 25) {
							exceptions.append("\n\n");
							exceptions.append(AutomationConstants.FAILUREREASON + throwable.getCause());
							exceptions.append("\n\n");
						} else {
							exceptions.append("\n\n");
							exceptions.append(AutomationConstants.FAILUREREASON + throwable.getCause().getMessage());
							exceptions.append("\n\n");
						}
					} else {
						if (throwable.toString().length() >= 145) {
							if (throwable.toString().contains("java.lang.AssertionError")) {
								exceptions.append("\n\n");
								exceptions.append(AutomationConstants.FAILUREREASON
										+ throwable.toString().substring(0, throwable.toString().length()));
								exceptions.append("\n\n");
							} else {
								exceptions.append("\n\n");
								exceptions.append(
										AutomationConstants.FAILUREREASON + throwable.toString().substring(0, 143));
								exceptions.append("\n\n");
							}
						} else {
							exceptions.append("\n\n");
							exceptions.append(
									AutomationConstants.FAILUREREASON + throwable.toString().split(":")[1].trim());
							exceptions.append("\n\n");
						}
					}
					try {
						failureScreen = Image.getInstance(fullScreenshot);
					} catch (Exception e) {
					}

					if (exceptionTable == null) {
						this.exceptionTable = new PdfPTable(new float[] { 1.9f });
						this.exceptionTable.setWidthPercentage(100);
					}

					cell = new PdfPCell(new Paragraph("Failure : " + failureCount,
							FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD, new BaseColor(255, 255, 255))));
					cell.setBackgroundColor(mainHeaderColor);
					cell.setFixedHeight(20f);
					cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
					cell.setBorderColor(tableBorderColor);
					cell.setBorderWidth(tableBorderWidth);
					this.exceptionTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(exceptions.toString(),
							FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(0, 0, 0))));
					cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
					cell.setBackgroundColor(new BaseColor(245, 249, 253));
					cell.setBorderColor(tableBorderColor);
					cell.setBorderWidth(tableBorderWidth);
					this.exceptionTable.addCell(cell);

					cell = new PdfPCell();
					if (failureScreen != null)
						cell.setImage(failureScreen);
					else
						cell.setImage(noImage);
					this.exceptionTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(" "));
					cell.disableBorderSide(Rectangle.BOX);
					this.exceptionTable.addCell(cell);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	/**
	 * Method call to get the Test environments for each execution
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @modified 01-04-2023
	 * @param result
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */
	private boolean getEnvironmentDetailsForPDFReport(ITestResult result) {
		try {
			Font valuesFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(128, 128, 128));
			Object currentClass = result.getInstance();
			WebDriver driver = null;
			try {
				driver = ((AutomationEngine) currentClass).getDriver();
			} catch (Exception e) {
			}
			if (platformValuesTable == null) {
				this.platformValuesTable = new PdfPTable(new float[] { .2f, .6f });
				this.platformValuesTable.setWidthPercentage(100);

				// Adding execution environment value to PDF report
				PdfPCell cellB = new PdfPCell();
				cellB = new PdfPCell(new Paragraph(AutomationConstants.EXECUTION_ENVIRONMENTS + " :", valuesFont));
				cellB.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellB.disableBorderSide(Rectangle.BOX);
				this.platformValuesTable.addCell(cellB);
				testExecutionEnvironments = dataHandler.getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG,
						"testEnvironment");
				if (testExecutionEnvironments.equalsIgnoreCase("")) {
					testExecutionEnvironments = "QA";
				}
				cellB = new PdfPCell(new Paragraph(testExecutionEnvironments, valuesFont));
				cellB.disableBorderSide(Rectangle.BOX);
				this.platformValuesTable.addCell(cellB);

				if (driver != null) {
					this.platformValuesTable = getExecutionEnvironmentDetailsForTestReport(cellB, valuesFont, driver);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return platformvalue;
	}

	/**
	 * Method to get the execution environment details for Web
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @modified 01-04-2023
	 * @param cellB
	 * @param valuesFont
	 * @param driver
	 * @return
	 */
	private PdfPTable getExecutionEnvironmentDetailsForTestReport(PdfPCell cellB, Font valuesFont, WebDriver driver) {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String operatingSystem = "";
		String browserName = "";
		String browserVersion = "";
		try {
			operatingSystem = System.getProperty("os.name").toString();
			browserName = cap.getCapability("browserName").toString();
			browserVersion = cap.getBrowserVersion().toString();

			cellB = new PdfPCell(new Paragraph("Operating System" + " :", valuesFont));
			cellB.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellB.disableBorderSide(Rectangle.BOX);
			this.platformValuesTable.addCell(cellB);

			PdfPCell cellC = new PdfPCell(new Paragraph(operatingSystem, valuesFont));
			cellC.disableBorderSide(Rectangle.BOX);
			this.platformValuesTable.addCell(cellC);

			PdfPCell cellD = new PdfPCell(new Paragraph(AutomationConstants.BROWSER_NAME + " :", valuesFont));
			cellD.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellD.disableBorderSide(Rectangle.BOX);
			this.platformValuesTable.addCell(cellD);

			cellC = new PdfPCell(new Paragraph(browserName, valuesFont));
			cellC.disableBorderSide(Rectangle.BOX);
			this.platformValuesTable.addCell(cellC);

			if (!browserVersion.equals("")) {
				PdfPCell cellE = new PdfPCell(new Paragraph(AutomationConstants.BROWSER_VERSION + " :", valuesFont));
				cellE.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellE.disableBorderSide(Rectangle.BOX);
				this.platformValuesTable.addCell(cellE);

				cellC = new PdfPCell(new Paragraph(browserVersion, valuesFont));
				cellC.disableBorderSide(Rectangle.BOX);
				this.platformValuesTable.addCell(cellC);
			}
		} catch (Exception e) {
			browserVersion = null;
			browserName = null;
		}
		platformvalue = true;
		return this.platformValuesTable;
	}

	/**
	 * Method to track the steps
	 * 
	 * @author sanoj.swaminathan
	 * @since 20-04-2021
	 * @param stepAction
	 */
	public void trackSteps(String stepAction) {
		try {
			test.log(Status.INFO, MarkupHelper.createLabel(stepAction, ExtentColor.BLUE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to track the steps
	 * 
	 * @author sanoj.swaminathan
	 * @since 20-04-2021
	 * @param categoryName
	 */
	public void assignCategory(String categoryName) {
		try {
			test.assignCategory(categoryName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to collect list of tests with date added in TestNG XML
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @return testClassNameWithDate
	 * 
	 */
	public ArrayList<String> getTestNameWithDate() {
		return testClassNameWithDate;
	}

	/**
	 * Method to collect list of Tests added in TestNG xml
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @return testName
	 * 
	 */
	public ArrayList<String> getTestName() {
		return testName;
	}

	/**
	 * Method to delete the corrupted files
	 * 
	 * @author sanoj.swaminathan
	 * @since 09-05-2021
	 * @param filePath
	 */
	private void deleteCorruptedFile(String filePath) {
		try {
			String folderName = filePath;
			File pdfPath = new File(System.getProperty("user.dir") + folderName);
			FileOutputStream fileOutStream = null;
			if (pdfPath.exists() && pdfPath.isDirectory()) {
				File[] testExecutionReports = new File(System.getProperty("user.dir") + folderName).listFiles();
				int testExecutionFilesSize = 0;
				testExecutionFilesSize = testExecutionReports.length;
				if (testExecutionFilesSize != 0) {
					for (File individualFiles : testExecutionReports) {
						if (individualFiles.isFile()) {
							long currentFileSize = 0;
							currentFileSize = individualFiles.length();
							if (currentFileSize <= 0 || currentFileSize <= 9) {
								try {
									fileOutStream = new FileOutputStream(individualFiles);
								} finally {
									fileOutStream.flush();
									fileOutStream.close();
									fileOutStream = null;
									System.gc();
								}
								if (folderName.contains("test-output")) {
									if (document.isOpen()) {
										document.close();
									}
								}
								individualFiles.delete();
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}