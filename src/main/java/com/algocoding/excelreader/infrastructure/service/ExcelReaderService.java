package com.algocoding.excelreader.infrastructure.service;

import com.algocoding.excelreader.domain.ExcelData;
import com.algocoding.excelreader.infrastructure.repository.ExcelFileRepository;

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

