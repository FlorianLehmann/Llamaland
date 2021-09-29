package com.swissre.interviews.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class ObjectUtils {

    public static void requireNonEmpty(String attributeName, String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(attributeName + " should not be empty nor null");
        }
    }

    public static void requireNonNull(String attributeName, Object object) {
        if (object == null) {
            throw new IllegalArgumentException(attributeName + " should not be null");
        }
    }

    public static void requireValidEmail(String attributeName, String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException(attributeName + " is not valid");
        }
    }
}
