package com.example.SLMS.validation.validator;

import com.example.SLMS.validation.annotation.BookYearConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BookYearValidator implements ConstraintValidator<BookYearConstraint, Integer> {
    public final static Integer MIN_YEAR = 1;
    public final static Integer MAX_YEAR = 9999;

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        if (year == null) return false;
        if (year < MIN_YEAR || year > MAX_YEAR) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Year must be between %d and %d, but was %d".formatted(MIN_YEAR, MAX_YEAR, year)
            ).addConstraintViolation();
            return false;
        }
        return true;
    }
}
