package com.algocoding.excel.presentation;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlReader {
    public static Map<String, Object> loadYaml(String filePath) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = YamlReader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + filePath);
            }
            return yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML file: " + filePath, e);
        }
    }
}
