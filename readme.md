# Personal Garage Manager

**Personal Car Management System** — Final Project.

Allows car owners to track:
- Vehicles (brand, model, year, mileage, VIN)
- Maintenance history (service records)
- Parts catalog
- Expenses (fuel, insurance, etc.)
- Multi-user access with roles

## Technologies

- **Spring Boot**
- **Spring Data JPA** + Hibernate
- **Spring Security** (Basic Auth + role-based access)
- **PostgreSQL**
- **Liquibase** (migrations + initial data)
- **MapStruct** (Entity ↔ DTO mapping)
- **Lombok**
- **JUnit + Mockito** (unit tests)
- Java 24

## Application Architecture

Clean layered architecture:
- models — entities
- repositories — Spring Data JPA
- services + services.impl — business logic
- mapper — MapStruct mappers
- dto — Request/Response DTOs organized by folders (vehicle, maintenance, part, expense, user)
- api — REST controllers
- config — SecurityConfig

## User Roles

 Role Permissions                                                                   
 **ADMIN** Full access + create/block/delete users                                       
 **OWNER** Add vehicles, expenses, maintenance records (default role on registration)    
 **MECHANIC** Add maintenance records and parts                                             

## How to Run the Project

### 1. Requirements
- PostgreSQL (local or Docker)
- Java 24
- Gradle

### 2. Database Setup
Create the database in PostgreSQL by typing:

CREATE DATABASE garage_db;