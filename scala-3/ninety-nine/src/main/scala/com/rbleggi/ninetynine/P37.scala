package com.rbleggi.ninetynine

// P37 (**) Calculate Euler’s totient function ϕ(m) (improved).
// If the list of the prime factors of a number m is known in the form of problem P36 then the function ϕ(m) can be efficiently calculated as follows:
// Let [[p1,m1],[p2,m2],[p3,m3],…] be the list of prime factors (and their multiplicities) of a given number m.
// Then ϕ(m) = (p1−1) * p1^(m1−1) * (p2−1) * p2^(m2−1) * ...

class P37(val n: Int) {
  // Efficient Euler's totient function using prime factor multiplicities
  def totientImproved: Int = {
    def primeFactorMultiplicity: List[(Int, Int)] = {
      def factors(x: Int, divisor: Int = 2, acc: List[Int] = Nil): List[Int] = {
        if (x < 2) acc.reverse
        else if (x % divisor == 0) factors(x / divisor, divisor, divisor :: acc)
        else factors(x, divisor + 1, acc)
      }
      factors(n).groupBy(identity).map { case (prime, list) => (prime, list.size) }.toList
    }
    primeFactorMultiplicity.foldLeft(1) { case (acc, (p, m)) =>
      acc * (p - 1) * math.pow(p, m - 1).toInt
    }
  }
}

@main def mainP37(): Unit = {
  println("Calculate Euler’s totient function ϕ(m) (improved). Example: 10.totientImproved == 4")
  val n = 10
  val t = new P37(n)
  println(s"Totient improved for $n: ${t.totientImproved}")
  val n2 = 315
  val t2 = new P37(n2)
  println(s"Totient improved for $n2: ${t2.totientImproved}")
}

