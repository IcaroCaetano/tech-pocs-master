package com.rbleggi.ninetynine

// Duplicate the elements of a list.
// For each element in the input list, produce two copies in the output list.
object P14 {
  def duplicate[A](items: List[A]): List[A] =
    // Use flatMap to map each element to a list of two elements, then flatten the result
    items.flatMap(e => List(e, e))
}

@main def mainP14(): Unit =
  println("Duplicate the elements of a list.")
  val symbols = List("a", "b", "c", "c", "d")
  println(s"The list is: $symbols")
  val duplicated = P14.duplicate(symbols)
  println(s"The duplicated list is: $duplicated")
