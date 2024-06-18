package com.example.example4.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

@Component
public class MinuteCandleValidator implements Validator {

    private List<Integer> allowedUnits;

    public MinuteCandleValidator() {
        allowedUnits = Arrays.asList(1, 3, 5, 10, 15, 30, 60, 240);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Integer.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Integer unit = (Integer) target;

        if (!allowedUnits.contains(unit)) {
            errors.rejectValue("unit", "invalid unit");
        }
    }
}
