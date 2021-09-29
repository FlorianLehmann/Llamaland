package com.swissre.interviews;

import com.swissre.interviews.models.Citizen;
import com.swissre.interviews.repositories.CitizenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CentenaryFinderTest {

    public static final LocalDate NOW = LocalDate.now();

    private CentenaryFinder centenaryFinder;
    private CitizenRepository citizenRepository;

    @BeforeEach
    void setUp() {
        citizenRepository = mock(CitizenRepository.class);
        centenaryFinder = new CentenaryFinder(citizenRepository, () -> NOW);
    }

    @Test
    public void givenNoCitizen_itShouldReturnAnEmptyList() {
        assertThat(centenaryFinder.find()).isEmpty();
    }

    @Test
    public void givenA90YearsOldCitizen_itShouldReturnAnEmptyList() {
        Citizen citizen = citizen(NOW.minusYears(90));
        when(citizenRepository.getCitizens()).thenReturn(new HashSet<>(Collections.singletonList(citizen)));

        assertThat(centenaryFinder.find()).isEmpty();
    }

    @Test
    public void givenACitizenTurning100YearsOldInFourteenDays_itShouldReturnTheCitizen() {
        Citizen citizen = citizen(NOW.minusYears(100).plusDays(14));
        when(citizenRepository.getCitizens()).thenReturn(new HashSet<>(Collections.singletonList(citizen)));

        assertThat(centenaryFinder.find()).containsExactly(citizen);
    }

    @Test
    public void givenCitizens_itShouldReturnOnlyTheCitizensTurning100YearsOldInFourteenDays() {
        Citizen citizen1 = citizen(NOW.minusYears(100).plusDays(14));
        Citizen citizen2 = citizen(NOW.minusYears(100).plusDays(14));
        Citizen citizen3 = citizen(NOW.minusYears(90));

        when(citizenRepository.getCitizens()).thenReturn(new HashSet<>(Arrays.asList(citizen1, citizen2, citizen3)));

        assertThat(centenaryFinder.find()).containsExactlyInAnyOrder(citizen1, citizen2);
    }

    @Test
    public void givenACitizenWhoUnsubscribeAndTurning100YearsOldInFourteenDays_itShouldReturnAnEmptyList() {
        Citizen citizen = citizen(NOW.minusYears(100).plusDays(14));
        citizen.unsubscribe();
        when(citizenRepository.getCitizens()).thenReturn(new HashSet<>(Collections.singletonList(citizen)));

        assertThat(centenaryFinder.find()).isEmpty();
    }

    private Citizen citizen(LocalDate dateOfBirth) {
        return new Citizen("Brown", "Bobby", dateOfBirth, "bobby@llamaland.com");
    }

}