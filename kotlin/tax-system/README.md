# TAX System Project

## Overview

This project demonstrates a flexible tax calculation system that calculates product prices based on tax rules defined by **year**, **state**, and **product**.

- **Kotlin** → A concise, modern JVM-based language.

---

## Features

- **Scalable Architecture**: Easily extendable to include more rules or criteria.
- **Specification Pattern**: Allows clear and flexible rules for filtering taxes by year, state, or product.

---

## Implementations

### 1. **Specific Specifications**

```mermaid
classDiagram

   class YearSpecification {
      <<interface>>
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class 2023YearSpecification {
      -stateSpecifications: List~StateSpecification~
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class 2024YearSpecification {
      -stateSpecifications: List~StateSpecification~
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class CASpecification2023 {
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class TXSpecification2023 {
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class CASpecification2024 {
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class TXSpecification2024 {
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class TaxRule {
      +state: State
      +product: String
      +year: Int
      +taxRate: Double
      +calculateTax(price: Double): Double
   }

   class TaxCalculator {
      +calculateTotalPrice(product: String, price: Double, state: State, year: Int): Double
   }

   YearSpecification <|-- 2023YearSpecification
   YearSpecification <|-- 2024YearSpecification

   2023YearSpecification --> CASpecification2023 : uses
   2023YearSpecification --> TXSpecification2023 : uses
   2024YearSpecification --> CASpecification2024 : uses
   2024YearSpecification --> TXSpecification2024 : uses

   TaxCalculator --> YearSpecification : uses
   YearSpecification ..> TaxRule : evaluates
```

#### **Advantages:**
- Clear domain modeling with explicit rules.
- Easy to debug and maintain simple use-cases.
- Straightforward logic and readability.

#### **Drawbacks:**
- Code duplication across similar classes.
- High coupling and low scalability.
- Complex to extend with many states or years.

---

### 2. **Compound Specification**

```mermaid
classDiagram

   class ProductSpecification {
      -product: String
      -compoundSpecification: CompoundSpecification
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class CompoundSpecification {
      -yearSpecifications: List~YearSpecification~
      -stateSpecification: StateSpecification
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class YearSpecification {
      -year: Int
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class StateSpecification {
      -state: String
      +isSatisfiedBy(candidate: TaxRule): Boolean
   }

   class TaxRule {
      +state: String
      +product: String
      +year: Int
      +taxRate: Double
      +calculateTax(price: Double): Double
   }

   class TaxCalculator {
      +calculateTotalPriceCompound(product: String, price: Double, state: State, year: Int): Double
   }

   ProductSpecification --> CompoundSpecification : uses
   CompoundSpecification --> YearSpecification : uses (multiple, OR)
   CompoundSpecification --> StateSpecification : uses

   TaxCalculator --> ProductSpecification : uses
   ProductSpecification ..> TaxRule : evaluates
```

#### **Advantages:**
- Highly flexible composition of rules.
- Reduces redundancy and improves scalability.
- Easy to extend to new specifications (years/states/products).

#### ️ **Drawbacks:**
- Slightly increased complexity for newcomers.
- Harder debugging due to dynamic rule composition.

---

## Prerequisites

- **JDK 21** → Ensure Java is installed.
- **Gradle** → Used for dependency management.

---

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/rbleggi/tech-pocs.git
   cd kotlin/tax-system
   ```

2. **Compiling & Running**:
   ```sh
   ./gradlew build run
   ```

3. **Tests**:
   ```sh
   ./gradlew test
   ```