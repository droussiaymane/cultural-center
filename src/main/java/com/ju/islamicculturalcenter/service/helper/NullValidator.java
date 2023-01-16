package com.ju.islamicculturalcenter.service.helper;

import com.ju.islamicculturalcenter.exceptions.ValidationException;

import static java.util.Objects.isNull;

public class NullValidator {

    public static void validate(Object obj){
        if(isNull(obj))
            throw new ValidationException(obj.toString() + "cannot be null!");
    }
}
