package com.swissre.interviews;

import com.swissre.interviews.models.Citizen;
import com.swissre.interviews.repositories.CitizenRepository;
import com.swissre.interviews.repositories.FileCitizenRepository;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {
        ApplicationArgs applicationArgs = new ApplicationArgs(args);

        CitizenRepository citizenRepository = new FileCitizenRepository(applicationArgs.getCitizensFilepath(), applicationArgs.getUnsubscribedCitizensEmailFilepath());

        CentenaryFinder centenaryFinder = new CentenaryFinder(citizenRepository, LocalDate::now);
        for (Citizen citizen : centenaryFinder.find()) {
            System.out.println(citizen);
        }
    }
}
