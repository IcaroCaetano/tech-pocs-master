package com.rbleggi.euler

@main def mainP01(): Unit = {
  val sumBelow10 = (1 until 10).filter(n => n % 3 == 0 || n % 5 == 0).sum
  println(s"Sum of multiples of 3 or 5 below 10: $sumBelow10 (expected: 23)")

  val sumBelow1000 = (1 until 1000).filter(n => n % 3 == 0 || n % 5 == 0).sum
  println(s"Sum of multiples of 3 or 5 below 1000: $sumBelow1000")
}
