package com.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.genericKeywords.UIGenericFunction;
import testExecutionEngine.TestExecutionEngine;

public class ExcelUtilities {
    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtilities(String excelFilePath) throws IOException {
        try (FileInputStream file = new FileInputStream(excelFilePath)) {
            this.workbook = new XSSFWorkbook(file);
        }
    }

    // Initialize sheet if it's not already initialized
    private Sheet getSheetInitialized(String sheetName) {
        if (sheet == null || !sheet.getSheetName().equals(sheetName)) {
            sheet = workbook.getSheet(sheetName);
        }
        return sheet;
    }

    // Get cell value at specific row and column
    public String getCellValue(int rowNumber, int columnNumber) {
        try {
            Row row = sheet.getRow(rowNumber);
            if (row != null) {
                Cell cell = row.getCell(columnNumber, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                return (cell != null) ? cell.toString() : "";
            }
        } catch (NullPointerException e) {
            return "";
        }
        return "";
    }

    // Get the sheet by name
    public Sheet getSheet(String sheetName) {
        return getSheetInitialized(sheetName);
    }

    // Get row number by searching for a row header
    public int getRowNumber(String sheetName, String rowName) {
        Sheet sheet = getSheetInitialized(sheetName);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            if (getCellValue(i, 0).equals(rowName)) {
                return i;
            }
        }
        return -1;
    }

    // Get column number based on column value and row identifier
    public int getColumnNumber(String sheetName, String columnValue, String rowIdentifier) {
        int rowNumber = -1;
        try {
            rowNumber = Integer.parseInt(rowIdentifier);
        } catch (NumberFormatException e) {
            rowNumber = getRowNumber(sheetName, rowIdentifier);
        }

        if (rowNumber == -1) return -1;

        for (int i = 0; i < sheet.getRow(rowNumber).getLastCellNum(); i++) {
            if (getCellValue(rowNumber, i).equals(columnValue)) {
                return i;
            }
        }
        return -1;
    }

    // Get header values with column numbers
    public Map<String, Integer> getHeadingAndColumnNumber() {
        Map<String, Integer> headerValueAndNumber = new HashMap<>();
        Row row = sheet.getRow(0);

        if (row != null) {
            for (int i = 0; i < row.getLastCellNum(); i++) {
                String cellValue = StringUtils.trimToEmpty(getCellValue(0, i));
                if (!cellValue.isEmpty()) {
                    headerValueAndNumber.put(cellValue, i);
                }
            }
        }
        return headerValueAndNumber;
    }

    // Get scenarios to be executed from a given sheet
    public List<String> getScenariosToBeExecuted() {
        List<String> scenarios = new ArrayList<>();
        sheet = getSheetInitialized(TestExecutionEngine.propFile.getProperty("RunManagerSheet"));
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            if ("Y".equalsIgnoreCase(StringUtils.trimToEmpty(getCellValue(i, 1)))) {
                scenarios.add(getCellValue(i, 0));
            }
        }
        return scenarios;
    }

    // Get test data from the data sheet
    public List<Map<String, String>> getTestDataFromDataSheet() {
        List<Map<String, String>> dataset = new ArrayList<>();
        sheet = getSheetInitialized(TestExecutionEngine.propFile.getProperty("DataSheet"));

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Map<String, String> currentDataset = new HashMap<>();
            for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                String key = getCellValue(0, j).trim();
                if (!key.isEmpty()) {
                    currentDataset.put(key, getCellValue(i, j));
                }
            }
            dataset.add(currentDataset);
        }
        return dataset;
    }

    // Close workbook after reading (optional to prevent memory leaks)
    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
