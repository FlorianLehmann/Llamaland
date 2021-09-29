package com.swissre.interviews.models;

import java.time.LocalDate;

import static com.swissre.interviews.utils.ObjectUtils.*;

public class Citizen {

    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String email;

    public Citizen(String lastName, String firstName, LocalDate dateOfBirth, String email) {
        requireNonEmpty("lastName", lastName);
        requireNonEmpty("firstName", firstName);
        requireNonNull("dateOfBirth", dateOfBirth);
        requireValidEmail("email", email);

        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("dateOfBirth cannot be set in the future");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                '}';
    }

}
