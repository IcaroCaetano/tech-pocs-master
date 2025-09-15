package com.rbleggi.ninetynine

import scala.annotation.tailrec

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
//   - The '|' operator is used to combine multiple patterns as alternatives (logical OR).
object P02 {
  // The @tailrec annotation ensures that the function is tail-recursive.
  // If the recursion is not in tail position, the compiler will show an error.
  // Tail recursion allows the compiler to optimize the function into a loop, preventing stack overflow and improving performance.
  @tailrec
  def penultimate[A](items: List[A]): A =
    println(s"Recursion step: $items")
    items match
      // If the list is empty (Nil) or has only one element, throw an exception
      // Nil is a singleton object representing an empty list in Scala
      // _ :: Nil matches a list with a single element (the underscore means we ignore the value)
      case Nil | _ :: Nil => throw new NoSuchElementException("penultimate of list with less than two elements")
      // penultimateElem :: lastElem :: Nil matches a list with exactly two elements
      // penultimateElem is the first (penultimate), lastElem is the second (last)
      case penultimateElem :: lastElem :: Nil => penultimateElem
      // head :: tail matches a list with at least one element
      // head is the first element (ignored here), tail is the rest of the list
      // Recursively call penultimate on the tail until the base case is reached
      case head :: tail => penultimate(tail)
}

@main def mainP02(): Unit =
  println("Find the last but one element of a list.")
  val numbers = List(1, 1, 2, 3, 5, 8)
  println(s"The list is: $numbers")
  val penultimateElement = P02.penultimate(numbers)
  println(s"The penultimate element is: $penultimateElement")
