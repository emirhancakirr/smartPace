package com.smartpace.smartpace.client.impl;

import com.smartpace.smartpace.client.Concept2Client;
import com.smartpace.smartpace.client.dto.Concept2WorkoutDto;
import com.smartpace.smartpace.config.Concept2ApiProperties;
import com.smartpace.smartpace.exception.Concept2ApiException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Concept2ClientImplTest {

    private static final String BASE_URL = "https://log.concept2.com/api";
    private static final String TOKEN = "test-token";

    private Concept2ApiProperties properties;
    private WebClient webClient;
    private CapturingExchangeFunction exchangeFunction;
    private Concept2Client client;

    @BeforeEach
    void setUp() {
        properties = new Concept2ApiProperties();
        properties.setBaseUrl(BASE_URL);
        exchangeFunction = new CapturingExchangeFunction();
        webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .exchangeFunction(exchangeFunction)
                .build();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();
        client = new Concept2ClientImpl(webClient, properties, registry);
    }

    @Test
    void getWorkouts_success_returnsMappedList() {
        String json = """
                [
                    {"date":"2024-01-15T10:30:00","distance":2000,"time":4200,"split":1050,"watts":250,"heart_rate":165}
                ]
                """;
        exchangeFunction.setResponse(okResponse(json));

        List<Concept2WorkoutDto> result = client.getWorkouts(TOKEN, LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2000, result.get(0).getDistance());
        assertEquals(4200, result.get(0).getTime());
        assertEquals(165, result.get(0).getHeartRate());
    }

    @Test
    void getWorkouts_sendsBearerToken() {
        exchangeFunction.setResponse(okResponse("[]"));

        client.getWorkouts(TOKEN, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));

        assertEquals("Bearer " + TOKEN, exchangeFunction.getRequest().headers().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void getWorkouts_includesDateRangeInUri() {
        exchangeFunction.setResponse(okResponse("[]"));
        LocalDate from = LocalDate.of(2024, 2, 1);
        LocalDate to = LocalDate.of(2024, 2, 29);

        client.getWorkouts(TOKEN, from, to);

        String uri = exchangeFunction.getRequest().url().toString();
        assertTrue(uri.contains("2024-02-01") && uri.contains("2024-02-29"));
    }

    @Test
    void getWorkouts_401_throwsConcept2ApiException() {
        exchangeFunction.setResponse(ClientResponse.create(HttpStatus.UNAUTHORIZED).build());

        assertThrows(Concept2ApiException.class,
                () -> client.getWorkouts(TOKEN, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)));
    }

    @Test
    void getWorkouts_500_throwsConcept2ApiException() {
        exchangeFunction.setResponse(ClientResponse.create(HttpStatus.INTERNAL_SERVER_ERROR).build());

        assertThrows(Concept2ApiException.class,
                () -> client.getWorkouts(TOKEN, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)));
    }

    @Test
    void getWorkouts_emptyResponse_returnsEmptyList() {
        exchangeFunction.setResponse(okResponse("[]"));

        List<Concept2WorkoutDto> result = client.getWorkouts(TOKEN, LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private static ClientResponse okResponse(String json) {
        return ClientResponse.create(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(ignored -> Flux.just(jsonToBuffer(json)))
                .build();
    }

    private static DataBuffer jsonToBuffer(String json) {
        return new DefaultDataBufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
    }

    private static final class CapturingExchangeFunction implements ExchangeFunction {
        private org.springframework.web.reactive.function.client.ClientRequest request;
        private ClientResponse response;

        void setResponse(ClientResponse response) {
            this.response = response;
        }

        org.springframework.web.reactive.function.client.ClientRequest getRequest() {
            return request;
        }

        @Override
        public reactor.core.publisher.Mono<ClientResponse> exchange(
                org.springframework.web.reactive.function.client.ClientRequest request) {
            this.request = request;
            return reactor.core.publisher.Mono.just(response);
        }
    }
}