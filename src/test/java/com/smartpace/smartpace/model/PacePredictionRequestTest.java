package com.smartpace.smartpace.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;

import com.smartpace.smartpace.dto.request.PacePredictionRequest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

public class PacePredictionRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void setCreateValidRequest() {
        int distance = 2000;
        LocalDate datefrom = LocalDate.now().minusMonths(3);
        LocalDate dateTo = LocalDate.now();

        PacePredictionRequest request = PacePredictionRequest.builder()
                .distance(distance)
                .dateFrom(datefrom)
                .dateTo(dateTo)
                .build();

        Set<ConstraintViolation<PacePredictionRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
        assertEquals(distance, request.getDistance());
        assertEquals(datefrom, request.getDateFrom());
        assertEquals(dateTo, request.getDateTo());
    }

    @Test
    void shouldFailValidationWhenDistanceNull() {
        LocalDate datefrom = LocalDate.now().minusMonths(3);
        LocalDate dateTo = LocalDate.now();

        PacePredictionRequest request = PacePredictionRequest.builder()
                .distance(null)
                .dateFrom(datefrom)
                .dateTo(dateTo)
                .build();

        Set<ConstraintViolation<PacePredictionRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("distance")));
    }

    @Test
    void shouldFailValidationWhenDistanceIsNegative() {
        PacePredictionRequest request = PacePredictionRequest.builder()
                .distance(-100)
                .dateFrom(LocalDate.now().minusMonths(3))
                .dateTo(LocalDate.now())
                .build();

        Set<ConstraintViolation<PacePredictionRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("distance")));
    }

    @Test
    void shouldFailValidationWhenDistanceIsZero() {
        PacePredictionRequest request = PacePredictionRequest.builder()
                .distance(0)
                .dateFrom(LocalDate.now().minusMonths(3))
                .dateTo(LocalDate.now())
                .build();

        Set<ConstraintViolation<PacePredictionRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("distance")));
    }

    @Test
    void shouldFailValidationWhenDateFromIsNull() {
        PacePredictionRequest request = PacePredictionRequest.builder()
                .distance(2000)
                .dateFrom(null)
                .dateTo(LocalDate.now())
                .build();

        Set<ConstraintViolation<PacePredictionRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("dateFrom")));
    }

    @Test
    void shouldFailValidationWhenDateToIsNull() {
        // Given
        PacePredictionRequest request = PacePredictionRequest.builder()
                .distance(2000)
                .dateFrom(LocalDate.now().minusMonths(3))
                .dateTo(null)
                .build();

        // When
        Set<ConstraintViolation<PacePredictionRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("dateTo")));
    }

    @Test
    void shouldAcceptValidDistances() {
        // Given & When & Then
        assertDoesNotThrow(() -> {
            PacePredictionRequest.builder()
                    .distance(2000)
                    .dateFrom(LocalDate.now().minusMonths(3))
                    .dateTo(LocalDate.now())
                    .build();
        });

        assertDoesNotThrow(() -> {
            PacePredictionRequest.builder()
                    .distance(5000)
                    .dateFrom(LocalDate.now().minusMonths(3))
                    .dateTo(LocalDate.now())
                    .build();
        });

        assertDoesNotThrow(() -> {
            PacePredictionRequest.builder()
                    .distance(10000)
                    .dateFrom(LocalDate.now().minusMonths(3))
                    .dateTo(LocalDate.now())
                    .build();
        });
    }
}
