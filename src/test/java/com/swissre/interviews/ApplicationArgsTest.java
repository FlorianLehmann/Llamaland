package com.swissre.interviews;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationArgsTest {

    private ApplicationArgs applicationArgs;

    @Test
    void shouldMatchGivenParameters() {
        String citizensFilepath = "citizensFilepath";
        String unsubscribedCitizensEmailFilepath = "unsubscribedCitizensEmailFilepath";
        String[] args = {"-c", citizensFilepath, "-u", unsubscribedCitizensEmailFilepath};

        applicationArgs = new ApplicationArgs(args);

        assertEquals(citizensFilepath, applicationArgs.getCitizensFilepath().toString());
        assertEquals(unsubscribedCitizensEmailFilepath, applicationArgs.getUnsubscribedCitizensEmailFilepath().toString());
    }

    @ParameterizedTest
    @MethodSource("missingParametersTestCases")
    void shouldThrowExceptionParametersAreMissing(String []args) {
        assertThrows(RuntimeException.class, () -> new ApplicationArgs(args));
    }

    public static Stream<Arguments> missingParametersTestCases() {
        return Stream.of(
            Arguments.of((Object) new String[]{"-c", "a"}),
            Arguments.of((Object) new String[]{"-u", "a"})
        );
    }

}