package com.swissre.interviews;

import com.swissre.interviews.models.Citizen;
import com.swissre.interviews.repositories.CitizenRepository;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CentenaryFinder {

    private static final int CENTURY_IN_YEARS = 100;

    private final LocalDate now;
    private final CitizenRepository citizenRepository;

    public CentenaryFinder(CitizenRepository citizenRepository, Supplier<LocalDate> localDateSupplier) {
        this.citizenRepository = citizenRepository;
        this.now = localDateSupplier.get();
    }

    public Set<Citizen> find() {
        Set<Citizen> citizens = citizenRepository.getCitizens();

        return citizens.stream()
                .filter(Citizen::hasSubscribed)
                .filter(citizen -> isTurning100YearsOldInFourteenDays(citizen.getDateOfBirth()))
                .collect(Collectors.toSet());
    }

    private boolean isTurning100YearsOldInFourteenDays(LocalDate dateOfBirth) {
        LocalDate dateTurning100YearsOld = dateOfBirth.plusYears(CENTURY_IN_YEARS);
        LocalDate notificationDate = dateTurning100YearsOld.minusDays(14);
        return now.equals(notificationDate);
    }

}
