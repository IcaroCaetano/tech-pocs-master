package com.rbleggi.taxsystem.specification

import com.rbleggi.taxsystem.model.Product

trait TaxSpecification {
  def isSatisfiedBy(state: String, year: Int): Boolean
  def calculateTax(product: Product, price: Double): Double
}

