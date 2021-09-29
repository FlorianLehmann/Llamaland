package com.swissre.interviews.utils;

import org.apache.commons.csv.CSVRecord;

@FunctionalInterface
public interface CSVRecordHandler<T> {
    void handle(CSVRecord csvRecord);
}
