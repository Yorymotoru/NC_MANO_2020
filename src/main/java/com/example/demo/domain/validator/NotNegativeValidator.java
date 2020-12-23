package com.example.demo.domain.validator;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class NotNegativeValidator implements ConstraintValidator<NotNegative, String> {
    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        int i;
        try {
            i = Integer.parseInt(field);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return i >= 0;
    }
}
