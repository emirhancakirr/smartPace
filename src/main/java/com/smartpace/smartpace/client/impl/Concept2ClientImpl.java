package com.smartpace.smartpace.client.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.smartpace.smartpace.client.Concept2Client;
import com.smartpace.smartpace.client.dto.Concept2WorkoutDto;
import com.smartpace.smartpace.config.Concept2ApiProperties;
import com.smartpace.smartpace.exception.Concept2ApiException;

@Service
public class Concept2ClientImpl implements Concept2Client {

    private WebClient webClient;
    private Concept2ApiProperties properties;

    public Concept2ClientImpl(WebClient webClient, Concept2ApiProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    @Override
    public List<Concept2WorkoutDto> getWorkouts(String token, LocalDate from, LocalDate to) {

        String uri = properties.getBaseUrl() + "/users/me/results?from=" + from + "&to=" + to;

        return webClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .defaultIfEmpty("")
                        .flatMap(body -> response.createException())
                        .map(ex -> new Concept2ApiException("Concept2 API error: " + response.statusCode(), ex)))
                .bodyToMono(new ParameterizedTypeReference<List<Concept2WorkoutDto>>() {
                })
                .block();
    }
}
