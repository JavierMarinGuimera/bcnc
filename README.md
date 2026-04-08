# Pricing Service

A REST service implemented with Spring Boot that exposes a price lookup API using a hexagonal architecture and vertical slicing.

## 🧩 Technologies used in this project

- Java 21
- Spring Boot 4.0.2
- Spring Web MVC
- Spring Data JPA
- Hibernate
- H2 Database (in-memory)
- Maven
- Lombok
- JUnit 5
- Spring Boot Test

## 🏗️ Project architecture

The project follows a **Hexagonal Architecture** approach with **Vertical Slicing**:
- `domain/`: business model, ports, and exceptions.
- `application/`: use cases and business orchestration.
- `infraestructure/inbound/`: input adapters (REST controllers and DTOs).
- `infraestructure/outbound/`: output adapters (persistence with JPA).

## 🚀 How to run the project

### Requirements
- Java 21 or higher
- Maven 3.8+

### Clone and build

```bash
git clone <repository-url>
cd test
mvn clean install
```

### Run the application

```bash
mvn spring-boot:run
```

The application starts at `http://localhost:8080`

### Access the H2 console

The H2 console is available at:
```
http://localhost:8080/h2-console
```

**Credentials:**
- URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (empty)

## 📡 Available endpoints

### Get all prices

**Endpoint:**
```
GET /prices
```

**Response (200 OK):**
```json
[
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 1,
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T23:59:59",
    "finalPrice": 35.50
  }
]
```

### Get applicable price by criteria

**Endpoint:**
```
GET /prices/get-price
```

**Request body:**
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

> Note: the current implementation uses `GET` with a JSON body for `/prices/get-price`.

## 🧪 Tests

This project includes integration tests using Spring Boot Test.

```bash
mvn test
```

## 💾 Database

The project uses an in-memory H2 database for development and tests. Initial data is loaded from `src/main/resources/data.sql` and the schema is defined in `src/main/resources/schema.sql`.
