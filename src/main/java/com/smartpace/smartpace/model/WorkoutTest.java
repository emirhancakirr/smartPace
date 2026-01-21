// src/test/java/com/smartpace/smartpace/model/WorkoutTest.java
package com.smartpace.smartpace.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class WorkoutTest {

    @Test
    void shouldCreateWorkoutWithAllFields() {
        // Given
        LocalDateTime date = LocalDateTime.of(2024, 1, 15, 10, 30);
        int distance = 2000; // meters
        int split = 1052; // deciseconds (1:45.2)
        int watts = 250;
        int heartRate = 165;

        // When
        Workout workout = Workout.builder()
                .date(date)
                .distance(distance)
                .split(split)
                .watts(watts)
                .heartRate(heartRate)
                .build();

        // Then
        assertNotNull(workout);
        assertEquals(date, workout.getDate());
        assertEquals(distance, workout.getDistance());
        assertEquals(split, workout.getSplit());
        assertEquals(watts, workout.getWatts());
        assertEquals(heartRate, workout.getHeartRate());
    }

    @Test
    void shouldCreateWorkoutWithOptionalFields() {
        // Given - heartRate optional olabilir
        LocalDateTime date = LocalDateTime.of(2024, 1, 15, 10, 30);
        int distance = 2000;
        int split = 1052;
        int watts = 250;

        // When
        Workout workout = Workout.builder()
                .date(date)
                .distance(distance)
                .split(split)
                .watts(watts)
                .build();

        // Then
        assertNotNull(workout);
        assertNull(workout.getHeartRate()); // Optional field
    }

    @Test
    void shouldHaveEqualsAndHashCode() {
        // Given
        LocalDateTime date = LocalDateTime.of(2024, 1, 15, 10, 30);
        Workout workout1 = Workout.builder()
                .date(date)
                .distance(2000)
                .split(1052)
                .watts(250)
                .heartRate(165)
                .build();

        Workout workout2 = Workout.builder()
                .date(date)
                .distance(2000)
                .split(1052)
                .watts(250)
                .heartRate(165)
                .build();

        // Then
        assertEquals(workout1, workout2);
        assertEquals(workout1.hashCode(), workout2.hashCode());
    }
}