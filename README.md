# ms-dacs2025-bff

Backend For Frontend (BFF) microservice.

## Description

A Spring Boot application acting as a Backend For Frontend, aggregating and orchestrating data from multiple backend services for the DACS2025 system. Its function is to act as an intermediate layer between the frontend and the different backend services, centralizing integration, authentication, and authorization logic, simplifying API consumption, and improving security and user experience.

## Objective
![Alternative text](assets/infraestructura.png)

## Configuration
[See the infrastructure configuration (PDF)](assets/DACS-configuracion-de-infraestructura.pdf)

## Features
- API Gateway
- Security configuration
- Aggregation of backend services
- Logging

## Architecture & Technology Stack

**Patterns:**
- Backend For Frontend (BFF)
- API Gateway pattern
- Layered architecture (Controller, Service, Repository)
- Security and authentication (Spring Security)
- Dependency Injection (Spring Framework)

**Technology Stack:**
- Java 17+
- Spring Boot
- Maven

## Requirements
- Java 17+
- Maven 3.6+

## Getting Started
1. Clone the repository.
2. Configure your settings in `src/main/resources/application.yml` or `application.properties`.
3. Build the project:
	```bash
	./mvnw clean install
	```
4. Run the application:
	```bash
	./mvnw spring-boot:run
	```

## Project Structure
- `src/main/java` - Source code
- `src/main/resources` - Configuration files
- `assets` - Static assets

## Learning & Experience

This BFF project has provided valuable experience in designing and implementing the Backend For Frontend pattern using Spring Boot. Key learning outcomes include:

- Applying the BFF and API Gateway patterns to centralize integration and security logic.
- Aggregating and orchestrating data from multiple backend services.
- Implementing authentication and authorization with Spring Security.
- Structuring a layered architecture for maintainability and scalability.
- Collaborating in a distributed team and using Git for version control.

These experiences have contributed to a deeper understanding of modern API gateway solutions and secure, scalable backend architectures.




