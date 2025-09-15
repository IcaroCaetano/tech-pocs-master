package com.rbleggi.euler

@main def mainP04(): Unit = {
  // Checks if a number is a palindrome
  def isPalindrome(n: Int): Boolean = {
    val s = n.toString
    s == s.reverse
  }

  var maxPalindrome = 0
  var factor1 = 0
  var factor2 = 0

  for (i <- 999 to 100 by -1) {
    for (j <- i to 100 by -1) {
      val product = i * j
      if (isPalindrome(product) && product > maxPalindrome) {
        maxPalindrome = product
        factor1 = i
        factor2 = j
      }
    }
  }

  println(s"Largest palindrome made from the product of two 3-digit numbers: $maxPalindrome = $factor1 x $factor2")
}

