# **Observability Framework**

## **Overview**

This project implements a simple and extensible framework for latency observability using the **Strategy Pattern** in Scala. It allows you to define different latency calculation strategies and manage multiple metric trackers flexibly.

---

## **Tech Stack**

- **Scala 3** → Modern JVM-based language with advanced type safety and functional programming features.
- **SBT** → Scala's official build tool.
- **JDK 21** → Required to run the application.

---

## **Features**

- **Latency Strategies** → Easily calculate latency in milliseconds or nanoseconds.
- **Strategy Pattern** → Add new calculation strategies by implementing the `LatencyStrategy` trait.
- **Metrics Management** → Create, retrieve, and reset named latency trackers.
- **Extensible** → Add new strategies or metric types without changing the core framework.

---

## **Architecture Diagram**

```mermaid
classDiagram
    direction TB

    class LatencyStrategy {
        <<trait>>
        +calculateLatency(startTime: Long, endTime: Long): Long
    }

    class MillisecondsLatencyStrategy {
        +calculateLatency(startTime: Long, endTime: Long): Long
    }

    class NanosecondsLatencyStrategy {
        +calculateLatency(startTime: Long, endTime: Long): Long
    }

    class LatencyTracker {
        -latencies: ListBuffer[Long]
        +recordLatency(startTime: Long, endTime: Long): Unit
        +getAverageLatency: Double
        +getAllLatencies: List[Long]
    }

    class MetricManager {
        +getTracker(name: String, strategy: LatencyStrategy): LatencyTracker
        +resetTracker(name: String): Unit
        +getAllTrackerNames: List[String]
    }

    LatencyStrategy <|-- MillisecondsLatencyStrategy
    LatencyStrategy <|-- NanosecondsLatencyStrategy
    LatencyTracker o-- LatencyStrategy: uses
    MetricManager o-- LatencyTracker: manages
```

---

## **Strategy Pattern**

The **Strategy Pattern** allows defining a family of algorithms, encapsulating them, and making them interchangeable. In this project:

- Each latency calculation strategy is implemented as a class that extends `LatencyStrategy`.
- The `LatencyTracker` receives a strategy and uses it to calculate the latency of each recorded operation.
- The `MetricManager` manages multiple latency trackers, each potentially using a different strategy.
- New strategies can be added without modifying the existing code.

---

## **Setup Instructions**

### **1️ - Clone the Repository**

```bash
git clone https://github.com/rbleggi/tech-pocs.git
cd scala-3/observability-framework
```

### **2️ - Compile & Run the Application**

```bash
./sbtw compile run
```

### **3️ - Run the Tests**

```bash
./sbtw test
```
