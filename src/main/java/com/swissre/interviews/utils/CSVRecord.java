package com.swissre.interviews.utils;


import static com.swissre.interviews.utils.ObjectUtils.requireNonNull;

public class CSVRecord {

    private final String[] columns;

    public CSVRecord(String[] columns) {
        requireNonNull("Columns", columns);

        this.columns = columns;
    }

    public String get(int index) {
        return columns[index];
    }
}
