# **Ticket Booking**

## **Overview**

This project implements a **flexible and safe ticket booking** using the **Builder Pattern**. Users can choose **zones
**, **dates**, **seats**, and **shows**, while the system enforces **maximum place capacity** and prevents overbooking.

---

## **Tech Stack**

- **Scala 3** → Modern JVM-based language with advanced type safety and functional programming features.
- **SBT** → Scala's official build tool.
- **JDK 21** → Required to run the application.

---

## **Features**

- **Builder Pattern** → Flexible ticket construction via fluent API
- **Zone & Seat Selection** → Pick specific seats from configured areas
- **Capacity Enforcement** → No overbooking allowed per show/zone
- **Multiple Shows Support** → Maintain date- and show-specific logic
- **Easy to Extend** → Add pricing, QR codes, or validation later

---

## **Architecture Diagram**

```mermaid
classDiagram
    direction TB

    class Ticket {
        +show: String
        +zone: String
        +seat: Int
        +date: String
    }

    class TicketBuilder {
        -show: String
        -zone: String
        -seat: Int
        -date: String
        +setShow(show: String): TicketBuilder
        +setZone(zone: String): TicketBuilder
        +setSeat(seat: Int): TicketBuilder
        +setDate(date: String): TicketBuilder
        +buildTicket(): Ticket
    }

    class TicketBookingService {
        +bookTicket(ticket: Ticket): Ticket
    }

    class PlaceCapacity {
        -bookedSeats: Set[(String, String, Int, String)]
        +isAvailable(show: String, zone: String, seat: Int, date: String): Boolean
        +registerTicket(ticket: Ticket): Unit
    }

    TicketBuilder --> Ticket: builds
    TicketBookingService --> Ticket: receives
    TicketBookingService --> PlaceCapacity: validates & registers

```

---

## **Builder Pattern**

The **Builder Pattern** provides a fluent interface for building a ticket with many required parameters:

1. Choose the show, zone, seat, and date
2. The builder validates it through a **PlaceCapacity** registry
3. If valid, it returns a `Ticket` object — if not, it throws an error
4. All logic is **encapsulated**, so clients don't deal with constraints directly

---

## **Setup Instructions**

### **1️ - Clone the Repository**

```bash
git clone https://github.com/rbleggi/tech-pocs.git
cd scala-3/ticket-booking
```

### **2️ - Compile & Run the Application**

```bash
./sbtw compile run
```

### **3️ - Run Tests**

```bash
./sbtw test
```