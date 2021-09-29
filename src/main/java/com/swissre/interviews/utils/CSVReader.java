package com.swissre.interviews.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

public class CSVReader {

    public static <T> void read(Path filepath, CSVRecordHandler<T> csvRecordHandler) {
        try (Reader in = new BufferedReader(new FileReader(filepath.toFile()))) {
            Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
            for (CSVRecord record : records) {
                csvRecordHandler.handle(record);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file at " + filepath);
        }
    }
}
