package com.rbleggi.ninetynine

// P36 (**) Determine the prime factors of a given positive integer (2).
// Construct a list containing the prime factors and their multiplicity.
// Example: 315.primeFactorMultiplicity == List((3,2), (5,1), (7,1))
// Alternately, use a Map for the result.
// Example: 315.primeFactorMultiplicity == Map(3 -> 2, 5 -> 1, 7 -> 1)

class P36(val n: Int) {
  // Returns a list of tuples (prime, multiplicity)
  def primeFactorMultiplicity: List[(Int, Int)] = {
    def factors(x: Int, divisor: Int = 2, acc: List[Int] = Nil): List[Int] = {
      if (x < 2) acc.reverse
      else if (x % divisor == 0) factors(x / divisor, divisor, divisor :: acc)
      else factors(x, divisor + 1, acc)
    }
    factors(n).groupBy(identity).map { case (prime, list) => (prime, list.size) }.toList.sortBy(_._1)
  }

  // Returns a Map of prime -> multiplicity
  def primeFactorMultiplicityMap: Map[Int, Int] = {
    def factors(x: Int, divisor: Int = 2, acc: List[Int] = Nil): List[Int] = {
      if (x < 2) acc.reverse
      else if (x % divisor == 0) factors(x / divisor, divisor, divisor :: acc)
      else factors(x, divisor + 1, acc)
    }
    factors(n).groupBy(identity).view.mapValues(_.size).toMap
  }
}

@main def mainP36(): Unit = {
  println("Determine the prime factors of a given positive integer (with multiplicity). Example: 315.primeFactorMultiplicity == List((3,2), (5,1), (7,1))")
  val n = 315
  val pfm = new P36(n)
  println(s"Prime factor multiplicity (List) for $n: ${pfm.primeFactorMultiplicity}")
  println(s"Prime factor multiplicity (Map) for $n: ${pfm.primeFactorMultiplicityMap}")
}

