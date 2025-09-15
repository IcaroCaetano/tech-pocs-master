package com.rbleggi.ninetynine

// P40 (**) Goldbach’s conjecture.
object P40 {
  // Helper: Returns true if n is prime
  def isPrime(n: Int): Boolean =
    if (n < 2) false
    else !(2 to math.sqrt(n).toInt).exists(n % _ == 0)

  // Returns a tuple of two primes that sum to n (Goldbach's conjecture)
  def goldbach(n: Int): (Int, Int) = {
    require(n > 2 && n % 2 == 0, "n must be an even integer greater than 2")
    val primes = (2 to n / 2).filter(isPrime)
    primes.find(p => isPrime(n - p)) match {
      case Some(p) => (p, n - p)
      case None => throw new IllegalArgumentException(s"No Goldbach partition found for $n")
    }
  }
}

@main def mainP40(): Unit = {
  val n = 28
  val gb = P40.goldbach(n)
  println(s"Goldbach partition for $n: $gb")
}

