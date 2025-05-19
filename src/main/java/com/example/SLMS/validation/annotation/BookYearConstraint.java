package com.example.SLMS.validation.annotation;

import com.example.SLMS.validation.validator.BookYearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BookYearValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface BookYearConstraint {
    String message() default "Must be a valid 4-digit year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
