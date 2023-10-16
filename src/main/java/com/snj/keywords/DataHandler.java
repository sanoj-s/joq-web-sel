package com.snj.keywords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.snj.base.AutomationEngine;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

public class DataHandler {
	static XSSFWorkbook workBook;
	static XSSFSheet sheet;

	public DataHandler() {

	}

	public DataHandler(String excelFilePath, String sheetName) {
		try {
			workBook = new XSSFWorkbook(excelFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = workBook.getSheet(sheetName);
	}

	/**
	 * Get number of rows from excel sheet
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-05-2021
	 * @return rowCount
	 * @throws AutomationException
	 */
	public int getRowCountFromExcel() throws AutomationException {
		int rowCount = sheet.getPhysicalNumberOfRows();
		return rowCount;
	}

	/**
	 * Get number of columns from excel sheet
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-05-2021
	 * @return colCount
	 */
	public int getColCountFromExcel() {
		int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
		return colCount;
	}

	/**
	 * Get string or numeric data from excel sheet
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-05-2021
	 * @param rowNum
	 * @param colNum
	 * @return cellValue
	 * @throws AutomationException
	 */
	public String getDataFromExcel(int rowNum, int colNum) throws AutomationException {
		String cellValue = null;
		try {
			CellType cellType = sheet.getRow(rowNum - 1).getCell(colNum - 1).getCellType();
			if (cellType.toString().toLowerCase().equals("numeric")) {
				cellValue = new Utilities().convertIntToString(new Utilities()
						.convertDoubleToInt(sheet.getRow(rowNum - 1).getCell(colNum - 1).getNumericCellValue()));
			}
			if (cellType.toString().toLowerCase().equals("string")) {
				cellValue = sheet.getRow(rowNum - 1).getCell(colNum - 1).getStringCellValue();
			}
			if (cellType.toString().toLowerCase().equals("formula")) {
				FormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();
				switch (evaluator.evaluateFormulaCell(sheet.getRow(rowNum - 1).getCell(colNum - 1))) {
				case NUMERIC:
					cellValue = Double.toString(sheet.getRow(rowNum - 1).getCell(colNum - 1).getNumericCellValue());
					break;
				case STRING:
					cellValue = sheet.getRow(rowNum - 1).getCell(colNum - 1).getStringCellValue();
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				workBook.close();
			} catch (Exception e) {
			}
		}
		return cellValue;
	}

	/**
	 * Get Date data from excel sheet
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-05-2021
	 * @param rowNum
	 * @param colNum
	 * @return cellValue
	 */
	public String getDateDataFromExcel(int rowNum, int colNum) {
		String dateValue = "";
		try {
			Date cellValue = sheet.getRow(rowNum - 1).getCell(colNum - 1).getDateCellValue();
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			dateValue = dateFormat.format(cellValue);
		} catch (Exception e) {
		} finally {
			try {
				workBook.close();
			} catch (Exception e) {
			}
		}
		return dateValue;
	}

	/**
	 * Get Date data from excel sheet based on the given date format
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-05-2021
	 * @param rowNum
	 * @param colNum
	 * @param dateFormat
	 * @return cellValue
	 */
	public String getDateDataFromExcel(int rowNum, int colNum, String dateFormat) {
		String dateValue = "";
		try {
			Date cellValue = sheet.getRow(rowNum - 1).getCell(colNum - 1).getDateCellValue();
			DateFormat finalDateFormat = new SimpleDateFormat(dateFormat);
			dateValue = finalDateFormat.format(cellValue);
		} catch (Exception e) {
		} finally {
			try {
				workBook.close();
			} catch (Exception e) {
			}
		}
		return dateValue;
	}

	/**
	 * Get list of data from a column
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-05-2021
	 * @param sheetName
	 * @param colNum
	 * @return
	 * @throws AutomationException
	 * @throws IOException
	 */
	public ArrayList<String> getColumnDataFromExcel(String sheetName, int colNum)
			throws AutomationException, IOException {
		ArrayList<String> list = new ArrayList<String>();
		try {
			if (colNum < 1)
				throw new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_COLUMN_NO);

			int index = workBook.getSheetIndex(sheetName);
			if (index == -1) {
				throw new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_SHEETNAME);
			}

			XSSFSheet mySheet = workBook.getSheetAt(index);
			Iterator<Row> rowIter = mySheet.rowIterator();
			Row row;
			Cell cell;
			while (rowIter.hasNext()) {
				row = (Row) rowIter.next();
				cell = row.getCell(colNum - 1);
				if (cell != null) {
					if (cell.getCellType().toString().toLowerCase().equals("string"))
						list.add(cell.getStringCellValue());
					else if (cell.getCellType().toString().toLowerCase().equals("numeric")) {
						list.add(cell.getNumericCellValue() + "");
					} else
						list.add(cell.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (new AutomationException(e));
		} finally {
			try {
				workBook.close();
			} catch (Exception e) {
			}
		}
		return list;
	}

	/**
	 * Returns true if data is set successfully else false
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-05-2021
	 * @param excelFilePath
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @param data
	 * @return
	 * @throws AutomationException
	 * 
	 */

	public boolean setDataInExcelFile(String excelFilePath, String sheetName, String colName, int rowNum, String data)
			throws AutomationException {
		try {
			if (rowNum <= 0)
				throw new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_ROW_NO);

			int index = workBook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				throw new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_SHEETNAME);

			XSSFSheet sheet = workBook.getSheetAt(index);
			Row row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				System.out.println(row.getCell(i));
				if (row.getCell(i) == null) {
					continue;
				} else if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
					colNum = i;
					break;
				}
			}
			if (colNum == -1)
				throw new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_COLUMN_NAME);

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			Cell cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);
			XSSFFormulaEvaluator.evaluateAllFormulaCells(workBook);
			FileOutputStream fileOut = new FileOutputStream(excelFilePath);
			workBook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				workBook.close();
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Returns true if data is set successfully else false
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-05-2021
	 * @param excelFilePath
	 * @param sheetName
	 * @param columnIndex
	 * @param rowNum
	 * @param data
	 * @return
	 * @throws AutomationException
	 * 
	 */

	public boolean setDataInExcelFile(String excelFilePath, String sheetName, int colNum, int rowNum, String data) {
		InputStream in = null;
		XSSFWorkbook wb = null;
		try {
			if (excelFilePath == null || excelFilePath.trim().equals(""))
				throw (new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_PATH));

			if (excelFilePath.endsWith(".xlsx") || excelFilePath.endsWith(".xls")) {

				in = new FileInputStream(excelFilePath);
				wb = new XSSFWorkbook(in);
			} else {
				throw (new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_FILE));
			}

			if (rowNum <= 0)
				throw (new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_ROW_NO));

			int index = wb.getSheetIndex(sheetName);
			if (index == -1)
				throw new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_SHEETNAME);

			XSSFSheet sheet = wb.getSheetAt(index);
			Row row = sheet.getRow(0);
			if (colNum < 1)
				throw new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_COLUMN_NO);

			sheet.autoSizeColumn(colNum - 1);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			Cell cell = row.getCell(colNum - 1);
			if (cell == null)
				cell = row.createCell(colNum - 1);
			cell.setCellValue(data);
			XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			FileOutputStream fileOut = new FileOutputStream(excelFilePath);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				wb.close();
				in.close();
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Method to get the property value from the property file
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-05-2021
	 * @param fileName
	 * @param propertyName
	 * @return propValue
	 * @throws AutomationException
	 */
	public String getProperty(String fileName, String propertyName) throws AutomationException {
		try {
			String propValue = "";
			Properties props = new Properties();
			ClassLoader classLoader = DataHandler.class.getClassLoader();
			InputStream input = classLoader.getResourceAsStream(fileName + ".properties");
			props.load(input);
			propValue = props.getProperty(propertyName);
			return propValue;
		} catch (IOException e) {
			throw new AutomationException(
					new AutomationEngine().getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Method to get the property value from the property file path
	 * 
	 * @author sanoj.swaminathan
	 * @since 16-10-2023
	 * @param fileName
	 * @param filePath
	 * @return propertyName
	 * @throws AutomationException
	 */
	public String getPropertyFromFilePath(String filePath, String propertyName) throws AutomationException {
		try {
			String propValue = "";
			Properties props = new Properties();
			FileInputStream input = new FileInputStream(filePath);
			props.load(input);
			input.close();
			propValue = props.getProperty(propertyName);
			return propValue;
		} catch (IOException e) {
			throw new AutomationException(
					new AutomationEngine().getExceptionMessage() + "\n" + AutomationConstants.CAUSE + e.getMessage());
		}
	}

	/**
	 * Method to write new key and value to the properties file
	 * 
	 * @author sanoj.swaminathan
	 * @since 31-07-2023
	 * @param filePath. For example: ./src/test/resources/config.properties
	 * @param keyName
	 * @param value
	 */
	public void writeToPropertiesFile(String filePath, String keyName, String value) {

		Properties properties = new Properties();
		try (InputStream inputStream = new FileInputStream(filePath)) {
			properties.load(inputStream);
		} catch (IOException e) {
		}

		if (!properties.containsKey(keyName)) {
			properties.setProperty(keyName, value);
			try (OutputStream outputStream = new FileOutputStream(filePath, false)) {
				properties.store(outputStream, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Key already exists, skipping addition.");
		}
	}

	/**
	 * Method to update the value of specific key in the properties file
	 * 
	 * @author sanojs
	 * @since 25-09-2023
	 * @param filePath
	 * @param keyName
	 * @param newValue
	 * @throws InterruptedException
	 */
	public void updatePropertyValueInFile(String filePath, String keyName, String newValue)
			throws InterruptedException {
		try {
			FileInputStream input = new FileInputStream(filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line;
			StringBuilder updatedFileContent = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				if (line.startsWith(keyName + "=")) {
					line = keyName + "=" + newValue;
				}
				updatedFileContent.append(line).append(System.lineSeparator());
			}
			reader.close();
			input.close();

			FileWriter writer = new FileWriter(filePath);
			writer.write(updatedFileContent.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get the total number of records in the CSV file
	 * 
	 * @author sanoj.swmaminathan
	 * @since 08-03-2023
	 * @param csvFilePath
	 * @return
	 */
	public int getRowCountFromCSV(String csvFilePath) throws AutomationException {
		int totalRecords = 0;
		try {
			totalRecords = getCSVData(csvFilePath, 0).size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalRecords;
	}

	/**
	 * Method to get the data value from the CSV file based on column index
	 * 
	 * @author sanoj.swmaminathan
	 * @since 08-05-2021
	 * @param csvFilePath
	 * @param columnIndex
	 * @return
	 */
	public List<String> getCSVData(String csvFilePath, int columnIndex) throws AutomationException {
		List<String> list = new ArrayList<String>();
		try {
			CSVReader reader = new CSVReader(new FileReader(csvFilePath));
			List<String[]> allList = reader.readAll();
			Iterator<String[]> iteratedList = allList.iterator();
			while (iteratedList.hasNext()) {
				String[] recordData = iteratedList.next();
				list.add(recordData[columnIndex]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Method to get the data value from the CSV file based on row and column index
	 * 
	 * @author sanoj.swmaminathan
	 * @since 08-03-2023
	 * @param csvFilePath
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	public String getCSVData(String csvFilePath, int rowIndex, int columnIndex) throws AutomationException {
		List<String> list = new ArrayList<String>();
		String dataValue = null;
		try {
			CSVReader reader = new CSVReader(new FileReader(csvFilePath));
			List<String[]> allList = reader.readAll();
			Iterator<String[]> iteratedList = allList.iterator();
			while (iteratedList.hasNext()) {
				String[] recordData = iteratedList.next();
				list.add(recordData[columnIndex]);
			}
			dataValue = list.get(rowIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataValue;
	}

	/**
	 * Method to get an ArrayList containing all column data of specified column
	 * index for the given CSV file
	 * 
	 * @author sanoj.swmaminathan
	 * @since 08-05-2021
	 * @param csvFilePath
	 * @param columnIndex
	 * @param delimiter
	 * @return
	 * @throws AutomationException
	 * 
	 */

	public ArrayList<String> getCSVData(String csvFilePath, int columnIndex, String delimiter)
			throws AutomationException {
		ArrayList<String> datas = new ArrayList<String>();

		File file = new File(csvFilePath);
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			while ((line = br.readLine()) != null) {
				String[] data = line.split("\\" + delimiter);
				datas.add(data[columnIndex].trim());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw (new AutomationException(e));
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	/**
	 * Method to get data value from CSV based on the row and column index along
	 * with specified delimiter
	 * 
	 * @author sanoj.swmaminathan
	 * @since 08-05-2021
	 * @param csvFilePath
	 * @param rowIndex
	 * @param columnIndex
	 * @param delimiter
	 * @return
	 * @throws AutomationException
	 * 
	 */

	public String getCSVData(String csvFilePath, int rowIndex, int columnIndex, String delimiter)
			throws AutomationException {
		ArrayList<String> datas = new ArrayList<String>();
		String dataValue = null;
		File file = new File(csvFilePath);
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			while ((line = br.readLine()) != null) {
				String[] data = line.split("\\" + delimiter);
				datas.add(data[columnIndex].trim());
			}
			dataValue = datas.get(rowIndex);
		} catch (Exception e) {
			e.printStackTrace();
			throw (new AutomationException(e));
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataValue;
	}

	/**
	 * Method to get an ArrayList containing all column data of specified column
	 * name for the given CSV file
	 * 
	 * @author sanoj.swmaminathana
	 * @since 08-05-2021
	 * @param csvFilePath
	 * @param columnName
	 * @param delimiter
	 * @return
	 * @throws AutomationException
	 * 
	 */
	public ArrayList<String> getCSVData(String csvFilePath, String columnName, String delimiter)
			throws AutomationException {
		ArrayList<String> datas = new ArrayList<String>();

		File file = new File(csvFilePath);
		BufferedReader br = null;
		String line = "";
		int columnInex = 0;
		boolean flag = false;
		try {
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			if ((line = br.readLine()) != null) {
				String[] data = line.split(delimiter);
				for (int i = 0; i < data.length; i++) {
					if (data[i].equals(columnName)) {
						columnInex = i;
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				while ((line = br.readLine()) != null) {
					String[] data = line.split(delimiter, -1);
					datas.add(data[columnInex].trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (new AutomationException(e));
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	/**
	 * Method to get data value from CSV based on the row index and column name
	 * along with specified delimiter
	 * 
	 * @author sanoj.swaminathan
	 * @since 08-03-2023
	 * @param csvFilePath
	 * @param rowIndex
	 * @param columnName
	 * @param delimiter
	 * @return
	 * @throws AutomationException
	 */
	public String getCSVData(String csvFilePath, int rowIndex, String columnName, String delimiter)
			throws AutomationException {
		ArrayList<String> datas = new ArrayList<String>();
		String dataValue = null;
		File file = new File(csvFilePath);
		BufferedReader br = null;
		String line = "";
		int columnInex = 0;
		boolean flag = false;
		try {
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			if ((line = br.readLine()) != null) {
				String[] data = line.split(delimiter);
				for (int i = 0; i < data.length; i++) {
					if (data[i].equals(columnName)) {
						columnInex = i;
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				while ((line = br.readLine()) != null) {
					String[] data = line.split(delimiter, -1);
					datas.add(data[columnInex].trim());
				}
			}
			dataValue = datas.get(rowIndex);
		} catch (Exception e) {
			e.printStackTrace();
			throw (new AutomationException(e));
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataValue;
	}
}
