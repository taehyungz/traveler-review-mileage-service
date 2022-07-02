package com.triple.mileage.common.validation;

import lombok.experimental.UtilityClass;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@UtilityClass
public class ConstraintViolationValidateUtil {

    private static final Validator validator = Validation
            .buildDefaultValidatorFactory()
            .getValidator();

    public static <T> void validate(T target) {
        Set<ConstraintViolation<T>> violations = validator.validate(target);
        if (violations.isEmpty()) {
            return;
        }
        throw new ConstraintViolationException(violations);
    }
}
