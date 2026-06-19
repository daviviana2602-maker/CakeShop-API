# 🍰 CakeShop API

REST API for managing users, products and orders, built with Java and Spring Boot, applying backend engineering practices focused on security, maintainability and layered Architecture with separation of concerns.

---

## 📖 About

CakeShop API is a backend system for an e-commerce platform focused on managing products, users and orders.

The project implements concepts commonly used in real-world applications:

* JWT Authentication
* Role-Based Access Control (RBAC)
* Resource Ownership Validation
* DTO-based API contracts
* Global Exception Handling
* Database Versioning
* Automated Testing
* Containerized Environment

---

## 🚀 Technologies

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA (Hibernate)

### Database

* PostgreSQL
* Flyway (Database Migration)

### Infrastructure

* Docker
* Docker Compose

### Documentation

* Swagger / OpenAPI

### Testing

* JUnit 5
* Mockito

---

## 🔐 Security

### Authentication

* User registration
* JWT login
* BCrypt password encryption
* Protected endpoints

### Authorization

Role-based permissions:

* USER
* ADMIN

### Ownership Control

Users can only access resources that belong to them.

The API validates ownership before allowing operations on private resources.

---

## 👤 Users

Features:

* User registration
* Authentication
* Profile management

Users can:

* Update name
* Update email
* Change password
* Delete account

---

## 🛡️ Administration

ADMIN users can:

* Ban users
* Unban users
* Promote users
* Demote users

---

## 🍰 Products

Features:

* Create products
* List products
* Update products
* Delete products

Access rules:

* Only ADMIN users can manage products

---

## 🛒 Orders

Features:

* Create orders
* Associate products with orders
* Manage order status

Available statuses:

* PENDING
* CONCLUDED
* CANCELED

---

## 🧪 Tests

The project contains automated tests using JUnit 5.

Current status:

✔ 51 tests passing consistently

Tests cover business rules and service layer behavior.

---

## ⚠️ Exception Handling

The API uses centralized exception handling with standardized responses.

Handled cases:

* Bad Request (400)
* Unauthorized (401)
* Forbidden (403)
* Not Found (404)
* Conflict (409)
* DTO Validation Errors

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
        └── enums