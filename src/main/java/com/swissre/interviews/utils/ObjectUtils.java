package com.swissre.interviews.utils;

import java.util.regex.Pattern;

public class ObjectUtils {

    private static final Pattern SIMPLE_EMAIL_PATTERN = Pattern.compile("^(.+)@(\\S+)$");

    public static void requireNonEmpty(String attributeName, String value) {
        boolean isBlank = value == null || value.trim().length() == 0;
        if (isBlank) {
            throw new IllegalArgumentException(attributeName + " should not be empty nor null");
        }
    }

    public static void requireNonNull(String attributeName, Object object) {
        if (object == null) {
            throw new IllegalArgumentException(attributeName + " should not be null");
        }
    }

    public static void requireValidEmail(String attributeName, String email) {
        requireNonNull(attributeName, email);
        if (!SIMPLE_EMAIL_PATTERN.matcher(email).find()) {
            throw new IllegalArgumentException(attributeName + " is not valid");
        }
    }
}
