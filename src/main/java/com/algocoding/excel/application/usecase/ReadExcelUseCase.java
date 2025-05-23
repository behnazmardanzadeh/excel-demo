package com.algocoding.excel.application.usecase;

import com.algocoding.excel.domain.ExcelData;
import com.algocoding.excel.infrastructure.service.ExcelReaderService;

import java.io.IOException;
import java.util.List;

public class ReadExcelUseCase {

    private final ExcelReaderService excelReaderService;

    public ReadExcelUseCase(ExcelReaderService excelReaderService) {
        this.excelReaderService = excelReaderService;
    }

    public List<ExcelData> execute(Integer sheetIndex) {
        try {
            return excelReaderService.readExcel(sheetIndex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ExcelData> executeDefault() {
        try {
            return excelReaderService.readExcel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

