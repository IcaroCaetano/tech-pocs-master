package com.rbleggi.ninetynine

import scala.annotation.tailrec

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
//     For example, 'a :: b' means 'a' is the first element, 'b' is the rest of the list.
//   - The '|' operator is used to combine multiple patterns as alternatives (logical OR).
//     For example, 'Nil | _ :: Nil' matches if the list is empty OR has only one element.
object P03 {
  // The @tailrec annotation ensures that the function is tail-recursive.
  // If the recursion is not in tail position, the compiler will show an error.
  // Tail recursion allows the compiler to optimize the function into a loop, preventing stack overflow and improving performance.
  @tailrec
  def nth[A](k: Int, items: List[A]): A =
    println(s"Recursion step: k = $k, $items")
    (k, items) match
      // If k is negative or the list is empty, throw an exception
      case (k, Nil) => throw new NoSuchElementException("nth of empty list")
      case (k, head :: tail) if k < 0 => throw new NoSuchElementException("negative index")
      // If k is 0, return the head (first element)
      case (0, head :: tail) => head
      // Otherwise, recursively call nth with k-1 and the tail of the list
      case (k, head :: tail) => nth(k - 1, tail)
}

@main def mainP03(): Unit =
  println("Find the Kth element of a list")
  val numbers = List(1, 1, 2, 3, 5, 8)
  println(s"The list is: $numbers")
  val k = 4
  println(s"The index to find is: $k")
  val kthElement = P03.nth(k, numbers)
  println(s"The element at index $k is: $kthElement")
