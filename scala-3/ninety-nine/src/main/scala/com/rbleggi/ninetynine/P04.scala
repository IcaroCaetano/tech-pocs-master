package com.rbleggi.ninetynine

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
//   - The '|' operator is used to combine multiple patterns as alternatives (logical OR).
// Note: The @tailrec annotation is not used here because the recursive call is not in tail position.
// In length, the recursive call is part of an addition (1 + length(tail)), so the compiler cannot optimize it as tail-recursive.
object P04 {
  def length[A](items: List[A]): Int =
    println(s"Recursion step: $items")
    items match
      // If the list is empty, return 0
      case Nil => 0
      // Otherwise, count 1 for the head and recursively count the tail
      case head :: tail => 1 + length(tail)
}

@main def mainP04(): Unit =
  println("Find the number of elements of a list.")
  val numbers = List(1, 1, 2, 3, 5, 8)
  println(s"The list is: $numbers")
  val count = P04.length(numbers)
  println(s"The number of elements is: $count")
