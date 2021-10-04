package com.swissre.interviews.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.swissre.interviews.utils.ObjectUtils.requireNonEmpty;
import static com.swissre.interviews.utils.ObjectUtils.requireValidEmail;
import static org.junit.jupiter.api.Assertions.*;

class ObjectUtilsTest {

    @ParameterizedTest
    @MethodSource("requireNonBlankInvalidParameters")
    void givenNullOrBlankString_ItShouldThrowAnIllegalArgumentException(String value) {
        assertThrows(IllegalArgumentException.class, () -> requireNonEmpty("value", value));
    }

    public static Stream<Arguments> requireNonBlankInvalidParameters() {
        return Stream.of(
          Arguments.of(""),
          Arguments.of(" "),
          Arguments.of((Object) null)
        );
    }

    @Test
    void givenNonEmpty_ItShouldNotThrowAnIllegalArgumentException() {
        requireNonEmpty("value", "value");
    }

    @ParameterizedTest
    @MethodSource("requireValidEmailWithInvalidEmailsTestCases")
    void givenInvalidEmail_ItShouldThrowAnIllegalArgumentException(String value) {
        assertThrows(IllegalArgumentException.class, () -> requireValidEmail("value", value));
    }

    public static Stream<Arguments> requireValidEmailWithInvalidEmailsTestCases() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of("bob"),
                Arguments.of("bob@"),
                Arguments.of("@llamaland.com")
        );
    }

    @ParameterizedTest
    @MethodSource("requireValidEmailWithValidEmailsTestCases")
    void givenValidEmail_ItShouldNotThrowAnException(String value) {
        requireValidEmail("value", value);
    }

    public static Stream<Arguments> requireValidEmailWithValidEmailsTestCases() {
        return Stream.of(
                Arguments.of("bob@llamaland"),
                Arguments.of("aa@llamaland.com")
        );
    }

}