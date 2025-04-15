package com.algocoding.excel.infrastructure.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import com.algocoding.excel.domain.ExcelData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileRepository {

    private final String filePath;

    public ExcelFileRepository(String filePath) {
        this.filePath = filePath;
    }

    public List<ExcelData> readExcelData(int sheetIndex) throws IOException {
        List<ExcelData> excelDataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Row headerRow = sheet.getRow(0);
            int columnCount = headerRow.getPhysicalNumberOfCells();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                Map<String, Object> rowData = new HashMap<>();

                for (int colIndex = 0; colIndex < columnCount; colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    String header = headerRow.getCell(colIndex).getStringCellValue();
                    Object value = getCellValue(cell);
                    rowData.put(header, value);
                }

                excelDataList.add(new ExcelData(rowData));
            }
        }

        return excelDataList;
    }

    public void writeExcelData(List<ExcelData> excelDataList) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            if (!excelDataList.isEmpty()) {
                ExcelData firstRowData = excelDataList.getFirst();
                Set<String> keys = firstRowData.getKeys();
                int colIndex = 0;

                for (String key : keys) {
                    Cell cell = headerRow.createCell(colIndex++);
                    cell.setCellValue(key);
                }

                // Create data rows
                for (int rowIndex = 0; rowIndex < excelDataList.size(); rowIndex++) {
                    ExcelData excelData = excelDataList.get(rowIndex);
                    Row dataRow = sheet.createRow(rowIndex + 1);

                    colIndex = 0;
                    for (String key : keys) {
                        Cell cell = dataRow.createCell(colIndex++);
                        Object value = excelData.getValue(key);
                        setCellValue(cell, value);
                    }
                }
            }

            // Write to file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }

    private Object getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> cell.getNumericCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            case FORMULA -> cell.getCellFormula();
            default -> null;
        };
    }

    private void setCellValue(Cell cell, Object value) {
        switch (value) {
            case String s -> cell.setCellValue(s);
            case Double v -> cell.setCellValue(v);
            case Boolean b -> cell.setCellValue(b);
            case null, default -> cell.setBlank(); // For null or unsupported types
        }
    }
}
