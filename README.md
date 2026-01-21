# PEXP – Experience Sharing Platform (Backend)

PEXP is a backend REST API built with Spring Boot for sharing real-world experiences like internships, workplace roles, college projects, and presentations.

This project was built mainly to practice real backend engineering concepts: authentication, authorization, pagination, filtering, Docker, CI/CD, and cloud deployment. There is no frontend yet – everything is API-driven and documented via Swagger.

---

## Live Demo

Swagger UI (No frontend just used for documentation of available endpoints in my project)

(deployment currently paused because Railway free credits expired):

https://pexp-production.up.railway.app/swagger-ui/index.html


## Tech Stack

- Java 21 (Temurin)
- Spring Boot 3.3.5
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Docker + Docker Compose
- GitHub Actions (CI/CD)
- Railway (Cloud deployment)
- Bucket4j (Rate limiting)
- Swagger / OpenAPI
- Spring Actuator

---

## What This Project Does

- Users can register and login using JWT authentication.
- Authenticated users can create, update, and delete experience posts.
- Admin users can manage any post.
- Posts support pagination, sorting, filtering, and keyword search.
- APIs are rate limited to prevent abuse.
- Global exception handling returns consistent error responses.
- APIs are documented using Swagger.
- The application is containerized and deployed to the cloud.

---

## Running Locally

### Prerequisites
- Java 21
- Maven
- Docker

### Run with Docker Compose

docker-compose up -d


This starts:
- MySQL database
- Spring Boot API

Swagger UI:
http://localhost:8080/swagger-ui/index.html


4. Access secured endpoints.

---

## Pagination & Search

The main posts endpoint supports:
- Page number
- Page size
- Sorting field
- Category filter
- Keyword search

This was implemented using Spring Data JPA `Pageable` and custom repository methods.

---

## CI/CD

A GitHub Actions pipeline is configured to:
- Build the project using Maven
- Run tests
- Build the Docker image

This helped catch build issues early and ensured the project stays deployable.

---

## Deployment

The application was deployed on Railway using Docker.

Environment variables such as database credentials and JWT secrets are configured in the Railway dashboard.

Deployment is currently paused to avoid consuming free credits.

---

## Issues Faced During Development

Some real problems I ran into while building this:

- MySQL container networking issues when connecting from Spring Boot.
- JWT filter order causing authentication failures.
- Actuator endpoints returning 403 until security rules were updated.
- EntityManager initialization failures in cloud deployment due to missing environment variables.
- Rate limiting filter breaking application startup because of incorrect bean wiring.
- Docker image build failures in CI because of missing secrets.

All of these were fixed by debugging logs, validating configuration, and improving the setup step by step.

---

## Possible Improvements

- Redis Caching
- Frontend UI (React or Angular)
- User profiles and avatars
- Comments and reactions on posts
- Redis caching
- Kubernetes deployment
- Monitoring dashboards (Prometheus/Grafana)

---

