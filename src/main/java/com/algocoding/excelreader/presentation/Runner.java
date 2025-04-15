package com.algocoding.excelreader.presentation;

import com.algocoding.excelreader.application.usecase.ReadExcelUseCase;
import com.algocoding.excelreader.domain.ExcelData;
import com.algocoding.excelreader.infrastructure.repository.ExcelFileRepository;
import com.algocoding.excelreader.infrastructure.service.ExcelReaderService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Runner {
    public static void main(String[] args) {
        String filePath = "";

        // Load the YAML file using YamlReader
        Map<String, Object> data = YamlReader.loadYaml("application.yml");

        // Safely access the file path
        filePath = getFilePath(data).orElseThrow(() -> new RuntimeException("File path not found"));

        System.out.println("File Path: " + filePath);

        ReadExcelUseCase readExcelUseCase = new ReadExcelUseCase(new ExcelReaderService(new ExcelFileRepository(filePath)));
        List<ExcelData> excelDataList = readExcelUseCase.execute(0);
    }

    private static Optional<String> getFilePath(Map<String, Object> data) {
        return Optional.ofNullable(data.get("excel"))
                .filter(Map.class::isInstance)
                .map(Map.class::cast)
                .map(excelConfig -> (String) excelConfig.get("file-path"));
    }
}