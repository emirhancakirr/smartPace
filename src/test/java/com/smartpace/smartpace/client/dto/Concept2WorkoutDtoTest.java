// src/test/java/com/smartpace/smartpace/client/dto/Concept2WorkoutDtoTest.java
package com.smartpace.smartpace.client.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class Concept2WorkoutDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    void shouldDeserializeFromJson() throws Exception {
        // Given - Concept2 API response format
        String json = """
                {
                    "date": "2024-01-15T10:30:00",
                    "distance": 2000,
                    "time": 4200,
                    "split": 1052,
                    "watts": 250,
                    "heart_rate": 165
                }
                """;

        // When
        Concept2WorkoutDto dto = objectMapper.readValue(json, Concept2WorkoutDto.class);

        // Then
        assertNotNull(dto);
        assertNotNull(dto.getDate());
        assertEquals(2000, dto.getDistance());
        assertEquals(4200, dto.getTime());
        assertEquals(1052, dto.getSplit());
        assertEquals(250, dto.getWatts());
        assertEquals(165, dto.getHeartRate());
    }

    @Test
    void shouldHandleMissingOptionalFields() throws Exception {
        // Given - JSON without optional fields
        String json = """
                {
                    "date": "2024-01-15T10:30:00",
                    "distance": 2000,
                    "time": 4200
                }
                """;

        // When
        Concept2WorkoutDto dto = objectMapper.readValue(json, Concept2WorkoutDto.class);

        // Then
        assertNotNull(dto);
        assertEquals(2000, dto.getDistance());
        assertEquals(1050, dto.getSplit()); // Calculated from time + distance
        assertNull(dto.getWatts()); // Optional field
        assertNull(dto.getHeartRate()); // Optional field
    }

    @Test
    void shouldCalculateSplitFromTimeAndDistance() {
        // Given
        Concept2WorkoutDto dto = Concept2WorkoutDto.builder()
                .date(LocalDateTime.now())
                .distance(2000)
                .time(4200) // 420 seconds = 7 minutes
                .build();

        // When
        Integer calculatedSplit = dto.getSplit();

        // Then
        // split = (time / distance) * 500
        // split = (4200 / 2000) * 500 = 1050 deciseconds
        assertNotNull(calculatedSplit);
        assertEquals(1050, calculatedSplit);
    }

    @Test
    void shouldUseProvidedSplitIfAvailable() {
        // Given
        Concept2WorkoutDto dto = Concept2WorkoutDto.builder()
                .date(LocalDateTime.now())
                .distance(2000)
                .time(4200)
                .split(1052) // Explicit split provided
                .build();

        // When
        Integer split = dto.getSplit();

        // Then
        assertEquals(1052, split); // Should use provided value, not calculated
    }

    @Test
    void shouldHandleZeroDistance() {
        // Given
        Concept2WorkoutDto dto = Concept2WorkoutDto.builder()
                .date(LocalDateTime.now())
                .distance(0)
                .time(0)
                .build();

        // When
        Integer split = dto.getSplit();

        // Then
        assertNull(split); // Should not calculate if distance is 0
    }

    @Test
    void shouldDeserializeWithSnakeCase() throws Exception {
        // Given - Concept2 API uses snake_case
        String json = """
                {
                    "date": "2024-01-15T10:30:00",
                    "distance": 2000,
                    "heart_rate": 165
                }
                """;

        // When
        Concept2WorkoutDto dto = objectMapper.readValue(json, Concept2WorkoutDto.class);

        // Then
        assertEquals(165, dto.getHeartRate()); // Should map heart_rate to heartRate
    }
}