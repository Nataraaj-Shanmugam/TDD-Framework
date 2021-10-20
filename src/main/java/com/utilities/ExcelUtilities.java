package com.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.genericKeywords.GenericKeywords;

public class ExcelUtilities extends GenericKeywords{
	Workbook book;
	Sheet sheet;
	public ExcelUtilities(String excelFilePath){
		try {
			book = new XSSFWorkbook(new File(excelFilePath));
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Excel functionalities
	public String getCellValue(int rowNumber, int ColumnNumber) {
		try {
			Cell cell = sheet.getRow(rowNumber).getCell(ColumnNumber, MissingCellPolicy.RETURN_NULL_AND_BLANK);
			//			cell.setCellFormula("`");
			return cell.toString();
		}catch(NullPointerException n) {
			return "";	
		}
	}

	/**
	 * Function to get the datasheet
	 * @param sheetName
	 * @return
	 */
	public Sheet getSheet(String sheetName) {
		return book.getSheet(sheetName);	
	}
	/**
	 *  Function to get row number of the specified row header
	 * @param rowName
	 * @return
	 */
	int getRowNumber(String rowName) {
		return getRowNumber(sheet.getSheetName(), rowName);	
	}

	/**
	 * Function to get row number of the specified row header and sheetname
	 * @param sheetName
	 * @param rowName
	 * @return
	 */
	int getRowNumber(String sheetName, String rowName) {
		for (int i = 0; i <= getLastRowNumber(); i++) {
			if(getCellValue(i, 0).equals(rowName)) {
				return i;
			}
		}
		return -1;	
	}

	/**
	 * Function to get column number of the specified row number and column value
	 * @param columnValue
	 * @param rowNumber
	 * @return
	 */
	int getColumnNumber(String columnValue,String row) {
		int rowNumber = 0;
		try {
			rowNumber=Integer.parseInt(row);
		}catch(NumberFormatException n) {
			rowNumber = getRowNumber(row);
		}

		for (int i = 0; i < getLastCellNumber(rowNumber); i++) {
			if(getCellValue(rowNumber, i).equals(columnValue)) {
				return i;
			}
		}
		return -1;	
	}

	/**
	 * Function to get header value for the specified row with (columnValue , columnNumber) format
	 * @return
	 */
	HashMap<String, Integer> getHeadingAndColumnNumber() {
		HashMap<String, Integer> headerValueAndNumber = new HashMap<String, Integer>();
		for (int i = 0; i < getLastRowNumber(); i++) {
			headerValueAndNumber.put(StringUtils.trimToEmpty(getCellValue(0, i)),i);
		}
		return headerValueAndNumber;	
	}

	/**
	 * Function to get last row number
	 * @return
	 */
	private int getLastRowNumber() { 
		return	sheet.getLastRowNum();
	}


	/**
	 * Function to get last column number for specified row
	 * @return
	 */
	private int getLastCellNumber(int rowNumber) {
		return sheet.getRow(rowNumber).getLastCellNum();
	}

	/**
	 * Function to get last column number for specified row
	 * @return
	 */
	private int getLastCellNumber(String sheetName, int rowNumber) {
		return getSheet(sheetName).getRow(rowNumber).getLastCellNum();
	}

	/**
	 * Function to get list of scenario to be executed
	 * @return
	 */
	ArrayList<String> scenariosToBeExecuted(){
		ArrayList<String> scenariosToBeExecuted = new ArrayList<String>();
		sheet = getSheet(propFile.getProperty("RunManagerSheet"));
		for (int i = 1; i <= getLastRowNumber(); i++) {
			if(StringUtils.trimToEmpty(getCellValue(i, 1)).equalsIgnoreCase("Y"))
				scenariosToBeExecuted.add(getCellValue(i, 0));
		}
		return scenariosToBeExecuted;
	}

	/**
	 * Function to get TestData for eachrow
	 * @return
	 */
	public List<HashMap<String, String>> getTestDataFromDataSheet(){
		List<HashMap<String,String>> dataset = new ArrayList<HashMap<String,String>>();
		sheet = getSheet(propFile.getProperty("DataSheet"));
		for (int i = 1; i <= getLastRowNumber(); i++) {
			HashMap<String,String> currentDataset = new HashMap<String,String>();
			for (int j = 0; j <= getLastCellNumber(0); j++) {
				if(!getCellValue(0, j).trim().isEmpty())
					currentDataset.put(getCellValue(0, j), getCellValue(i, j));
			}
			dataset.add(currentDataset);
		}
		return dataset;
	}
}
