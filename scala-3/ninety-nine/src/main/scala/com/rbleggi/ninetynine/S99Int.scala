package com.rbleggi.ninetynine

// Arithmetic section: S99Int class and implicit conversion
class S99Int(val value: Int) {
  // P31: Determine whether a given integer number is prime.
  def isPrime: Boolean =
    if (value <= 1) false
    else if (value == 2) true
    else !(2 to math.sqrt(value).toInt).exists(value % _ == 0)
}

object S99Int {
  // Implicit conversion from Int to S99Int
  import scala.language.implicitConversions
  implicit def intToS99Int(n: Int): S99Int = new S99Int(n)
}
