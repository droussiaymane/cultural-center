package com.ju.islamicculturalcenter.service.helper;

import com.ju.islamicculturalcenter.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class CompositeValidator<P, R> implements Validator<P, List<R>> {

    private final List<Validator<P, List<R>>> validators = new ArrayList<>();

    public static void joinViolations(List<String> violations) {
        if (!violations.isEmpty()) {
            throw new ValidationException(join(",", violations));
        }
    }

    public static boolean hasValue(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public CompositeValidator<P, R> addValidator(Predicate<P> predicate, R violation) {
        validators.add(new PredicateValidator<>(predicate, violation));
        return this;
    }

    @Override
    public List<R> validate(P input) {
        return validators.stream()
                .map(v -> v.validate(input))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
