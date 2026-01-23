package com.smartpace.smartpace.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartpace.smartpace.model.Trend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PacePredictionResponse {

    /**
     * Recommended pace in formatted string (MM:SS.s).
     * Example: "1:45.2"
     */
    private String recommendedPace;
    /**
     * Recommended pace in deciseconds.
     * Example: 1052 = 1:45.2
     */
    private Integer recommendedPaceDeciseconds;

    /**
     * Confidence score of the prediction (0.0 to 1.0).
     * Higher values indicate more reliable predictions.
     */
    private Double confidence;

    /**
     * Number of workouts used for the prediction.
     */
    private Integer basedOnWorkouts;

    /**
     * Performance trend direction.
     */
    private Trend trend;

    /**
     * Additional analysis data.
     */
    private Analysis analysis;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Analysis {

        /**
         * Average pace from historical workouts (MM:SS.s).
         */
        private String averagePace;

        /**
         * Best pace from historical workouts (MM:SS.s).
         */
        private String bestPace;

        /**
         * Recent trend percentage change.
         * Negative values indicate improvement (pace decreased).
         */
        private Double recentTrend;
    }

}
