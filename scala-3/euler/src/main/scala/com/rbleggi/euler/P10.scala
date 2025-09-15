package com.rbleggi.euler

// Project Euler Problem 10
// Find the sum of all the primes below two million
@main def mainP10(): Unit = {
  val limit = 2000000

  val isPrime = Array.fill(limit)(true)
  isPrime(0) = false
  isPrime(1) = false

  for (i <- 2 until math.sqrt(limit).toInt + 1) {
    if (isPrime(i)) {
      for (j <- i * 2 until limit by i) {
        isPrime(j) = false
      }
    }
  }

  val sum = isPrime.zipWithIndex.filter(_._1).map(_._2.toLong).sum
  println(s"The sum of all primes below $limit is: $sum")
}
