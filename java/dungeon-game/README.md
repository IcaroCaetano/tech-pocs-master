# Dungeon Game REST API

A **Spring Boot 3.5.4** REST API that solves the [Dungeon Game problem](https://leetcode.com/problems/dungeon-game/), supports **A/B testing experiments**, and persists results in **PostgreSQL 17**. The application is fully containerized with **Docker** and uses **Flyway** for database migrations.

---

## A/B Testing

This project implements A/B testing to compare different solution strategies for the Dungeon Game problem. Each API request is assigned to an experiment variant (e.g., Variant A or B) using a deterministic assignment strategy based on the player ID. The assignment logic and experiment configuration are managed in:
- `experiment/ExperimentAssigner.java`

Key aspects:
- **Experiment assignment:** Each player is assigned to a variant using a hash-based split, ensuring consistent exposure.
- **Variant logic:** Different solution algorithms (e.g., 1D vs. 2D dynamic programming) are tested for effectiveness.
- **Exposure tracking:** All experiment exposures and results are persisted in the database for later analysis.
- **Configuration:** Experiment splits and variants are configurable via `application.yaml`.

This enables data-driven evaluation of algorithm performance and supports future experimentation.

![img_2.png](img_2.png)
![img.png](img.png)
![img_1.png](img_1.png)
---

## Features

- Solve the Dungeon Game problem using **dynamic programming (1D & 2D approaches)**.
- REST API for solving dungeons and returning minimum health required.
- **A/B testing** with configurable experiment splits.
- Persistent storage with **PostgreSQL**:
    - Dungeon solutions
    - Experiment exposures
- Flyway-based migrations for database version control.
- Fully containerized with Docker + Docker Compose.
- Configurable via `application.yaml`.

---

## Tech Stack

- Java 23
- Spring Boot 3.5.4
- PostgreSQL 17
- Hibernate / JPA
- Flyway for migrations
- Docker & Docker Compose
- Gradle build system

---

## Prerequisites

- Docker 23+
- Docker Compose 2+
- Java 23 (for local dev, optional if using Docker)
- Gradle 8+ (optional, Docker builds include Gradle wrapper)

---

## Setup and Run with Docker Compose

1. **Clone the repository**

```bash
git clone https://github.com/rbleggi/tech-pocs.git
cd java/dungeon-game
```

2. **Build and start containers**

```bash
docker compose up --build
```

3. **Clean all containers**
```bash
docker compose down --volumes 
```

* PostgreSQL 17 container starts.
* Flyway automatically applies migrations.
* Dungeon Game REST API starts on localhost:8080

## Run Tests

To run all unit and integration tests:

**Linux/macOS:**
```bash
./gradlew test
```
**Windows:**
```bat
gradlew.bat test
```

Test reports are generated in `build/reports/tests/test/index.html`.

## Monitoring & Metrics

### Prometheus
Prometheus is available at [http://localhost:9091](http://localhost:9091) after starting the containers.

### Grafana
Grafana is available at [http://localhost:3000](http://localhost:3000) after starting the containers.
Default login: **admin/admin**.

Dashboards and datasources are provisioned automatically from the `grafana/provisioning` folder.

## Test the API
```
curl -X POST -H "Content-Type: application/json" \
     -d '{"playerId":"player1","dungeon":[[0,-3],[-10,0]]}' \
     http://localhost:8080/api/dungeon/solve
```

Expected response:
````json
{
  "playerId": "player1",
  "minHealthRequired": 7,
  "variant": "A"
}
````

