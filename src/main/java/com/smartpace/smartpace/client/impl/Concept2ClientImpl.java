package com.smartpace.smartpace.client.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.smartpace.smartpace.client.Concept2Client;
import com.smartpace.smartpace.client.dto.Concept2WorkoutDto;
import com.smartpace.smartpace.config.Concept2ApiProperties;
import com.smartpace.smartpace.exception.Concept2ApiException;
import com.smartpace.smartpace.exception.Concept2TimeoutException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class Concept2ClientImpl implements Concept2Client {

    private WebClient webClient;
    private Concept2ApiProperties properties;
    private final CircuitBreaker circuitBreaker;

    public Concept2ClientImpl(WebClient webClient, Concept2ApiProperties properties, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.webClient = webClient;
        this.properties = properties;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("concept2");
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
                        .map(ex -> new Concept2ApiException("Concept2 API error: " + response.statusCode(), ex,
                                response.statusCode().value()))
                        .flatMap(Mono::error))
                .bodyToMono(new ParameterizedTypeReference<List<Concept2WorkoutDto>>() {
                })

                .retryWhen(Retry.backoff(properties.getRetry().getMaxAttempts(),
                        Duration.ofMillis(properties.getRetry().getInitialInterval())))
                .filter(throwable -> {
                    if (throwable instanceof Concept2ApiException ex) {
                        return ex.getStatusCode() >= 500;
                    }
                    return true;
                })
                .onErrorMap(throwable -> {
                    if (throwable.getClass().getName().contains("RetryExhaustedException") && throwable.getCause() != null) {
                        return throwable.getCause();
                    }
                    return throwable;
                })
                .onErrorMap(throwable -> isTimeout(throwable)
                        ? new Concept2TimeoutException(
                                "Concept2 API request timed out after " + properties.getTimeout() + "ms",
                                throwable,
                                504)
                        : throwable)

                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .block();
    }

    private static boolean isTimeout(Throwable throwable) {
        Throwable t = throwable;
        while (t != null) {
            String name = t.getClass().getName();
            if (t instanceof TimeoutException
                    || name.contains("TimeoutException")
                    || name.contains("ReadTimeoutException")
                    || name.contains("WriteTimeoutException")) {
                return true;
            }
            t = t.getCause();
        }
        return false;
    }

}
