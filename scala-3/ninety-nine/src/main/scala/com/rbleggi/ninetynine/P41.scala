package com.rbleggi.ninetynine

// P41 (**) A list of Goldbach compositions.
object P41 {
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

  // Prints Goldbach compositions for all even numbers in the range
  def printGoldbachList(r: Range): Unit = {
    r.filter(n => n > 2 && n % 2 == 0).foreach { n =>
      val (a, b) = goldbach(n)
      println(s"$n = $a + $b")
    }
  }

  // Returns a list of Goldbach compositions for all even numbers in the range
  def goldbachList(r: Range): List[(Int, (Int, Int))] =
    r.filter(n => n > 2 && n % 2 == 0).map(n => (n, goldbach(n))).toList

  // Returns a list of Goldbach compositions where both primes are greater than a given limit
  def goldbachListLimited(r: Range, limit: Int): List[(Int, (Int, Int))] =
    goldbachList(r).filter { case (_, (a, b)) => a > limit && b > limit }
}

@main def mainP41(): Unit = {
  println("Goldbach compositions for 9 to 20:")
  P41.printGoldbachList(9 to 20)

  val limited = P41.goldbachListLimited(2 to 3000, 50)
  println(s"\nNumber of Goldbach compositions in 2..3000 with both primes > 50: ${limited.size}")
}

