package com.swissre.interviews.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CSVRecordTest {

    @Test
    public void shouldCreateCSVRecordGivenValidParameters() {
        new CSVRecord(new String[]{});
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionGivenNullParameter() {
        assertThrows(IllegalArgumentException.class, () -> new CSVRecord(null));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2})
    public void shouldThrowIndexOutOfBoundExceptionGivenNonExistingColumn(int index) {
        CSVRecord csvRecord = new CSVRecord(new String[]{"value1", "value2"});
        assertThrows(IndexOutOfBoundsException.class, () -> csvRecord.get(index));
    }
}