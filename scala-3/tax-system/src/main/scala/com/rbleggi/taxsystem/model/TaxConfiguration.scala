package com.rbleggi.taxsystem.model

case class TaxConfiguration(state: String, year: Int, rates: Map[String, Double])
