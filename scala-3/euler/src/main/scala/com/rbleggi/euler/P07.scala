package com.rbleggi.euler

// Problem 7
// By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
// What is the 10,001st prime number?
@main def mainP07(): Unit = {
  def isPrime(n: Int): Boolean = {
    if (n < 2) false
    else if (n == 2) true
    else if (n % 2 == 0) false
    else !(3 to math.sqrt(n).toInt by 2).exists(n % _ == 0)
  }

  var count = 0
  var candidate = 1
  var lastPrime = 2

  while (count < 10001) {
    candidate += 1
    if (isPrime(candidate)) {
      count += 1
      lastPrime = candidate
    }
  }

  println(s"The 10,001st prime number is: $lastPrime")
}

