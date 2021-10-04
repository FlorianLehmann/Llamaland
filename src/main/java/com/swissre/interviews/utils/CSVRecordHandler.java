package com.swissre.interviews.utils;

@FunctionalInterface
public interface CSVRecordHandler<T> {
    void handle(CSVRecord csvRecord);
}
