package com.ju.islamicculturalcenter.service.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailHelper {

    public static final String REGEX_PATTERN = "^(.+)@(.+)$";
    private static final Pattern pattern = Pattern.compile(REGEX_PATTERN);

    public static Boolean validateEmail(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
