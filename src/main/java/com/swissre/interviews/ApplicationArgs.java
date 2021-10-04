package com.swissre.interviews;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ApplicationArgs {

    private static final String CITIZENS_FILE_FLAG = "-c";
    private static final String UNSUBSCRIBED_CITIZENS_FILE_FLAG = "-u";

    private Path citizensFilepath;
    private Path unsubscribedCitizensEmailFilepath;

    public ApplicationArgs(String[] args) {
        Map<String, String> flagToValue = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (CITIZENS_FILE_FLAG.equals(args[i])) {
                citizensFilepath = Paths.get(args[i+1]);
            }

            if (UNSUBSCRIBED_CITIZENS_FILE_FLAG.equals(args[i])) {
                unsubscribedCitizensEmailFilepath = Paths.get(args[i+1]);
            }
        }

        if (citizensFilepath == null) {
            throw new RuntimeException("File containing citizens is required. (-c <path>)");
        }

        if (unsubscribedCitizensEmailFilepath == null) {
            throw new RuntimeException("File containing unsubscribed citizens is required. (-u <path>)");
        }
    }

    public Path getCitizensFilepath() {
        return citizensFilepath;
    }

    public Path getUnsubscribedCitizensEmailFilepath() {
        return unsubscribedCitizensEmailFilepath;
    }
}
