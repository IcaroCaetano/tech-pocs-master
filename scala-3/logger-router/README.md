# **Logger Router**

## **Overview**

This project implements a **lightweight and flexible log routing system** using the **Strategy Pattern**. Logs can be
sent to multiple destinations like **FileSystem**, **ELK**, or **Console**, with support for **synchronous or
asynchronous delivery** — all through a simple, unified interface.

---

## **Tech Stack**

- **Scala 3** → Modern JVM-based language with advanced type safety and functional programming features.
- **SBT** → Scala's official build tool.
- **JDK 21** → Required to run the application.

---

## **Features**

- **Multi-Destination Logging** → File, ELK, and Console supported
- **Sync or Async per Strategy** → Configure independently for each target
- **Strategy Pattern** → Logging logic is cleanly separated and reusable
- **No Boilerplate** → No builder or wrapper classes required
- **Minimal API** → Log directly via `.log(LogLevel, message)`

---

## **Architecture Diagram**

```mermaid
classDiagram
    direction TB

    class LogLevel {
        <<enum>>
        +INFO
        +ERROR
    }

    class LogStrategy {
        <<interface>>
        +log(level: LogLevel, msg: String): Unit
    }

    class FileSystemStrategy {
        +log(level: LogLevel, msg: String): Unit
    }

    class ELKStrategy {
        +log(level: LogLevel, msg: String): Unit
    }

    class ConsoleStrategy {
        +log(level: LogLevel, msg: String): Unit
    }

    class AsyncStrategy {
        -inner: LogStrategy
        +log(level: LogLevel, msg: String): Unit
    }

    class LoggerRouter {
        - List[LogStrategy]
        +log(level: LogLevel, msg: String): Unit
    }

    LogStrategy <|-- FileSystemStrategy
    LogStrategy <|-- ELKStrategy
    LogStrategy <|-- ConsoleStrategy
    LogStrategy <|-- AsyncStrategy
    AsyncStrategy --> LogStrategy: wraps
    LoggerRouter --> LogStrategy: delegates
```

---

## **Strategy Pattern**

The **Strategy Pattern** allows each logging destination to encapsulate its own behavior. Instead of hardcoding log
handling:

- Each destination implements `LogStrategy`.
- The router (`LoggerRouter`) delegates the log call to all configured strategies.
- `AsyncStrategy` wraps any existing strategy to make it non-blocking.
- No wrapper classes or builders are needed — just build a list of strategies.

---

## **Setup Instructions**

### **1️ - Clone the Repository**

```shell
git clone https://github.com/rbleggi/tech-pocs.git
cd scala-3/logger-builder-router-system
```

### **2️ - Compile & Run the Application**

```shell
./sbtw compile run
```

### **3️ - Run Tests**

```shell
./sbtw compile test
```