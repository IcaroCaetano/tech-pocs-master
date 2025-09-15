package com.rbleggi.ninetynine

import scala.annotation.tailrec

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
object P01 {
  // The @tailrec annotation ensures that the function is tail-recursive.
  // If the recursion is not in tail position, the compiler will show an error.
  // Tail recursion allows the compiler to optimize the function into a loop, preventing stack overflow and improving performance.
  @tailrec
  def last[A](items: List[A]): A =
    println(s"Recursion step: $items")
    items match
      // If the list is empty (Nil), throw an exception
      // Nil is a singleton object representing an empty list in Scala
      case Nil => throw new NoSuchElementException("last of empty list")
      // If the list has only one element, return it
      // single :: Nil matches a list with a single element (single)
      case single :: Nil => single
      // If the list has more than one element, ignore the first (head) and recursively call last on the rest (tail)
      case head :: tail => last(tail)
}

@main def mainP01(): Unit =
  println("Find the last element of a list.")
  val numbers = List(1, 1, 2, 3, 5, 8)
  println(s"The list is: $numbers")
  val lastElement = P01.last(numbers)
  println(s"The last element is: $lastElement")
