package com.smartpace.smartpace.client;

import java.util.List;
import java.time.LocalDate;

import com.smartpace.smartpace.client.dto.Concept2WorkoutDto;


public interface Concept2Client {

    List<Concept2WorkoutDto>  getWorkouts(String token, LocalDate from, LocalDate to);

}
