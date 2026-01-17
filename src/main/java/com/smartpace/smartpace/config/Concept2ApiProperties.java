package com.smartpace.smartpace.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "concept2.api")
@Validated
public class Concept2ApiProperties {

    private String baseUrl;
    private int timeout;
    private Retry retry = new Retry();

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Retry getRetry() {
        return retry;
    }

    public void setRetry(Retry retry) {
        this.retry = retry;
    }

    public static class Retry {
        private int maxAttempts;
        private int initialInterval;

        public int getMaxAttempts() {
            return maxAttempts;
        }

        public void setMaxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
        }

        public int getInitialInterval() {
            return initialInterval;
        }

        public void setInitialInterval(int initialInterval) {
            this.initialInterval = initialInterval;
        }
    }
}
