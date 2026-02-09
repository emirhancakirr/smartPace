package com.smartpace.smartpace.config;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
    
    private final Concept2ApiProperties properties;
    
    public WebClientConfig(Concept2ApiProperties properties) {
        this.properties = properties;
    }
    
    @Bean
    public WebClient concept2WebClient() {
        HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofMillis(properties.getTimeout()));
        
        return WebClient.builder()
            .filter(logRequestResponse())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    private static ExchangeFilterFunction logRequestResponse() {
        Logger log = LoggerFactory.getLogger(WebClientConfig.class);
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            if (log.isDebugEnabled()) {
                log.debug("Concept2 API request: {} {}", request.method(), request.url());
            }
            return Mono.just(request);
        }).andThen(ExchangeFilterFunction.ofResponseProcessor(response -> {
            if (log.isDebugEnabled()) {
                log.debug("Concept2 API response: status={}", response.statusCode());
            }
            return Mono.just(response);
        }));
    }
}