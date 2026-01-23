// src/test/java/com/smartpace/smartpace/dto/response/PacePredictionResponseTest.java
package com.smartpace.smartpace.model;

import com.smartpace.smartpace.dto.response.PacePredictionResponse;
import com.smartpace.smartpace.model.Trend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacePredictionResponseTest {
    
    @Test
    void shouldCreateResponseWithAllFields() {
        // Given
        String recommendedPace = "1:45.2";
        int recommendedPaceDeciseconds = 1052;
        double confidence = 0.85;
        int basedOnWorkouts = 15;
        Trend trend = Trend.IMPROVING;
        PacePredictionResponse.Analysis analysis = PacePredictionResponse.Analysis.builder()
            .averagePace("1:46.5")
            .bestPace("1:42.1")
            .recentTrend(-1.3)
            .build();
        
        // When
        PacePredictionResponse response = PacePredictionResponse.builder()
            .recommendedPace(recommendedPace)
            .recommendedPaceDeciseconds(recommendedPaceDeciseconds)
            .confidence(confidence)
            .basedOnWorkouts(basedOnWorkouts)
            .trend(trend)
            .analysis(analysis)
            .build();
        
        // Then
        assertNotNull(response);
        assertEquals(recommendedPace, response.getRecommendedPace());
        assertEquals(recommendedPaceDeciseconds, response.getRecommendedPaceDeciseconds());
        assertEquals(confidence, response.getConfidence());
        assertEquals(basedOnWorkouts, response.getBasedOnWorkouts());
        assertEquals(trend, response.getTrend());
        assertEquals(analysis, response.getAnalysis());
    }
    
    @Test
    void shouldCreateResponseWithMinimalFields() {
        // Given
        String recommendedPace = "1:45.2";
        int recommendedPaceDeciseconds = 1052;
        
        // When
        PacePredictionResponse response = PacePredictionResponse.builder()
            .recommendedPace(recommendedPace)
            .recommendedPaceDeciseconds(recommendedPaceDeciseconds)
            .build();
        
        // Then
        assertNotNull(response);
        assertEquals(recommendedPace, response.getRecommendedPace());
        assertEquals(recommendedPaceDeciseconds, response.getRecommendedPaceDeciseconds());
    }
    
    @Test
    void shouldHaveEqualsAndHashCode() {
        // Given
        PacePredictionResponse response1 = PacePredictionResponse.builder()
            .recommendedPace("1:45.2")
            .recommendedPaceDeciseconds(1052)
            .confidence(0.85)
            .basedOnWorkouts(15)
            .trend(Trend.IMPROVING)
            .build();
        
        PacePredictionResponse response2 = PacePredictionResponse.builder()
            .recommendedPace("1:45.2")
            .recommendedPaceDeciseconds(1052)
            .confidence(0.85)
            .basedOnWorkouts(15)
            .trend(Trend.IMPROVING)
            .build();
        
        // Then
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
    
    @Test
    void shouldHandleAllTrendValues() {
        // Given & When & Then
        assertDoesNotThrow(() -> {
            PacePredictionResponse.builder()
                .recommendedPace("1:45.2")
                .recommendedPaceDeciseconds(1052)
                .trend(Trend.IMPROVING)
                .build();
        });
        
        assertDoesNotThrow(() -> {
            PacePredictionResponse.builder()
                .recommendedPace("1:45.2")
                .recommendedPaceDeciseconds(1052)
                .trend(Trend.DECLINING)
                .build();
        });
        
        assertDoesNotThrow(() -> {
            PacePredictionResponse.builder()
                .recommendedPace("1:45.2")
                .recommendedPaceDeciseconds(1052)
                .trend(Trend.STABLE)
                .build();
        });
    }
}