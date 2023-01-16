package com.ju.islamicculturalcenter.service.helper;

import com.ju.islamicculturalcenter.exceptions.ValidationException;

import java.util.regex.Pattern;

import static com.ju.islamicculturalcenter.service.helper.CompositeValidator.hasValue;

public class ValidationUtils {

    public static void validateSearchKeyword(String keyword) {
        keyword = keyword.trim();
        if (!isValidKeyword(keyword)) {
            throw new ValidationException("Special characters are not allowed.");
        }
        if (!hasValue(keyword)) {
            throw new ValidationException("Empty search query");
        }
    }

    private static boolean isValidKeyword(String keyword) {
        return Pattern.matches("^[a-zA-Z0-9-._@]+$", keyword);
    }

}
