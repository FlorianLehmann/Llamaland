package com.swissre.interviews.repositories;

import com.swissre.interviews.models.Citizen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FileCitizenRepositoryTest {
    private File citizensFile;
    private File unsubscribedCitizensEmailFile;

    @BeforeEach
    void setUp() throws IOException {
        citizensFile = File.createTempFile("citizens", "");
        unsubscribedCitizensEmailFile = File.createTempFile("unsubscribedCitizensEmail", "");
    }

    @AfterEach
    void tearDown() {
        citizensFile.deleteOnExit();
        unsubscribedCitizensEmailFile.deleteOnExit();
    }

    @Test
    void givenEmptyFiles_ItShouldReturnAnEmptySet() {
        FileCitizenRepository fileCitizenRepository = new FileCitizenRepository(citizensFile.toPath(), unsubscribedCitizensEmailFile.toPath());

        assertThat(fileCitizenRepository.getCitizens()).isEmpty();
    }

    @Test
    void givenOneRegisteredCitizen_ItShouldReturnASetWithASingleItem() throws IOException {
        String citizensFileContent = "Brown,Bobby,10-11-1950,bobby.brown@ilovellamaland.com";
        write(citizensFile, citizensFileContent);
        FileCitizenRepository fileCitizenRepository = new FileCitizenRepository(citizensFile.toPath(), unsubscribedCitizensEmailFile.toPath());

        assertThat(fileCitizenRepository.getCitizens()).hasSize(1);
    }

    @Test
    void givenNoUnsubscribedCitizenAndOneRegisteredCitizen_ItShouldParseAndReturnASetWithASingleCitizen() throws IOException {
        String citizensFileContent = "Brown,Bobby,10-11-1950,bobby.brown@ilovellamaland.com";
        write(citizensFile, citizensFileContent);
        FileCitizenRepository fileCitizenRepository = new FileCitizenRepository(citizensFile.toPath(), unsubscribedCitizensEmailFile.toPath());

        Citizen expectedCitizen = new Citizen("Brown", "Bobby", LocalDate.of(1950, 11, 10), "bobby.brown@ilovellamaland.com");
        assertThat(fileCitizenRepository.getCitizens().stream().findFirst()).contains(expectedCitizen);
    }

    @Test
    void givenAnUnsubscribedAndRegisteredCitizen_ItShouldUnsubscribeTheCitizenReturned() throws IOException {
        String citizensFileContent = "Brown,Bobby,10-11-1950,bobby.brown@ilovellamaland.com";
        String unsubscribedCitizensEmailFileContent = "bobby.brown@ilovellamaland.com";
        write(citizensFile, citizensFileContent);
        write(unsubscribedCitizensEmailFile, unsubscribedCitizensEmailFileContent);
        FileCitizenRepository fileCitizenRepository = new FileCitizenRepository(citizensFile.toPath(), unsubscribedCitizensEmailFile.toPath());

        Citizen expectedCitizen = new Citizen("Brown", "Bobby", LocalDate.of(1950, 11, 10), "bobby.brown@ilovellamaland.com");
        expectedCitizen.unsubscribe();

        assertThat(fileCitizenRepository.getCitizens().stream().findFirst()).contains(expectedCitizen);
    }

    @Test
    void givenDifferentCitizensRegisteredWithSameEmail_ItShouldReturnAnEmptySet() throws IOException {
        String citizensFileContent = "Brown,Bobby,10-11-1950,bobby.brown@ilovellamaland.com\n" +
                "O'Rourke,Betsy,10-11-1950,bobby.brown@ilovellamaland.com";
        write(citizensFile, citizensFileContent);
        FileCitizenRepository fileCitizenRepository = new FileCitizenRepository(citizensFile.toPath(), unsubscribedCitizensEmailFile.toPath());

        assertThat(fileCitizenRepository.getCitizens()).isEmpty();
    }

    private void write(File file, String content) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(content);
        bufferedWriter.close();
    }
}