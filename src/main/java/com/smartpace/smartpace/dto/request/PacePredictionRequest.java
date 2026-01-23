package com.smartpace.smartpace.dto.request;

import java.time.LocalDate;

import com.smartpace.smartpace.dto.ValidDateRange;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidDateRange
public class PacePredictionRequest {

    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be positive")
    private Integer distance;


    @NotNull(message = "Date from is required")
    private LocalDate dateFrom;

    @NotNull(message = "Date is required")
    private LocalDate dateTo;
}
