package com.snj.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.snj.action.UtilityActions;
import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

public class ExcelDataHandler {

	static XSSFWorkbook workBook;
	static XSSFSheet sheet;

	public ExcelDataHandler(String excelPath, String sheetName) throws AutomationException {
		try {
			workBook = new XSSFWorkbook(excelPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = workBook.getSheet(sheetName);
	}

	/**
	 * Get rows count
	 * 
	 * @author sanojs
	 * @since 08-05-2021
	 * @param filePath
	 * @param sheetName
	 * @return rowCount
	 * @throws AutomationException
	 */
	public int getRowCount() throws AutomationException {
		int rowCount = sheet.getPhysicalNumberOfRows();
		return rowCount;
	}

	/**
	 * Get Columns count
	 * 
	 * @author sanojs
	 * @since 08-05-2021
	 * @param filePath
	 * @param sheetName
	 * @return colCount
	 */
	public int getColCount() {
		int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
		return colCount;
	}

	/**
	 * Get string or numeric data from the Cell
	 * 
	 * @author sanojs
	 * @since 08-05-2021
	 * @param filePath
	 * @param sheetName
	 * @param rowNum
	 * @param colNum
	 * @return cellValue
	 * @throws AutomationException
	 */
	public String getCellData(int rowNum, int colNum) throws AutomationException {
		String cellValue = null;
		try {
			CellType cellType = sheet.getRow(rowNum - 1).getCell(colNum - 1).getCellType();
			if (cellType.toString().toLowerCase().equals("numeric")) {
				cellValue = new UtilityActions().convertIntToString(new UtilityActions()
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
			// e.printStackTrace();
		} finally {
			try {
				workBook.close();
			} catch (Exception e) {
			}
		}
		return cellValue;
	}

	/**
	 * Get Date data from the Cell
	 * 
	 * @author sanojs
	 * @since 08-05-2021
	 * @param filePath
	 * @param sheetName
	 * @param rowNum
	 * @param colNum
	 * @return cellValue
	 */
	public String getCellDateData(int rowNum, int colNum) {
		String dateValue = "";
		try {
			Date cellValue = sheet.getRow(rowNum - 1).getCell(colNum - 1).getDateCellValue();
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			dateValue = dateFormat.format(cellValue);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			try {
				workBook.close();
			} catch (Exception e) {
			}
		}
		return dateValue;
	}

	/**
	 * Get Date data from the Cell
	 * 
	 * @author sanojs
	 * @since 08-05-2021
	 * @param filePath
	 * @param sheetName
	 * @param rowNum
	 * @param colNum
	 * @return cellValue
	 */
	public String getCellDateData(int rowNum, int colNum, String dateFormat) {
		String dateValue = "";
		try {
			Date cellValue = sheet.getRow(rowNum - 1).getCell(colNum - 1).getDateCellValue();
			DateFormat finalDateFormat = new SimpleDateFormat(dateFormat);
			dateValue = finalDateFormat.format(cellValue);
		} catch (Exception e) {
			// e.printStackTrace();
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
	 * @author sanojs
	 * @since 08-05-2021
	 * @param sheetName
	 * @param colNum
	 * @return
	 * @throws AutomationException
	 * @throws IOException
	 */
	public ArrayList<String> getColumnData(String sheetName, int colNum) throws AutomationException, IOException {
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
	 * @author sanojs
	 * @since 08-05-2021
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
	 * @author sanojs
	 * @since 08-05-2021
	 * @param filePath
	 * @param sheetName
	 * @param columnIndex
	 * @param rowNum
	 * @param data
	 * @return
	 * @throws AutomationException
	 * 
	 */

	public boolean setDataInExcelFile(String filePath, String sheetName, int colNum, int rowNum, String data) {
		InputStream in = null;
		XSSFWorkbook wb = null;
		try {
			if (filePath == null || filePath.trim().equals(""))
				throw (new AutomationException(AutomationConstants.EXCEPTIION_EXCEL_PATH));

			if (filePath.endsWith(".xlsx") || filePath.endsWith(".xls")) {

				in = new FileInputStream(filePath);
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
			FileOutputStream fileOut = new FileOutputStream(filePath);
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
}
