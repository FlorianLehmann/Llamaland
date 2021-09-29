package com.swissre.interviews;

import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationArgs {

    private final Path citizensFilepath;
    private final Path unsubscribedCitizensEmailFilepath;

    public ApplicationArgs(String[] args) {
        Options options = new Options();
        options.addRequiredOption("c","citizens", true, "To specify the file containing citizens data");
        options.addRequiredOption("u","unsubscribed-citizens", true, "To specify the file containing emails of unsubscribed citizens");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse( options, args);
            citizensFilepath = Paths.get(cmd.getOptionValue("c"));
            unsubscribedCitizensEmailFilepath = Paths.get(cmd.getOptionValue("u"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getCitizensFilepath() {
        return citizensFilepath;
    }

    public Path getUnsubscribedCitizensEmailFilepath() {
        return unsubscribedCitizensEmailFilepath;
    }
}
