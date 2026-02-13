# Pricing Service

A modern REST API for managing pricing information with a **Hexagonal Architecture** implementation using Spring Boot.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Running Tests](#running-tests)
- [Database](#database)

## 🎯 Overview

The Pricing Service is a Spring Boot application that provides price lookup functionality based on product ID, brand ID, and application date. It returns the highest priority price applicable for the given criteria.

**Key Features:**
- RESTful API for price queries
- Hexagonal architecture for clean code separation
- H2 in-memory database for testing
- Comprehensive integration tests
- LIMIT 1 query optimization

## 🏗️ Architecture

This project follows **Hexagonal Architecture** (Ports & Adapters pattern), separating business logic from infrastructure concerns:

```
┌─────────────────────────────────────────────────────┐
│                                                       │
│  ┌──────────────┐           ┌──────────────┐       │
│  │   Domain     │           │ Application  │       │
│  │              │────────→  │  Use Cases   │       │
│  │  - Model     │           │              │       │
│  │  - Port      │           └──────────────┘       │
│  │  - Exception │                                   │
│  └──────────────┘                                   │
│         ▲                          ▲                │
│         │                          │                │
│    ┌────┴────────────────────────┴────┐            │
│    │    Infrastructure (Inbound/    │            │
│    │       Outbound)               │            │
│    │                                │            │
│    │  - Controllers (HTTP)          │            │
│    │  - Persistence (Database)      │            │
│    └────────────────────────────────┘            │
│                                                       │
└─────────────────────────────────────────────────────┘
```

### Key Principles:
- **Domain Independence**: Core business logic has no Spring dependencies
- **Testability**: Easy unit testing without framework dependencies
- **Flexibility**: Swap implementations without affecting core logic
- **Scalability**: Add new adapters without modifying existing code

## 🛠️ Technology Stack

| Layer | Technology |
|-------|------------|
| **Framework** | Spring Boot 4.0.2 |
| **Language** | Java 21 |
| **Database** | H2 |
| **Build Tool** | Maven |
| **Persistence** | Spring Data JPA with Hibernate |
| **Testing** | JUnit 5, Mockito |
| **Lombok** | For reducing boilerplate |

## 📁 Project Structure

This project uses **Vertical Slicing** organized by feature/domain, with each feature following Hexagonal Architecture:

```
src/main/java/com/zara/pricing/
│
├── prices/                              # Prices feature module
│   │
│   ├── domain/                          # Business logic (framework-agnostic)
│   │   ├── model/
│   │   │   └── Price.java              # Core business entity
│   │   ├── port/
│   │   │   └── PriceRepositoryPort.java # Output port (abstraction)
│   │   └── exception/
│   │       └── PriceNotFoundException.java # Domain exception
│   │
│   ├── application/                     # Application layer (use cases)
│   │   └── usecase/
│   │       └── GetPriceUseCase.java    # Business logic orchestrator
│   │
│   ├── infraestructure/                 # Technical implementations
│   │   ├── inbound/                     # Input adapters (HTTP)
│   │   │   ├── controller/
│   │   │   │   └── PriceController.java # REST endpoint
│   │   │   └── dto/
│   │   │       ├── PriceRequestDTO.java
│   │   │       └── PriceResponseDTO.java
│   │   │
│   │   └── outbound/                    # Output adapters (persistence)
│   │       └── persistence/
│   │           ├── PriceRepositoryAdapter.java # Port implementation
│   │           ├── PriceJpaRepository.java     # Spring Data JPA
│   │           └── PriceJpaEntity.java         # JPA entity
│
└── PricingApplication.java              # Bootstrap class

src/main/resources/
├── application.properties               # Spring configuration
├── schema.sql                           # Database schema
└── data.sql                             # Initial test data

src/test/java/
└── com/zara/pricing/prices/
    └── PriceControllerIntegrationTest.java  # Integration tests
```

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.8+

### Clone and Build

```bash
# Clone the repository
git clone <repository-url>
cd test

# Build the project
mvn clean install
```

### Run the Application

```bash
# Run the Spring Boot application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Access H2 Console

The H2 console is available at:
```
http://localhost:8080/h2-console
```

**Credentials:**
- URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (empty)

## 📡 API Endpoints

### Get Applicable Price

**Endpoint:**
```
POST /api/prices
```

**Request Body:**
```json
{
  "applicationDate": "2020-06-14T10:00:00",
  "productId": 35455,
  "brandId": 1
}
```

**Response (200 OK):**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "finalPrice": 35.50
}
```

**Error Response (404 Not Found):**
```json
{
  "timestamp": "2026-02-11T17:53:04.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "No price found for the given criteria",
  "path": "/api/prices"
}
```

### Query Logic

The endpoint returns the highest priority price where:
- `productId` matches the request
- `brandId` matches the request
- The `applicationDate` falls between `startDate` and `endDate`
- Results are ordered by `priority` DESC
- Only the first result is returned (LIMIT 1)

## 🧪 Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=PriceControllerIntegrationTest
```

### Run with Coverage
```bash
mvn test jacoco:report
```

### Test Cases

The integration test suite includes 5 test cases:

1. **Test 1: 14-06 10:00** - Price lookup at specific time
2. **Test 2: 14-06 16:00** - Different time on same day
3. **Test 3: 15-06 09:00** - Different day
4. **Test 4: 16-06 21:00** - Different date range
5. **Test 5: 01-07 00:00** - Extended date range

All tests verify correct price list ID and final price amount.

## 🗄️ Database

### Schema

The application uses an H2 in-memory database with the following schema:

```sql
CREATE TABLE PRICES (
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    BRAND_ID BIGINT,
    START_DATE TIMESTAMP,
    END_DATE TIMESTAMP,
    PRICE_LIST INTEGER,
    PRODUCT_ID BIGINT,
    PRIORITY INTEGER,
    PRICE DECIMAL(10,2),
    CURR VARCHAR(3)
);
```

### Initial Data

Test data is loaded from `data.sql` containing:
- Product ID: 35455
- Brand ID: 1
- Multiple price entries with varying date ranges and priorities
- Currency: EUR

## 🔄 Data Flow

```
HTTP Request
    ↓
PriceController (Adapter)
    ↓
GetPriceUseCase (Application)
    ↓
PriceRepositoryPort (Domain Port)
    ↓
PriceRepositoryAdapter (Adapter Implementation)
    ↓
PriceJpaRepository (Spring Data)
    ↓
H2 Database
    ↓
Response DTO
    ↓
HTTP Response
```

## 📦 Dependencies

Key Maven dependencies:
- `spring-boot-starter-web` - Web framework
- `spring-boot-starter-data-jpa` - ORM framework
- `spring-boot-starter-test` - Testing framework
- `spring-boot-h2console` - H2 database console
- `lombok` - Annotation processing for boilerplate

## 🎓 Learning Resources

### Hexagonal Architecture
- [Alistair Cockburn - Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Spring on Hexagonal Architecture](https://spring.io/)

### Spring Boot
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)

## 📝 License

This project is part of the Zara Abilities Test.

## 👨‍💻 Development

### Build Commands

```bash
# Clean build
mvn clean install

# Skip tests
mvn clean install -DskipTests

# Run application
mvn spring-boot:run

# Build Docker image (if configured)
mvn docker:build
```

### IDE Configuration

**IntelliJ IDEA:**
- Enable annotation processing for Lombok
- Set language level to Java 21
- Configure Maven to use Java 21

**Eclipse:**
- Install Lombok plugin
- Set language compliance to Java 21

## 🐛 Troubleshooting

### Issue: Tests fail with port already in use
**Solution:** Change `server.port` in `application.properties`

### Issue: H2 console not accessible
**Solution:** Verify `spring.h2.console.enabled=true` in properties

### Issue: Query returns no results
**Solution:** Check that:
- Product ID and Brand ID exist in database
- Application date falls within price date range
- Database has been populated with `data.sql`

---

**Last Updated:** February 11, 2026  
**Version:** 0.0.1-SNAPSHOT
