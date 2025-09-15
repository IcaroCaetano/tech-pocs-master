package com.rbleggi.ninetynine

// P39 (*) A list of prime numbers in a given range.
object P39 {
  // Returns true if n is prime
  def isPrime(n: Int): Boolean =
    if (n < 2) false
    else !(2 to math.sqrt(n).toInt).exists(n % _ == 0)

  // Returns a list of all primes in the given inclusive range
  def listPrimesinRange(r: Range): List[Int] =
    r.filter(isPrime).toList
}

@main def mainP39(): Unit = {
  println("List of primes in range 7 to 31:")
  val primes = P39.listPrimesinRange(7 to 31)
  println(primes)
}

