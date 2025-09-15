package com.rbleggi.taxsystem.impl2

data class TaxRule(val state: String, val product: String, val year: Int, val taxRate: Double) {
    fun calculateTax(price: Double): Double = price * (taxRate / 100)
}

class StateSpecification(private val state: String) {
    fun isSatisfiedBy(candidate: TaxRule) = candidate.state == state
}

class YearSpecification(private val year: Int) {
    fun isSatisfiedBy(candidate: TaxRule) = candidate.year == year
}

class CompoundSpecification(
    private val yearSpecs: List<YearSpecification>,
    private val stateSpec: StateSpecification
) {
    fun isSatisfiedBy(candidate: TaxRule): Boolean =
        yearSpecs.any { it.isSatisfiedBy(candidate) } && stateSpec.isSatisfiedBy(candidate)
}

class ProductSpecification(
    private val product: String,
    private val compoundSpec: CompoundSpecification
) {
    fun isSatisfiedBy(candidate: TaxRule): Boolean =
        candidate.product == product && compoundSpec.isSatisfiedBy(candidate)
}

class TaxCalculatorCompound(private val rules: List<TaxRule>) {
    fun calculateTotalPriceCompound(product: String, price: Double, state: String, year: Int): Double {
        val spec = ProductSpecification(
            product,
            CompoundSpecification(
                yearSpecs = listOf(YearSpecification(2023), YearSpecification(2024)),
                stateSpec = StateSpecification(state)
            )
        )

        val tax = rules.firstOrNull { rule ->
            rule.year == year && spec.isSatisfiedBy(rule)
        }?.calculateTax(price) ?: 0.0

        return price + tax
    }
}

fun main() {

    val taxRules = listOf(
        TaxRule("CA", "Laptop", 2023, 8.5),
        TaxRule("CA", "Laptop", 2024, 9.0),
        TaxRule("TX", "Laptop", 2023, 6.25),
        TaxRule("CA", "Book", 2024, 0.0),
        TaxRule("TX", "Pizza", 2024, 4.0)
    )

    val calculator = TaxCalculatorCompound(taxRules)

    println("Compound Specification:")

    println("Laptop in CA (2023): \$${calculator.calculateTotalPriceCompound("Laptop", 1500.0, "CA", 2023)}")
    println("Laptop in CA (2024): \$${calculator.calculateTotalPriceCompound("Laptop", 1500.0, "CA", 2024)}")
    println("Laptop in CA (2025): \$${calculator.calculateTotalPriceCompound("Laptop", 1500.0, "CA", 2025)}")
    println("Pizza in TX (2024): \$${calculator.calculateTotalPriceCompound("Pizza", 20.0, "TX", 2024)}")
}
