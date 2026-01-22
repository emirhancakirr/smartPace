package com.smartpace.smartpace.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class Workout {
    

    LocalDateTime date;
    Integer distance;
    //it is defined in the minute per 500m
    Integer split;
    Integer watts;
    Integer heartRate;
}