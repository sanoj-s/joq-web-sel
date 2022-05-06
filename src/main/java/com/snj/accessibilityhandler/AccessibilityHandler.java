package com.snj.accessibilityhandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;

public class AccessibilityHandler {

	/**
	 * Method to track the violations using AxeBuilder support
	 * 
	 * @author sanojs
	 * @since 06-05-2022
	 * @param driver
	 * @param pageName
	 */
	public void trackViolations(WebDriver driver, String pageName) {
		Results violationResults;
		try {
			violationResults = new AxeBuilder().analyze(driver);
			if (!new File(System.getProperty("user.dir") + "\\Reports").exists()) {
				(new File(System.getProperty("user.dir") + "\\Reports")).mkdir();
			}
			if (!new File(System.getProperty("user.dir") + "\\Reports\\Accessibility").exists()) {
				(new File(System.getProperty("user.dir") + "\\Reports\\Accessibility")).mkdir();
			}
			if (!new File(System.getProperty("user.dir") + "\\Reports\\Accessibility\\Summary").exists()) {
				(new File(System.getProperty("user.dir") + "\\Reports\\Accessibility\\Summary")).mkdir();
			}
			if (!new File(System.getProperty("user.dir") + "\\Reports\\Accessibility\\Details").exists()) {
				(new File(System.getProperty("user.dir") + "\\Reports\\Accessibility\\Details")).mkdir();
			}

			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd");
			LocalDateTime dateNow = LocalDateTime.now();
			String dateValue = dateFormat.format(dateNow);
			String sheetName = pageName + "_" + dateValue;
			String filePath = System.getProperty("user.dir")
					+ "\\Reports\\Accessibility\\Summary\\AccessibilityTestReport_" + dateValue + ".xlsx";
			createExcelFile(filePath);
			int j = 2;
			for (int i = 0; i < violationResults.getViolations().size(); i++) {
				writeToExcel(filePath, sheetName, 1, j, violationResults.getViolations().get(i).getId());
				writeToExcel(filePath, sheetName, 2, j, violationResults.getViolations().get(i).getDescription());
				writeToExcel(filePath, sheetName, 3, j, violationResults.getViolations().get(i).getImpact());
				writeToExcel(filePath, sheetName, 4, j, violationResults.getViolations().get(i).getHelp());
				writeToExcel(filePath, sheetName, 5, j, violationResults.getViolations().get(i).getHelpUrl());
				writeToExcel(filePath, sheetName, 6, j, violationResults.getViolations().get(i).getTags().toString());
				j++;
			}
			AxeReporter.writeResultsToJsonFile(System.getProperty("user.dir") + "\\Reports\\Accessibility\\Details\\"
					+ pageName + "_" + getCurrentDateAndTime(), violationResults);
			AxeReporter.writeResultsToTextFile(System.getProperty("user.dir") + "\\Reports\\Accessibility\\Details\\"
					+ pageName + "_" + getCurrentDateAndTime(), violationResults.getViolations());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to create a new excel file
	 * 
	 * @author sanojs
	 * @since 06-05-2022
	 * @param filePath
	 * @return
	 */
	private XSSFWorkbook createExcelFile(String filePath) {
		XSSFWorkbook workbook = null;
		try {
			File fileName;
			FileOutputStream fos = null;

			File file = new File(filePath);
			if (!file.exists()) {
				fileName = new File(filePath);
				fos = new FileOutputStream(fileName);
				workbook = new XSSFWorkbook();
				workbook.createSheet("Instructions");
				XSSFSheet sheet = workbook.getSheetAt(0);
				Row header = sheet.createRow(0);
				header.createCell(0).setCellValue("Accessibility Testing Report");

				XSSFCellStyle style = workbook.createCellStyle();
				style.setBorderTop(BorderStyle.THICK);
				style.setBorderBottom(BorderStyle.MEDIUM);
				style.setBorderRight(BorderStyle.MEDIUM);
				XSSFFont font = workbook.createFont();
				font.setFontHeightInPoints((short) 15);
				font.setBold(true);
				style.setFont(font);
				header.getCell(0).setCellStyle(style);

				Row row = sheet.getRow(0);
				sheet.autoSizeColumn(0);
				sheet.autoSizeColumn(1);
				row = sheet.getRow(1);
				if (row == null)
					row = sheet.createRow(1);
				Cell cell = row.getCell(0);
				if (cell == null)
					cell = row.createCell(0);
				cell.setCellValue("Please go through following tabs to know the violations");
				workbook.write(fos);
				fos.flush();
				fos.close();
				workbook.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * Method to write the violations in a excel report
	 * 
	 * @author sanojs
	 * @since 06-05-2022
	 * @param sheetName
	 * @param columnIndex
	 * @param rowNum
	 * @param data
	 * @return
	 */
	private boolean writeToExcel(String filePath, String sheetName, int columnIndex, int rowNum, String data) {
		InputStream in = null;
		XSSFWorkbook wb = null;
		Sheet newSheet = null;
		FileOutputStream fileOutStream = null;
		try {
			if (filePath == null || filePath.trim().equals(""))
				throw (new Exception());
			if (filePath.endsWith(".xlsx") || filePath.endsWith(".xls")) {
				in = new FileInputStream(filePath);
				wb = new XSSFWorkbook(in);
			} else {
				throw (new Exception());
			}
			if (rowNum <= 0)
				throw (new Exception());
			try {
				newSheet = wb.getSheet(sheetName);
				if (newSheet != null) {
					throw new Exception("Sheet Already exist...");
				}
				newSheet = wb.createSheet(sheetName);
			} catch (Exception e) {
			}
			int index = wb.getSheetIndex(newSheet);
			if (index == -1)
				throw (new Exception());
			XSSFCellStyle style = wb.createCellStyle();
			style.setBorderTop(BorderStyle.THICK);
			style.setBorderBottom(BorderStyle.THIN);
			XSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 15);
			font.setBold(true);
			style.setFont(font);
			XSSFSheet sheet = wb.getSheetAt(index);
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Violation ID");
			header.createCell(1).setCellValue("Violation Description");
			header.createCell(2).setCellValue("Violation Impact");
			header.createCell(3).setCellValue("Violation Help");
			header.createCell(4).setCellValue("Violation Help URL");
			header.createCell(5).setCellValue("Violation Issue Tags");
			for (int j = 0; j <= 5; j++)
				header.getCell(j).setCellStyle(style);
			Row row = sheet.getRow(0);
			if (columnIndex < 1)
				throw (new Exception());
			sheet.autoSizeColumn(columnIndex - 1);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			Cell cell = row.getCell(columnIndex - 1);
			if (cell == null)
				cell = row.createCell(columnIndex - 1);

			XSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setWrapText(true);
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(data);
			XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			fileOutStream = new FileOutputStream(filePath);
			wb.write(fileOutStream);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				wb.close();
				fileOutStream.close();
				in.close();
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Method to get the current date and time
	 * 
	 * @author sanojs
	 * @since 06-05-2022
	 * @return
	 */
	private String getCurrentDateAndTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
		Date date = new Date();
		String currdate = dateFormat.format(date);
		String currtime = timeFormat.format(date);
		return currdate + "_" + currtime;
	}
}
