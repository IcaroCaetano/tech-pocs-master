# Scala HTTP Server

## Overview

This project implements a simple and extensible HTTP server in Scala 3 using the Strategy Pattern. It supports custom GET endpoints and demonstrates how to compose route handlers in a clean, extensible way.

---

## **Tech Stack**

- **Scala 3** → Modern JVM-based language with advanced type safety and functional programming features.
- **SBT** → Scala's official build tool.
- **JDK 21** → Required to run the application.

---

## Features
- **GET endpoints**: Easily add new GET routes
- **Strategy Pattern**: Add new route handlers by implementing the `GetRouteHandler` trait
- **Simple CLI**: Run the server from the command line
- **Extensible**: Add new endpoints without changing the server core

---

## Architecture Diagram

```mermaid
classDiagram
    direction TB

    class GetRouteHandler {
        <<trait>>
        +handle(path: String): Option[String]
    }

    class HelloHandler {
        +handle(path: String): Option[String]
    }

    class PingHandler {
        +handle(path: String): Option[String]
    }

    class Main {
        +run(): Unit
    }

    GetRouteHandler <|.. HelloHandler
    GetRouteHandler <|.. PingHandler
    Main --> GetRouteHandler
```

---

## Implementation Details

- The server uses the `GetRouteHandler` trait, which defines the method `handle(path: String): Option[String]`.
- Concrete implementations (e.g., `HelloHandler`, `PingHandler`) handle specific GET endpoints.
- The `run` method starts the server, composes handlers, and dispatches requests to the appropriate handler.
- To add new endpoints, implement the `GetRouteHandler` trait and add your handler to the list in `run`.

---

## **Setup Instructions**

### **1️ - Clone the Repository**

```bash
git clone https://github.com/rbleggi/tech-pocs.git
cd scala-3/http-server
```

### **2️ - Compile & Run the Application**

```shell
./sbtw compile run
```

### **3️ - Run Tests**

```shell
./sbtw compile test
```
