package com.swissre.interviews.models;

import java.time.LocalDate;
import java.util.Objects;

import static com.swissre.interviews.utils.ObjectUtils.*;

public class Citizen {

    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String email;
    private boolean hasSubscribed;

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
        this.hasSubscribed = true;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public boolean hasSubscribed() {
        return hasSubscribed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citizen citizen = (Citizen) o;
        return hasSubscribed == citizen.hasSubscribed && Objects.equals(firstName, citizen.firstName) && Objects.equals(lastName, citizen.lastName) && Objects.equals(dateOfBirth, citizen.dateOfBirth) && Objects.equals(email, citizen.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth, email, hasSubscribed);
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", hasSubscribed=" + hasSubscribed +
                '}';
    }

    public void unsubscribe() {
        this.hasSubscribed = false;
    }
}
