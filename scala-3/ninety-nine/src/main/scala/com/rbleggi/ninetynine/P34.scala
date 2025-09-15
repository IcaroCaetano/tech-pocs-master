package com.rbleggi.ninetynine

// P34 (**) Calculate Euler’s totient function ϕ(m).
// Euler’s totient function ϕ(m) is the number of positive integers r (1 <= r <= m) that are coprime to m.
// Example: 10.totient == 4

object P34 {
  // Reuse gcd from P33
  def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)

  // Returns Euler's totient function for m
  def totient(m: Int): Int =
    (1 to m).count(r => gcd(r, m) == 1)
}

@main def mainP34(): Unit = {
  println("Calculate Euler’s totient function ϕ(m). Example: 10.totient == 4")
  val m = 10
  val result = P34.totient(m)
  println(s"ϕ($m) = $result")
}

