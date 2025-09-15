# Spring Boot API Documentation

This document provides information about the Spring Boot API included in the DevOps Deployment System.

## Overview

The Spring Boot API can be accessed at [http://localhost:8080](http://localhost:8080), which provides a welcome page with links to all available endpoints.

## API Endpoints

- `GET /`: Hello World

## Monitoring Endpoints

The API leverages Spring Boot Actuator for monitoring and health checks:

- `GET /actuator/health`: Health status of the application
- `GET /actuator/info`: Application information
- `GET /actuator/prometheus`: Prometheus metrics for monitoring

## Technical Details

- Built using Java 21 and Spring Boot 3.2.0
- Containerized using Docker
- Configured with health checks and monitoring endpoints
- Exposes RESTful endpoints for application functionality
- Exposes metrics via Spring Boot Actuator for monitoring