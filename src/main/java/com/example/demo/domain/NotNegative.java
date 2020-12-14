package com.example.demo.domain;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNegativeValidator.class)

public @interface NotNegative {
    String message() default "can't have a negative number of floors";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
