package com.rbleggi.euler

@main def mainP02(): Unit = {
  // Generate Fibonacci sequence up to 4 million

  // Defines an infinite lazy Fibonacci sequence.
  // - Starts with 1 and 2 using #:: (prepends elements to LazyList).
  // - fibs.zip(fibs.tail) pairs each element with its next: (1,2), (2,3), (3,5), ...
  // - .map { case (a, b) => a + b } sums each pair to generate the next Fibonacci number.
  // - Elements are computed only when needed (lazy evaluation).
  def fibs: LazyList[Int] = 1 #:: 2 #:: fibs.zip(fibs.tail).map { case (a, b) => a + b }
  val fibsUpTo4M = fibs.takeWhile(_ <= 4000000)
  val evenSum = fibsUpTo4M.filter(_ % 2 == 0).sum
  println(s"Sum of even Fibonacci numbers not exceeding 4 million: $evenSum")
}

