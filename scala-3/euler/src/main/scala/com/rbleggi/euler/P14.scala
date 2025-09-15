package com.rbleggi.euler

// Project Euler Problem 14
// Which starting number, under one million, produces the longest Collatz chain?

@main def runP14(): Unit = {
  val cache = scala.collection.mutable.Map[Long, Int](1L -> 1)

  def collatzLength(n: Long): Int = {
    cache.getOrElseUpdate(n, {
      if (n % 2 == 0) 1 + collatzLength(n / 2)
      else 1 + collatzLength(3 * n + 1)
    })
  }

  var maxLength = 0
  var result = 0

  for (i <- 1 until 1000000) {
    val length = collatzLength(i)
    if (length > maxLength) {
      maxLength = length
      result = i
    }
  }

  println(s"The starting number under one million that produces the longest Collatz chain is: $result")
  println(s"The length of the chain is: $maxLength")
}

