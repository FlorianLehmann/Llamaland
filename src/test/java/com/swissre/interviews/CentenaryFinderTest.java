package com.swissre.interviews;

import com.swissre.interviews.models.Citizen;
import com.swissre.interviews.repositories.CitizenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CentenaryFinderTest {

    public static final LocalDate NOW = LocalDate.now();

    private CentenaryFinder centenaryFinder;
    private InMemoryCitizenRepository citizenRepository;

    @BeforeEach
    void setUp() {
        citizenRepository = new InMemoryCitizenRepository();
        centenaryFinder = new CentenaryFinder(citizenRepository, () -> NOW);
    }

    @Test
    public void givenNoCitizen_itShouldReturnAnEmptyList() {
        assertTrue(centenaryFinder.find().isEmpty());
    }

    @Test
    public void givenA90YearsOldCitizen_itShouldReturnAnEmptyList() {
        Citizen citizen = citizen(NOW.minusYears(90));
        citizenRepository.addCitizen(citizen);

        assertTrue(centenaryFinder.find().isEmpty());
    }

    @Test
    public void givenACitizenTurning100YearsOldInFourteenDays_itShouldReturnTheCitizen() {
        Citizen citizen = citizen(NOW.minusYears(100).plusDays(14));
        citizenRepository.addCitizen(citizen);

        assertEquals(1, centenaryFinder.find().size());
        assertTrue(centenaryFinder.find().contains(citizen));
    }

    @Test
    public void givenCitizens_itShouldReturnOnlyTheCitizensTurning100YearsOldInFourteenDays() {
        Citizen citizen1 = citizen(NOW.minusYears(100).plusDays(14));
        Citizen citizen2 = citizen(NOW.minusYears(100).plusDays(14), "bob@gmail.com");
        Citizen citizen3 = citizen(NOW.minusYears(90));

        citizenRepository.addCitizen(citizen1);
        citizenRepository.addCitizen(citizen2);
        citizenRepository.addCitizen(citizen3);

        Set<Citizen> results = centenaryFinder.find();
        assertTrue(results.contains(citizen1));
        assertTrue(results.contains(citizen2));
    }

    @Test
    public void givenACitizenWhoUnsubscribeAndTurning100YearsOldInFourteenDays_itShouldReturnAnEmptyList() {
        Citizen citizen = citizen(NOW.minusYears(100).plusDays(14));
        citizen.unsubscribe();
        citizenRepository.addCitizen(citizen);

        assertTrue(centenaryFinder.find().isEmpty());
    }

    private Citizen citizen(LocalDate dateOfBirth) {
        return citizen(dateOfBirth, "bobby@llamaland.com");
    }

    private Citizen citizen(LocalDate dateOfBirth, String email) {
        return new Citizen("Brown", "Bobby", dateOfBirth, email);
    }

    private class InMemoryCitizenRepository implements CitizenRepository {

        private Set<Citizen> citizens = new HashSet<>();

        public void addCitizen(Citizen citizen) {
            citizens.add(citizen);
        }

        @Override
        public Set<Citizen> getCitizens() {
            return new HashSet<>(citizens);
        }
    }
}