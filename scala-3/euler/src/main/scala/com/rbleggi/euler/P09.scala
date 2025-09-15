package com.rbleggi.euler

// Project Euler Problem 9
// Find the product abc of the unique Pythagorean triplet where a + b + c = 1000
@main def mainP09(): Unit = {
  for (a <- 1 to 998) {
    for (b <- a + 1 to 999) {
      val c = 1000 - a - b
      // Check if (a, b, c) is a Pythagorean triplet
      if (a * a + b * b == c * c) {
        println(s"The Pythagorean triplet is: a=$a, b=$b, c=$c")
        println(s"The product abc is: ${a * b * c}")
        return
      }
    }
  }
}

