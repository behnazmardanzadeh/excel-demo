package com.algocoding.excel.domain;

import java.util.Map;
import java.util.Set;

public record ExcelData(Map<String, Object> data) {

    public Object getValue(String key) {
        return data.get(key);
    }

    public Set<String> getKeys() {
        return data.keySet();
    }

    public boolean containsKey(String key) {
        return data.containsKey(key);
    }
}