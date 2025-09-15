package com.rbleggi.euler

@main def mainP05(): Unit = {
  // Function to compute the greatest common divisor (GCD)
  def gcd(a: Long, b: Long): Long = if (b == 0) a else gcd(b, a % b)

  // Function to compute the least common multiple (LCM)
  def lcm(a: Long, b: Long): Long = a * b / gcd(a, b)

  // Compute the least common multiple(LCM) of numbers from 1 to 20
  val result = (1L to 20L).reduce(lcm)

  println(s"The smallest positive number evenly divisible by all numbers from 1 to 20 is: $result")
}

