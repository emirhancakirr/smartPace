package com.smartpace.smartpace.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Domain model representing a rowing workout.
 * Immutable value object containing workout metrics.
 */
@Value
@Builder
public class Workout {
    
    /**
     * Date and time when the workout was performed
     */
    LocalDateTime date;
    
    /**
     * Total distance covered in meters
     */
    Integer distance;
    
    /**
     * Average split time per 500m in deciseconds.
     * Example: 1052 = 1 minute 45.2 seconds per 500m
     */
    Integer split;
    
    /**
     * Average power output in watts
     */
    Integer watts;
    
    /**
     * Average heart rate in beats per minute (BPM).
     * Optional - may be null if heart rate monitor was not used
     */
    Integer heartRate;
}