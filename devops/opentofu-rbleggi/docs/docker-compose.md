# Docker Compose Implementation

This document provides information about the Docker Compose implementation of the DevOps Deployment System.

## Overview

Docker Compose is used for local development and testing only. For production or staging, use Kubernetes, Helm, and OpenTofu as described in the main [README](../README.md) and [Kubernetes Deployment](kubernetes.md).

## Prerequisites

- Docker
- Docker Compose

## Running the System

1. Start all services (rebuild images if necessary):
    ```sh
    docker-compose up -d --build
    ```

2. Check the status of the services:
    ```sh
    docker-compose ps
    ```

3. View logs for a specific service:
    ```sh
    docker-compose logs [service_name]
    ```
   For example:
    ```sh
    docker-compose logs api
    docker-compose logs jenkins
    ```

4. Stop all services:
    ```sh
    docker-compose down -v --remove-orphans
    ```

## Accessing the Services

After starting the Docker Compose environment, you can access the services at the following URLs:

- API: [http://localhost:8080](http://localhost:8080)
- Jenkins: [http://localhost:8081](http://localhost:8081)
- Prometheus: [http://localhost:9090](http://localhost:9090)
- Grafana: [http://localhost:3000](http://localhost:3000)

## Notes

- All services are started in a single network for local development.
- For production, use the Kubernetes/Helm/OpenTofu workflow as described in the documentation.
- Integration with Prometheus and Grafana is enabled by default for all applications.

For more details, [Kubernetes Deployment](kubernetes.md) docs.