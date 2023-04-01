package com.snj.reporting;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ui.Align;
import org.jfree.data.general.DefaultPieDataset;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

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

public class AutomationReporting implements ITestListener {
	private Document document = null;
	PdfWriter pdfWriter = null;
	PdfPTable successTable = null, failTable = null, skipTable = null, chartTable = null, exceptionTable = null,
			platformValuesTable = null;
	BaseColor mainHeaderColor = new BaseColor(46, 116, 181);
	BaseColor subHeaderColor = new BaseColor(245, 249, 253);
	BaseColor tableBorderColor = new BaseColor(89, 89, 89);
	float tableBorderWidth = 1f;
	int successCount = 0, failureCount = 0, skippedCount = 0, testCount = 0;
	double executionTime = 0;
	Image noImage = null;
	public String testExecutionEnvironments;
	Future<?> future;
	public WebDriver driverForClient = null;
	boolean platformvalue = false;
	public static String suiteFileName = "";
	public static ArrayList<String> testClassNameWithDate = new ArrayList<>();
	public static ArrayList<String> testName = new ArrayList<String>();

	public AutomationReporting() throws AutomationException {
		this.document = new Document();
	}

	/**
	 * This method will invoke when an execution begins (if we include
	 * AutomationReporter.class) Also collects list of 'Tests' from TestNG xml file.
	 * 
	 * @author sanojs
	 * @since 09-05-2021
	 * @param context
	 * 
	 */
	public void onStart(ITestContext context) {
		try {
			XmlSuite suiteName = context.getCurrentXmlTest().getSuite();
			List<XmlTest> testNames = suiteName.getTests();
			testCount = testNames.size();
		} catch (Exception lException) {
			lException.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
		try {
			new File(new File(System.getProperty("user.dir")), AutomationConstants.REPORT_FOLDER_PDF).mkdirs();

			DateFormat dateFormatt = new SimpleDateFormat("dd_MM_yyyy");
			DateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
			Date date = new Date();
			String currdate = dateFormatt.format(date);
			String currtime = timeFormat.format(date);
			String finaldatetime = currdate + "_" + currtime;
			pdfWriter = PdfWriter.getInstance(this.document, new FileOutputStream(System.getProperty("user.dir")
					+ AutomationConstants.REPORT_FOLDER_PDF + context.getName() + "_" + finaldatetime + ".pdf"));
			String currentTestNameWithDate = ((ITestContext) context).getCurrentXmlTest().getName() + "_"
					+ dateFormat.format(new Date()) + ".pdf";
			testClassNameWithDate.add(currentTestNameWithDate);
			String currentTestName = ((ITestContext) context).getCurrentXmlTest().getName();
			testName.add(currentTestName);

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
		System.out.println("Starting automation execution......");
	}

	/**
	 * This method will invoke for each test starts
	 * 
	 * @author sanojs
	 * @since 09-05-2021
	 * @param result
	 * 
	 */
	@Override
	public void onTestStart(ITestResult result) {
		Object currentClass = result.getInstance();
		driverForClient = ((AutomationEngine) currentClass).getDriver();
	}

	/**
	 * This method will invoke when test case pass
	 * 
	 * @author sanojs
	 * @since 09-05-2021
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
			getEnvironmentDetailsForTestReport(result);
		}
	}

	/**
	 * This method will invoke when test case fails
	 * 
	 * @author sanojs
	 * @since 09-05-2021
	 * @param result
	 * 
	 */

	public void onTestFailure(ITestResult result) {
		failureCount++;
		if (future != null) {
			future.cancel(true);
		}
		if (this.failTable == null) {
			failTable = createHeaderTableTemplate(failTable, AutomationConstants.FAILED_TESTS);
		}
		PdfPCell cell = addCellValueInEachTable(result, failTable);
		if (platformvalue != true) {
			getEnvironmentDetailsForTestReport(result);
		}

		// Capture the defect screenshot and add to PDF report
		captureExceptionAndScreenShotInPDFReport(result, cell);
	}

	/**
	 * This method will invoke when test case skips
	 * 
	 * @author sanojs
	 * @since 09-05-2021
	 * @param result
	 * 
	 */

	public void onTestSkipped(ITestResult result) {
		skippedCount++;

		if (future != null) {
			future.cancel(true);
		}
		if (this.skipTable == null) {
			skipTable = createHeaderTableTemplate(skipTable, AutomationConstants.SKIPPED_TESTS);
		}
		addCellValueInEachTable(result, skipTable);
		if (platformvalue != true) {
			getEnvironmentDetailsForTestReport(result);
		}
	}

	/**
	 * This method will invoke when an execution ends
	 * 
	 * @author sanojs
	 * @since 09-05-2021
	 * @param context
	 * 
	 */
	public void onFinish(ITestContext context) {

		// Create execution details and graph
		createExecutionDetailsAndGraphSection();
	}

	/**
	 * Method to create header logos in the PDF report
	 * 
	 * @author sanojs
	 * @since 09-05-2021
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

			try {
				noImage = Image.getInstance(ClassLoader.getSystemResource(AutomationConstants.NOIMAGE_ICON_PATH));
			} catch (Exception e) {
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
	 * @author sanojs
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
				projectName = new DataHandler().getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG, "projectName");

				if (projectName == null || projectName.equals("")) {
					this.document.add(new Paragraph("Test Summary",
							FontFactory.getFont(FontFactory.TIMES_ROMAN, 26, Font.BOLD, new BaseColor(46, 116, 181))));
				} else {
					this.document.add(new Paragraph("Test Summary for " + projectName,
							FontFactory.getFont(FontFactory.TIMES_ROMAN, 26, Font.BOLD, new BaseColor(46, 116, 181))));
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
	 * @author sanojs
	 * @since 09-05-2021
	 */
	private void createExecutionDetailsAndGraphSection() {
		try {
			Font blue = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD, new BaseColor(46, 116, 181));
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

			Paragraph pp = new Paragraph(AutomationConstants.EXECUTION_ENVIRONMENTS, blue);
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
			Font passTestFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 204, 0));
			Font failTestFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(204, 0, 0));

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
			JFreeChart pieChart = ChartFactory.createPieChart("", pieSet, true, true, false);
			pieChart.setBorderVisible(false);

			PiePlot ColorConfigurator = (PiePlot) pieChart.getPlot();
			System.setProperty("passColor", "#00cc00");
			Color passColor = Color.getColor("passColor");
			ColorConfigurator.setSectionPaint(AutomationConstants.PASSED, passColor);
			System.setProperty("failColor", "#cc0000");
			Color failColor = Color.getColor("failColor");
			ColorConfigurator.setSectionPaint(AutomationConstants.FAILED, failColor);
			System.setProperty("skipColor", "#666666");
			Color skipColor = Color.getColor("skipColor");
			ColorConfigurator.setSectionPaint(AutomationConstants.SKIPPED, skipColor);
			System.setProperty("rectbackColor", "#f5f9fd");
			Color rectbackColor = Color.getColor("rectbackColor");
			ColorConfigurator.setBackgroundPaint(new GradientPaint(0, 0, rectbackColor, 200, 200, rectbackColor, true));
			ColorConfigurator.setBackgroundAlpha(0.5f);
			ColorConfigurator.setIgnoreZeroValues(true);
			ColorConfigurator.setLabelFont(new java.awt.Font(AutomationConstants.LABEL_FONT, 0, 10));
			ColorConfigurator.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({1})"));

			PdfContentByte contentByte = pdfWriter.getDirectContent();
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
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new BaseColor(46, 116, 181)));
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
	 * @author sanojs
	 * @since 09-05-2021
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
	 * @author sanojs
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
	 * @author sanojs
	 * @since 09-05-2021
	 * @param result
	 * @param cell
	 * @return
	 * 
	 */
	private WebDriver captureExceptionAndScreenShotInPDFReport(ITestResult result, PdfPCell cell) {
		Object currentClass = result.getInstance();
		WebDriver driver = null;
		Throwable throwable = null;
		Capabilities mobileCheck = null;
		try {
			driver = ((AutomationEngine) currentClass).getDriver();
		} catch (NullPointerException e1) {
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
				mobileCheck = ((RemoteWebDriver) driver).getCapabilities();
			} else {
				throw new AutomationException("Driver represent a NULL value");
			}
			Object browserName = mobileCheck.getCapability("browserName");
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
							exceptions
									.append(AutomationConstants.FAILUREREASON + throwable.toString().substring(0, 143));
							exceptions.append("\n\n");
						}
					} else {
						exceptions.append("\n\n");
						exceptions
								.append(AutomationConstants.FAILUREREASON + throwable.toString().split(":")[1].trim());
						exceptions.append("\n\n");
					}
				}
				Image failureScrene = null;
				try {
					Utilities utilHelp = new Utilities();
					failureScrene = Image.getInstance(utilHelp.takeScreenshotAsByteArray(driver));
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
				if (failureScrene != null)
					cell.setImage(failureScrene);
				else
					cell.setImage(noImage);
				this.exceptionTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(" "));
				cell.disableBorderSide(Rectangle.BOX);
				this.exceptionTable.addCell(cell);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	/**
	 * Method call to get the Test environments for each execution. Report will
	 * contain "PLATFORM, DEVICE NAME, OS VERSION" for each successful / failure
	 * runs.
	 * 
	 * @author sanojs
	 * @since 09-05-2021
	 * @param result
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */
	private boolean getEnvironmentDetailsForTestReport(ITestResult result) {
		try {
			Font valuesFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(128, 128, 128));
			Object currentClass = result.getInstance();
			WebDriver driver = null;
			try {
				driver = ((AutomationEngine) currentClass).getDriver();
			} catch (NullPointerException e) {
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
				testExecutionEnvironments = new DataHandler().getProperty(AutomationConstants.AUTOMATION_TEST_CONFIG,
						"testEnvironment");
				if (testExecutionEnvironments.equalsIgnoreCase("")) {
					testExecutionEnvironments = "QA";
				}
				cellB = new PdfPCell(new Paragraph(testExecutionEnvironments, valuesFont));
				cellB.disableBorderSide(Rectangle.BOX);
				this.platformValuesTable.addCell(cellB);

				if (driver != null) {
					this.platformValuesTable = getExecutionEnvironmentDetailsForTestReport(cellB, valuesFont, driver);
				} else {
					cellB = new PdfPCell(new Paragraph(AutomationConstants.PLATFORM_NAME + " :", valuesFont));
					cellB.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB.disableBorderSide(Rectangle.BOX);
					this.platformValuesTable.addCell(cellB);

					String platformEmpty = "Driver not loaded";
					cellB = new PdfPCell(new Paragraph(platformEmpty, valuesFont));
					cellB.disableBorderSide(Rectangle.BOX);
					this.platformValuesTable.addCell(cellB);

					cellB = new PdfPCell(new Paragraph(AutomationConstants.BROWSER_NAME + " :", valuesFont));
					cellB.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB.disableBorderSide(Rectangle.BOX);
					this.platformValuesTable.addCell(cellB);

					String browserNameEmpty = "Driver not loaded";
					cellB = new PdfPCell(new Paragraph(browserNameEmpty, valuesFont));
					cellB.disableBorderSide(Rectangle.BOX);
					this.platformValuesTable.addCell(cellB);

					cellB = new PdfPCell(new Paragraph(AutomationConstants.BROWSER_VERSION + " :", valuesFont));
					cellB.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB.disableBorderSide(Rectangle.BOX);
					this.platformValuesTable.addCell(cellB);

					String versionEmpty = "Driver not loaded";
					cellB = new PdfPCell(new Paragraph(versionEmpty, valuesFont));
					cellB.disableBorderSide(Rectangle.BOX);
					this.platformValuesTable.addCell(cellB);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return platformvalue;
	}

	/**
	 * Method to get the execution environment details for Web and Mobile executions
	 * 
	 * @author sanojs
	 * @since 09-05-2021
	 * @param cellB
	 * @param valuesFont
	 * @param driver
	 * @return
	 */
	private PdfPTable getExecutionEnvironmentDetailsForTestReport(PdfPCell cellB, Font valuesFont, WebDriver driver) {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String platform = "";
		String browserName = "";
		String browserVersion = "";
		try {
			platform = cap.getCapability("platformName").toString();
			browserName = cap.getCapability("browserName").toString();
			browserVersion = cap.getBrowserVersion().toString();

			cellB = new PdfPCell(new Paragraph(AutomationConstants.PLATFORM_NAME + " :", valuesFont));
			cellB.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellB.disableBorderSide(Rectangle.BOX);
			this.platformValuesTable.addCell(cellB);

			PdfPCell cellC = new PdfPCell(new Paragraph(platform, valuesFont));
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
			platform = null;
			browserVersion = null;
			browserName = null;
		}
		platformvalue = true;
		return this.platformValuesTable;
	}

	/**
	 * Method to collect list of tests with date added in TestNG XML
	 * 
	 * @author sanojs
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
	 * @author sanojs
	 * @since 09-05-2021
	 * @return testName
	 * 
	 */
	public ArrayList<String> getTestName() {
		return testName;
	}
}