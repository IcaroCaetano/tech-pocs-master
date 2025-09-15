package com.rbleggi.taxsystem

import com.rbleggi.taxsystem.model.*
import com.rbleggi.taxsystem.service.TaxCalculator

@main def run(): Unit = {
  val product1 = Product("Smartphone", "electronics")
  val product2 = Product("Rice", "food")
  val product3 = Product("History Book", "book")

  val calculator = new TaxCalculator()

  val priceProduct1 = 2500.0
  val priceProduct2 = 20.0
  val priceProduct3 = 50.0

  println(f"Tax for ${product1.name} in SP (2024): RS ${calculator.calculateTax("SP", 2024, product1, priceProduct1)}%.2f")
  println(f"Tax for ${product2.name} in MG (2024): RS ${calculator.calculateTax("MG", 2024, product2, priceProduct2)}%.2f")
  println(f"Tax for ${product3.name} in RJ (2024): RS ${calculator.calculateTax("RJ", 2024, product3, priceProduct3)}%.2f")
  println(f"Tax for ${product1.name} in SP (2025): RS ${calculator.calculateTax("SP", 2025, product1, priceProduct1)}%.2f")
}