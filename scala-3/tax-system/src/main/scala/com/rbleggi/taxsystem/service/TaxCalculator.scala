package com.rbleggi.taxsystem.service

import com.rbleggi.taxsystem.model.{Product, TaxConfiguration}
import com.rbleggi.taxsystem.specification.{DefaultTaxSpecification, TaxSpecification}

class TaxCalculator {

  def calculateTax(state: String, year: Int, product: Product, price: Double): Double = {
    val specifications: List[TaxSpecification] = List(
      new DefaultTaxSpecification(TaxConfiguration("SP", 2024, Map("electronics" -> 0.18, "food" -> 0.07, "book" -> 0.00))),
      new DefaultTaxSpecification(TaxConfiguration("RJ", 2024, Map("electronics" -> 0.20, "food" -> 0.08, "book" -> 0.02))),
      new DefaultTaxSpecification(TaxConfiguration("MG", 2024, Map("electronics" -> 0.15, "food" -> 0.05, "book" -> 0.01))),
      new DefaultTaxSpecification(TaxConfiguration("SP", 2025, Map("electronics" -> 0.19, "food" -> 0.06, "book" -> 0.00)))
    )

    val rule = specifications.find(_.isSatisfiedBy(state, year))

    rule match {
      case Some(spec) => spec.calculateTax(product, price)
      case None => throw new Exception(s"No tax rule found for $state in the year $year")
    }
  }
}