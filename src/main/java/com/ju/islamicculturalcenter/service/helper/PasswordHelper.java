package com.ju.islamicculturalcenter.service.helper;

import lombok.NoArgsConstructor;
import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.passay.CharacterData;
import org.passay.EnglishCharacterData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordHelper {

    public static final String ERROR_CODE = "ERRONEOUS_SPECIAL_CHARS";
    public static final String REGEX_PATTERN = "(?=.*[A-Z]).{8,20}$";
    private static final Pattern pattern = Pattern.compile(REGEX_PATTERN);

    public static String generatePassword(){

        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(1);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(1);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(1);

        return gen.generatePassword(8, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
    }

    public static Boolean validatePassword(String password){
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
