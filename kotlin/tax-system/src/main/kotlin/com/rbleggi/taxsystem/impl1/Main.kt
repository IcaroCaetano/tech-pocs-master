package com.rbleggi.taxsystem.impl1

data class TaxRule(val state: String, val product: String, val year: Int, val taxRate: Double) {
    fun calculateTax(price: Double): Double = price * (taxRate / 100)
}

interface StateSpecification {
    fun isSatisfiedBy(candidate: TaxRule): Boolean
}

class CASpecification2023 : StateSpecification {
    override fun isSatisfiedBy(candidate: TaxRule): Boolean = candidate.state == "CA"
}

class TXSpecification2023 : StateSpecification {
    override fun isSatisfiedBy(candidate: TaxRule): Boolean = candidate.state == "TX"
}

class CASpecification2024 : StateSpecification {
    override fun isSatisfiedBy(candidate: TaxRule): Boolean = candidate.state == "CA"
}

class TXSpecification2024 : StateSpecification {
    override fun isSatisfiedBy(candidate: TaxRule): Boolean = candidate.state == "TX"
}

interface YearSpecification {
    fun isSatisfiedBy(candidate: TaxRule): Boolean
}

class Year2023Specification(private val stateSpecs: List<StateSpecification>) : YearSpecification {
    override fun isSatisfiedBy(candidate: TaxRule): Boolean =
        candidate.year == 2023 && stateSpecs.any { it.isSatisfiedBy(candidate) }
}

class Year2024Specification(private val stateSpecs: List<StateSpecification>) : YearSpecification {
    override fun isSatisfiedBy(candidate: TaxRule): Boolean =
        candidate.year == 2024 && stateSpecs.any { it.isSatisfiedBy(candidate) }
}

class TaxCalculator(private val rules: List<TaxRule>) {

    fun calculateTotalPrice(product: String, price: Double, state: String, year: Int): Double {
        val yearSpec: YearSpecification? = when (year) {
            2023 -> Year2023Specification(listOf(CASpecification2023(), TXSpecification2023()))
            2024 -> Year2024Specification(listOf(CASpecification2024(), TXSpecification2024()))
            else -> null
        }

        val tax = rules.firstOrNull { rule ->
            rule.product == product && yearSpec?.isSatisfiedBy(rule) == true && rule.state == state
        }?.calculateTax(price) ?: 0.0

        return price + tax
    }
}

fun main() {
    val taxRules = listOf(
        TaxRule("CA", "Laptop", 2023, 8.5),
        TaxRule("TX", "Laptop", 2023, 6.25),
        TaxRule("CA", "Book", 2024, 0.0),
        TaxRule("TX", "Pizza", 2024, 4.0)
    )

    val calculator = TaxCalculator(taxRules)

    println("Year/State Specific Implementation:")
    println("Laptop in CA (2023): \$${calculator.calculateTotalPrice("Laptop", 1500.0, "CA", 2023)}")
    println("Laptop in TX (2023): \$${calculator.calculateTotalPrice("Laptop", 1500.0, "TX", 2023)}")
    println("Book in CA (2024): \$${calculator.calculateTotalPrice("Book", 30.0, "CA", 2024)}")
    println("Pizza in TX (2024): \$${calculator.calculateTotalPrice("Pizza", 20.0, "TX", 2024)}")
}
