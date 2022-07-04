package com.snj.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.time.StopWatch;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.snj.exception.AutomationException;
import com.snj.reporting.AutomationReport;

public class PageLoadTracker {
	StopWatch pageLoad = new StopWatch();

	public PageLoadTracker() {
		pageLoad.start();
	}

	/**
	 * Method to collect the page load time
	 * 
	 * @author sanojs
	 * @since 04-07-2022
	 * @param driver
	 * @param expectedObject
	 * @param expectedTime
	 * @param pageNameOrFlow
	 * @throws AutomationException
	 * @throws UnirestException
	 */
	public void collectLoadTime(WebDriver driver, String expectedObject, long expectedTime, String pageNameOrFlow,
			String reportName) throws AutomationException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(expectedTime));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(expectedObject)));

			pageLoad.stop();
			long pageLoadTime_ms = pageLoad.getTime();
			long pageLoadTime_Seconds = pageLoadTime_ms / 1000;
			if (pageLoadTime_Seconds < 1) {
				System.out.println("Total Page Load Time for " + pageNameOrFlow + " is less than 1 second");
				new AutomationReport()
						.trackSteps("Total Page Load Time for " + pageNameOrFlow + " is less than 1 second");
				trackDetailsToReport(pageNameOrFlow, pageLoadTime_Seconds, expectedTime, reportName);
			} else {
				System.out.println(
						"Total Page Load Time for " + pageNameOrFlow + " : " + pageLoadTime_Seconds + " seconds");
				new AutomationReport().trackSteps(
						"Total Page Load Time for " + pageNameOrFlow + " : " + pageLoadTime_Seconds + " seconds");
				trackDetailsToReport(pageNameOrFlow, pageLoadTime_Seconds, expectedTime, reportName);
			}
		} catch (Exception e) {
			throw new AutomationException(pageNameOrFlow + " is not loaded within " + expectedTime + " seconds");
		}
	}

	/**
	 * Method to track the performance details and generate excel report
	 * 
	 * @author sanojs
	 * @since 04-07-2022
	 * @param pageNameOrFlow
	 * @param pageLoadTime_Seconds
	 * @param benchmarTime_Seconds
	 * @param reportName
	 * @throws IOException
	 */
	private void trackDetailsToReport(String pageNameOrFlow, long pageLoadTime_Seconds, long benchmarTime_Seconds,
			String reportName) throws IOException {

		// Creating file object of existing excel file
		if (!new File(System.getProperty("user.dir") + "\\Reports").exists()) {
			(new File(System.getProperty("user.dir") + "\\Reports")).mkdir();
		}
		if (!new File(System.getProperty("user.dir") + "\\Reports\\Performance").exists()) {
			(new File(System.getProperty("user.dir") + "\\Reports\\Performance")).mkdir();
		}
		if (!new File(System.getProperty("user.dir") + "\\Reports\\Performance\\Summary").exists()) {
			(new File(System.getProperty("user.dir") + "\\Reports\\Performance\\Summary")).mkdir();
		}
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		LocalDateTime dateNow = LocalDateTime.now();
		String dateValue = dateFormat.format(dateNow);
		String filePath = System.getProperty("user.dir") + "\\Reports\\Performance\\Summary\\Performance_Test_Report_"
				+ reportName + "_" + dateValue + ".xlsx";

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
			header.createCell(0).setCellValue("Page or Flow");
			header.createCell(1).setCellValue("Actual Load Time (in Seconds)");
			header.createCell(2).setCellValue("Benchmark Time (in Seconds)");
			header.createCell(3).setCellValue("Status");
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setBold(true);
			style.setFont(font);
			style.setWrapText(true);
			sheet.setColumnWidth(0, 70 * 256);
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 15 * 256);
			sheet.setColumnWidth(3, 10 * 256);
			for (int j = 0; j <= 3; j++) {
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
		Object[][] newdataLists = {
				{ pageNameOrFlow, String.valueOf(pageLoadTime_Seconds), String.valueOf(benchmarTime_Seconds) } };
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
				// Setting the status after compare with benchmark value and also setting the
				// color style
				for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
					CellStyle style = workbook.createCellStyle();
					Font font = workbook.createFont();
					font.setBold(true);
					DataFormatter df = new DataFormatter();
					int actualTime = Integer.parseInt(df.formatCellValue(sheet.getRow(i).getCell(1)));
					int benchmarkTime = Integer.parseInt(df.formatCellValue(sheet.getRow(i).getCell(2)));
					Cell cell = row.createCell(3);
					if (actualTime > benchmarkTime) {
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
