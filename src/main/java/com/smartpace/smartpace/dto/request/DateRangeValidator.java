package com.smartpace.smartpace.dto.request;

import com.smartpace.smartpace.dto.ValidDateRange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, PacePredictionRequest> {

    @Override
    public boolean isValid(PacePredictionRequest request, ConstraintValidatorContext context) {
        if (request == null || request.getDateFrom() == null || request.getDateTo() == null) {
            return true;
        }
        boolean isValid = !request.getDateFrom().isAfter(request.getDateTo());

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "Date from must be before or equal to date to"
            ).addPropertyNode("dateFrom").addConstraintViolation();
        }
        return isValid;

    }

}
