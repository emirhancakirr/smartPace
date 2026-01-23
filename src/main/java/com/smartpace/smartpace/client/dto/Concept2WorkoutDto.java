// src/main/java/com/smartpace/smartpace/client/dto/Concept2WorkoutDto.java
package com.smartpace.smartpace.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Concept2WorkoutDto {
    
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  
    private LocalDateTime date;
    
    @JsonProperty("distance")
    private Integer distance;
    
    @JsonProperty("time")
    private Integer time;
    
    @JsonProperty("split")
    private Integer split;
    
    @JsonProperty("watts")
    private Integer watts;
    
    @JsonProperty("heart_rate")
    private Integer heartRate;


    public Integer getSplit() {
        if (split != null) {
            return split;
        }
        if (time != null && distance != null && distance > 0) {
            return (time * 500) / distance;
        }
        return null;
    }
}