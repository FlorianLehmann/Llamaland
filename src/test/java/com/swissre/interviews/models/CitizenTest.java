package com.swissre.interviews.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CitizenTest {

    public static final String EMAIL = "bob@llamaland.com";
    public static final String FIRST_NAME = "Bobby";
    public static final String LAST_NAME = "Brown";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(2020, 1, 12);

    @Test
    public void shouldCreateCitizenGivenValidParameters() {
        citizen();
    }

    @ParameterizedTest
    @MethodSource("invalidParameterTestCases")
    public void shouldThrowIllegalArgumentExceptionGivenInvalidParameters(String lastName, String firstName, LocalDate localDate, String email) {
        assertThrows(IllegalArgumentException.class, () -> new Citizen(lastName, firstName, localDate, email));
    }

    public static Stream<Arguments> invalidParameterTestCases() {
        return Stream.of(
                Arguments.of(" ", FIRST_NAME, DATE_OF_BIRTH, EMAIL),
                Arguments.of(null, FIRST_NAME, DATE_OF_BIRTH, EMAIL),
                Arguments.of(LAST_NAME, " ", DATE_OF_BIRTH, EMAIL),
                Arguments.of(LAST_NAME, null, DATE_OF_BIRTH, EMAIL),
                Arguments.of(LAST_NAME, FIRST_NAME, null, EMAIL),
                Arguments.of(LAST_NAME, FIRST_NAME, LocalDate.now().plusYears(1), EMAIL),
                Arguments.of(LAST_NAME, FIRST_NAME, DATE_OF_BIRTH, "bob@")
        );
    }

    @Test
    public void shouldSubscribeToKingsEmailByDefault() {
        Citizen citizen = citizen();
        assertTrue(citizen.hasSubscribed());
    }

    @Test
    public void shouldSetSubscriptionToFalseWhenACitizenWantsToUnsubscribe() {
        Citizen citizen = citizen();
        citizen.unsubscribe();

        assertFalse(citizen.hasSubscribed());
    }

    @Test
    public void givenTwoCitizensCreatedWithSameParameters_TheyShouldBeEqual() {
        Citizen citizen1 = citizen();
        Citizen citizen2 = citizen();
        assertEquals(citizen1, citizen2);
    }

    private Citizen citizen() {
        return new Citizen(LAST_NAME, FIRST_NAME, DATE_OF_BIRTH, EMAIL);
    }

}