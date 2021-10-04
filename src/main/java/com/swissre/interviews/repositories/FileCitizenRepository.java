package com.swissre.interviews.repositories;

import com.swissre.interviews.models.Citizen;
import com.swissre.interviews.utils.CSVReader;
import com.swissre.interviews.utils.CSVRecord;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileCitizenRepository implements CitizenRepository {

    private static final int LAST_NAME_INDEX = 0;
    private static final int FIRST_NAME_INDEX = 1;
    private static final int DATE_OF_BIRTH_INDEX = 2;
    private static final int EMAIL_INDEX = 3;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final Path citizensFilepath;
    private final Path unsubscribedCitizensEmailFilepath;

    public FileCitizenRepository(Path citizensFilepath, Path unsubscribedCitizensEmailFilepath) {
        this.citizensFilepath = citizensFilepath;
        this.unsubscribedCitizensEmailFilepath = unsubscribedCitizensEmailFilepath;
    }

    public Set<Citizen> getCitizens() {
        Map<String, Citizen> emailToCitizens = new HashMap<>();
        Set<String> duplicatedEmails = new HashSet<>();
        Set<String> emailOfUnsubscribedCitizens = getEmailOfUnsubscribedCitizens();

        CSVReader.read(citizensFilepath, csvRecord -> {
            Citizen citizen = createCitizen(csvRecord, emailOfUnsubscribedCitizens);

            boolean emailAlreadyAssignedToDifferentCitizen = emailToCitizens.containsKey(citizen.getEmail());
            if (emailAlreadyAssignedToDifferentCitizen) {
                duplicatedEmails.add(citizen.getEmail());
            }

            emailToCitizens.put(citizen.getEmail(), citizen);
        });

        emailToCitizens.entrySet().removeIf(entry -> duplicatedEmails.contains(entry.getKey()));

        return new HashSet<>(emailToCitizens.values());
    }

    private Citizen createCitizen(CSVRecord record, Set<String> emailOfUnsubscribedCitizens) {
        Citizen citizen = new Citizen(
                record.get(LAST_NAME_INDEX),
                record.get(FIRST_NAME_INDEX),
                LocalDate.parse(record.get(DATE_OF_BIRTH_INDEX), dateFormatter),
                record.get(EMAIL_INDEX)
        );

        if (emailOfUnsubscribedCitizens.contains(citizen.getEmail())) {
            citizen.unsubscribe();
        }

        return citizen;
    }

    private Set<String> getEmailOfUnsubscribedCitizens() {
        Set<String> emailOfUnsubscribedCitizens = new HashSet<>();
        CSVReader.read(unsubscribedCitizensEmailFilepath, csvRecord -> emailOfUnsubscribedCitizens.add(csvRecord.get(0)));
        return emailOfUnsubscribedCitizens;
    }
}
