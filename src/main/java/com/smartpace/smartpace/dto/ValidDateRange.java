package com.smartpace.smartpace.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.smartpace.smartpace.dto.request.DateRangeValidator;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface ValidDateRange {
    String message() default "Date from must be before or equal to date to";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
