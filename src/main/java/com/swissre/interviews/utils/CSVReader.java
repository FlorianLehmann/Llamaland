package com.swissre.interviews.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class CSVReader {

    private static final String SEPARATOR = ",";

    public static <T> void read(Path filepath, CSVRecordHandler<T> csvRecordHandler) {
        try (BufferedReader in = new BufferedReader(new FileReader(filepath.toFile()))) {
            Iterable<CSVRecord> records = parseCSV(in);
            for (CSVRecord record : records) {
                csvRecordHandler.handle(record);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file at " + filepath);
        }
    }

    private static Iterable<CSVRecord> parseCSV(BufferedReader fileReader) {
        return fileReader.lines()
                .map(s -> s.split(SEPARATOR))
                .map(CSVRecord::new)
                .collect(Collectors.toList());
    }
}
