package com.algocoding.excel.infrastructure.service;

import com.algocoding.excel.domain.ExcelData;
import com.algocoding.excel.infrastructure.repository.ExcelFileRepository;

import java.io.IOException;
import java.util.List;

public class ExcelReaderService {
    private final ExcelFileRepository repository;

    public ExcelReaderService(ExcelFileRepository repository) {
        this.repository = repository;
    }

    public List<ExcelData> readExcel() throws IOException {
        return repository.readExcelData(0);
    }

    public List<ExcelData> readExcel(int sheetIndex) throws IOException {
        return repository.readExcelData(sheetIndex);
    }
}

