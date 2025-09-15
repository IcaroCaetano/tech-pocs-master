package com.rbleggi.ninetynine

import S99Int._

@main def mainP31(): Unit = {
  println("Determine whether a given integer number is prime.")
  val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 17, 19, 20)
  numbers.foreach(n => println(s"$n.isPrime = ${n.isPrime}"))
}
