# 🚣 SmartPace

> Intelligent pace prediction engine for rowing athletes, powered by Concept2 Logbook API

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Overview

SmartPace is a backend microservice designed to enhance training efficiency for rowing athletes. By leveraging historical performance data from the Concept2 Logbook API (splits, watts, heart rate), it delivers personalized training pace recommendations through advanced predictive algorithms.

The system is architected as a **Stateless Calculation Engine** following an API-first approach, ensuring high availability and seamless scalability within the Spring Boot ecosystem.

## Features

### Core Capabilities
- 🎯 **Intelligent Pace Prediction** - Personalized pace recommendations based on historical performance
- 📊 **Performance Analytics** - Moving average and trend analysis algorithms
- 🔗 **Concept2 Integration** - Seamless connection to Concept2 Logbook API via OAuth 2.0
- ⚡ **High Performance** - Stateless architecture optimized for < 500ms response times (P95)
- 🛡️ **Resilient Design** - Circuit breaker, retry mechanisms, and graceful degradation

### Technical Highlights
- RESTful API design with DTO pattern for response isolation
- Reactive HTTP client (Spring WebClient) for external API calls
- OAuth 2.0 passthrough authentication (Bearer token)
- Data transformation utilities (deciseconds ↔ MM:SS/500m)
- Edge case handling: missing data trimming, outlier filtering

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         SmartPace API                           │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────────────┐  │
│  │  Controller │───▶│   Service   │───▶│  Prediction Engine  │  │
│  │    Layer    │    │    Layer    │    │    (Algorithms)     │  │
│  └─────────────┘    └─────────────┘    └─────────────────────┘  │
│         │                  │                      │              │
│         ▼                  ▼                      ▼              │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────────────┐  │
│  │     DTO     │    │  WebClient  │───▶│   Concept2 Logbook  │  │
│  │   Mappers   │    │  (Reactive) │    │        API          │  │
│  └─────────────┘    └─────────────┘    └─────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

## Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 17+** | Core language |
| **Spring Boot 4.x** | Application framework |
| **Spring WebClient** | Reactive HTTP client |
| **Spring Actuator** | Health checks & metrics |
| **Lombok** | Boilerplate reduction |
| **JUnit 5 + Mockito** | Testing framework |
| **Maven** | Build & dependency management |

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- Concept2 Logbook API credentials (for integration)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/smartpace.git
   cd smartpace
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

The service will start on `http://localhost:8080`

### Configuration

Configure the application via `src/main/resources/application.properties`:

```properties
spring.application.name=smartpace

# Server Configuration
server.port=8080

# Concept2 API Configuration
concept2.api.base-url=https://log.concept2.com/api
concept2.api.timeout=3000

# Actuator Endpoints
management.endpoints.web.exposure.include=health,info,metrics
```

## API Reference

### Predict Pace

Returns personalized pace recommendations based on historical workout data.

```http
GET /predict-pace
```

**Headers:**
| Header | Type | Description |
|--------|------|-------------|
| `Authorization` | `string` | **Required**. Bearer token from Concept2 OAuth |

**Query Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `distance` | `integer` | Target distance in meters (e.g., 2000, 5000, 10000) |
| `dateFrom` | `string` | Start date for historical data (ISO 8601) |
| `dateTo` | `string` | End date for historical data (ISO 8601) |

**Response:**
```json
{
  "recommendedPace": "1:45.2",
  "recommendedPaceDeciseconds": 1052,
  "confidence": 0.85,
  "basedOnWorkouts": 15,
  "trend": "IMPROVING",
  "analysis": {
    "averagePace": "1:46.5",
    "bestPace": "1:42.1",
    "recentTrend": -1.3
  }
}
```

### Health Check

```http
GET /actuator/health
```

## Testing

Run the test suite:

```bash
# Run all tests
./mvnw test

# Run with coverage report
./mvnw test jacoco:report
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Concept2](https://www.concept2.com/) for providing the Logbook API
- Spring Boot team for the excellent framework
- The rowing community for inspiration

---

<p align="center">
  Made with ❤️ for the rowing community
</p>
