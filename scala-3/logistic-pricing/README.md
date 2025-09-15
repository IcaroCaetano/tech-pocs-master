# **Logistic Pricing**

## **Overview**

This project implements a **modular and extensible freight pricing system** using the **Strategy Pattern**. Freight
costs are dynamically calculated based on factors like **volume, size, and transport type** — such as **Truck**, **Rail
**, or **Boat** — with prices subject to **real-time variability**.

---

## **Tech Stack**

- **Scala 3** → Modern JVM-based language with advanced type safety and functional programming features.
- **SBT** → Scala's official build tool.
- **JDK 21** → Required to run the application.

---

## **Features**

- **Strategy-Based Price Calculation** → Easily plug in new pricing models
- **Supports Multiple Transport Types** → Truck, Rail, Boat
- **Dynamic Pricing** → Pricing logic is fully decoupled and runtime-driven
- **Extensible Design** → Add more transport strategies without touching core logic
- **Minimal Boilerplate** → No builder classes or verbose configuration

---

## **Architecture Diagram**

```mermaid
classDiagram
    direction TB

    class FreightInfo {
        +volume: Double
        +size: Double
        +distance: Double
        +transportType: TransportType
    }

    class TransportType {
        <<enum>>
        +Truck
        +Rail
        +Boat
    }

    class PricingStrategy {
        <<interface>>
        +calculate(info: FreightInfo): Double
    }

    class TruckPricingStrategy {
        +calculate(info: FreightInfo): Double
    }

    class RailPricingStrategy {
        +calculate(info: FreightInfo): Double
    }

    class BoatPricingStrategy {
        +calculate(info: FreightInfo): Double
    }

    class FreightCalculator {
        -strategy: PricingStrategy
        +calculate(info: FreightInfo): Double
    }

    PricingStrategy <|-- TruckPricingStrategy
    PricingStrategy <|-- RailPricingStrategy
    PricingStrategy <|-- BoatPricingStrategy
    FreightCalculator --> PricingStrategy: uses
```

---

## **Strategy Pattern**

Using the **Strategy Pattern**, the pricing logic is abstracted away from the core calculator:

1. Each transport mode implements its own `PricingStrategy`.
2. `FreightCalculator` delegates calculation to the selected strategy.
3. Easily swap or update strategies at runtime.
4. No need to modify core logic when adding a new transport type or changing pricing logic.

---

## **Setup Instructions**

### **1️ - Clone the Repository**

```bash
git clone https://github.com/rbleggi/tech-pocs.git
cd scala-3/logistic-pricing
```

### **2️ - Compile & Run the Application**

```bash
./sbtw compile run
```

### **3️ - Run Tests**

```bash
./sbtw test
```