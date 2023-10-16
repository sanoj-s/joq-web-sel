package com.joq.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

import com.joq.exception.AutomationException;
import com.joq.keywords.APIUtilities;
import com.joq.reporting.AutomationReport;

import io.restassured.response.Response;

public class ResponseTimeTracker {

	/**
	 * Method to collect the API response time
	 * 
	 * @author sanoj.swaminathan
	 * @since 01-04-2023
	 * @param response
	 * @param expectedResponseTime
	 * @param APIName
	 * @param reportName
	 * @throws AutomationException
	 * @throws UnirestException
	 */
	public void trackResponseTime(Response response, long expectedResponseTime, String APIName, String reportName)
			throws AutomationException {
		try {
			int actualResponseTime = new APIUtilities().getResponseTime(response, "milliseconds");

			if ((actualResponseTime * 60) < 1) {
				System.out.println("Response time for " + APIName + " is less than 1 second");
				new AutomationReport().trackSteps("Response time for " + APIName + " is less than 1 second");
				trackDetailsToReport(APIName, actualResponseTime, expectedResponseTime, reportName);
			} else {
				System.out.println("Response time for " + APIName + " : " + actualResponseTime + " seconds");
				new AutomationReport()
						.trackSteps("Response time for " + APIName + " : " + actualResponseTime + " seconds");
				trackDetailsToReport(APIName, actualResponseTime, expectedResponseTime, reportName);
			}
		} catch (Exception e) {
			throw new AutomationException(APIName + " is not loaded within " + expectedResponseTime + " seconds");
		}
	}

	/**
	 * Method to track the API response details and generate excel report
	 * 
	 * @author sanoj.swaminathan
	 * @since 01-04-2023
	 * @param APIName
	 * @param actualResponseTime
	 * @param expectedResponseTime
	 * @param reportName
	 * @throws IOException
	 */
	private void trackDetailsToReport(String APIName, long actualResponseTime, long expectedResponseTime,
			String reportName) throws IOException {

		// Creating file object of existing excel file
		if (!new File(System.getProperty("user.dir") + "\\Reports").exists()) {
			(new File(System.getProperty("user.dir") + "\\Reports")).mkdir();
		}
		if (!new File(System.getProperty("user.dir") + "\\Reports\\API_Response_Time_Audit").exists()) {
			(new File(System.getProperty("user.dir") + "\\Reports\\API_Response_Time_Audit")).mkdir();
		}

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		LocalDateTime dateNow = LocalDateTime.now();
		String dateValue = dateFormat.format(dateNow);
		String filePath = System.getProperty("user.dir")
				+ "\\Reports\\API_Response_Time_Audit\\API_Response_Test_Report_" + reportName + "_" + dateValue
				+ ".xlsx";

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
			header.createCell(0).setCellValue("API Name");
			header.createCell(1).setCellValue("Actual Time (in Milliseconds)");
			header.createCell(2).setCellValue("Expected Time (in Milliseconds)");
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
				{ APIName, String.valueOf(actualResponseTime), String.valueOf(expectedResponseTime) } };
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
