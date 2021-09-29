package com.swissre.interviews;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationArgsTest {

    private ApplicationArgs applicationArgs;

    @Test
    void shouldMatchGivenParameters() {
        String citizensFilepath = "citizensFilepath";
        String unsubscribedCitizensEmailFilepath = "unsubscribedCitizensEmailFilepath";
        String[] args = {"-c", citizensFilepath, "-u", unsubscribedCitizensEmailFilepath};

        applicationArgs = new ApplicationArgs(args);

        assertThat(applicationArgs.getCitizensFilepath().toString()).isEqualTo(citizensFilepath);
        assertThat(applicationArgs.getUnsubscribedCitizensEmailFilepath().toString()).isEqualTo(unsubscribedCitizensEmailFilepath);
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