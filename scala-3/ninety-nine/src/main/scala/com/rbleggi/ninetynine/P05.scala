package com.rbleggi.ninetynine

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
//   - The '|' operator is used to combine multiple patterns as alternatives (logical OR).
//   - The ':+' operator appends an element to the end of a list, creating a new list.
// Note: The @tailrec annotation is not used here because the recursive call is not in tail position.
// In reverse, the recursive call is part of a list append (reverse(tail) :+ head), so the compiler cannot optimize it as tail-recursive.
object P05 {
  def reverse[A](items: List[A]): List[A] =
    println(s"Recursion step: $items")
    items match
      // If the list is empty, return Nil (empty list)
      case Nil => Nil
      // Otherwise, reverse the tail and append the head at the end
      case head :: tail => reverse(tail) :+ head
}

@main def mainP05(): Unit =
  println("Reverse a list.")
  val numbers = List(1, 1, 2, 3, 5, 8)
  println(s"The list is: $numbers")
  val reversed = P05.reverse(numbers)
  println(s"The reversed list is: $reversed")
