package com.rbleggi.taxsystem.specification

import com.rbleggi.taxsystem.model.{Product, TaxConfiguration}

class DefaultTaxSpecification(config: TaxConfiguration) extends TaxSpecification {
  override def isSatisfiedBy(state: String, year: Int): Boolean =
    config.state == state && config.year == year

  override def calculateTax(product: Product, price: Double): Double =
    price * config.rates.getOrElse(product.category, 0.10)
}