package com.rbleggi.euler

@main def mainP03(): Unit = {
  // Finds the largest prime factor of a given number
  def largestPrimeFactor(n: Long): Long = {
    var num = n
    var factor = 2L
    var lastFactor = 1L
    while (factor * factor <= num) {
      if (num % factor == 0) {
        lastFactor = factor
        num /= factor
        while (num % factor == 0) {
          num /= factor
        }
      }
      factor += 1
    }
    if (num > 1) num else lastFactor
  }

  val testNumber = 13195L
  val testResult = largestPrimeFactor(testNumber)
  println(s"Largest prime factor of $testNumber: $testResult")

  val targetNumber = 600851475143L
  val result = largestPrimeFactor(targetNumber)
  println(s"Largest prime factor of $targetNumber: $result")
}

