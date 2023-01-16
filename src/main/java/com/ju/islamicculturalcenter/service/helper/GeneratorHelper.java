package com.ju.islamicculturalcenter.service.helper;


import java.util.Random;

public class GeneratorHelper {

    private final Random random;

    public GeneratorHelper() {
        this.random = new Random(System.currentTimeMillis());
    }

    public Integer generateRandomNumber() {
        return ((1 + random.nextInt(2)) * 10000 + random.nextInt(10000));
    }
}
