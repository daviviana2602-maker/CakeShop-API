# 🍰 CakeShop API

REST API for an e-commerce platform built with **Java 21** and **Spring Boot**, following a layered architecture and backend engineering best practices focused on security, maintainability and scalability.

## 🚀 Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA (Hibernate)
* PostgreSQL
* Redis
* Flyway
* Docker & Docker Compose
* Swagger / OpenAPI
* JUnit 5
* Mockito
* GitHub Actions 
* Resend API

---

## ✨ Features

### Authentication & Security

* JWT Authentication with **Access Token** and **Refresh Token**
* BCrypt password hashing
* Global exception handling
* DTO validation
* Protected endpoints with Spring Security

### Authorization

* Role-Based Access Control (RBAC)
* USER and ADMIN roles
* Ownership validation to ensure users can only access their own resources

### User Management

* User registration
* Login
* Profile update
* Password change
* Account deletion
* Secure email change flow

### Email Verification

Account creation requires email verification before activation.

Email verification is also required when changing the user's email address.

Implemented through integration with the **Resend API**, using verification tokens with expiration.

### Product Management

* Create products
* Update products
* Delete products
* Paginated product listing with Redis

Only ADMIN users can manage products.

### Order Management

* Create orders
* Associate products with orders
* Order status management

Supported statuses:

* PENDING
* CONCLUDED
* CANCELED

### Administration

ADMIN users can:

* Promote users
* Demote users
* Activate accounts
* Deactivate accounts

---

## ⚡ Redis

Redis is used to improve application performance through caching.

Current implementation includes:

* Product cache
* Paginated product listing cache

---

## 🧪 Automated Tests

* 48 automated tests
* JUnit 5
* Mockito

Tests cover business rules and service layer behavior.

---

## 🔄 Continuous Integration

GitHub Actions automatically validates every push by:

* Running the automated test suite
* Rebuilding the application
* Building Docker containers

This guarantees that the project remains buildable and tests continue passing after every commit.

---

## 📚 API Documentation

Interactive API documentation is available through Swagger / OpenAPI.

---

## 🏗️ Architecture

Layered architecture with clear separation of responsibilities.

```text
src
└── main
    └── java
        ├── controller
        ├── service
        ├── repository
        ├── entity
        ├── security
        ├── dto
        │   ├── request
        │   └── response
        ├── exception
        ├── enums
        ├── normalization
        └── config
```


---

## 🏗 Architecture

Layered architecture with separation of responsibilities:

```text
src
└── main
    └── java
        ├── controller
        ├── service
        ├── repository
        ├── entity
        ├── security
        ├── dto
        │   ├── request
        │   └── response
        ├── exception
        ├── enums
        ├── config
        └── normalization