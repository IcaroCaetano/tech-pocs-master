package com.rbleggi.ninetynine

// Lotto: Draw N different random numbers from the set 1..M.
object P24 {
  def lotto(n: Int, m: Int): List[Int] =
    // Use P23.randomSelect to select n random numbers from the range 1 to m
    P23.randomSelect(n, (1 to m).toList)
}

@main def mainP24(): Unit = {
  println("Lotto: Draw N different random numbers from the set 1..M.")
  val numbers = P24.lotto(6, 49)
  println(s"Lotto numbers: $numbers")
}
