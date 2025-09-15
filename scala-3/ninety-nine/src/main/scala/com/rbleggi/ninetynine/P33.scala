package com.rbleggi.ninetynine

// P33 (*) Determine whether two positive integer numbers are coprime.
// Two numbers are coprime if their greatest common divisor equals 1.
// Example: 35.isCoprimeTo(64) == true

object P33 {
  // Euclid's algorithm for GCD
  def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)

  // Returns true if a and b are coprime
  def isCoprimeTo(a: Int, b: Int): Boolean =
    gcd(a, b) == 1
}

@main def mainP33(): Unit = {
  println("Determine whether two positive integer numbers are coprime.")
  val a = 35
  val b = 64
  val result = P33.isCoprimeTo(a, b)
  println(s"Are $a and $b coprime? $result")
}
