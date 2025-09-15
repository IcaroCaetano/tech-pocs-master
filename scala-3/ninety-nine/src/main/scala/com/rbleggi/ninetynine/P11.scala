package com.rbleggi.ninetynine

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
//   - The map method applies a function to each element of a list and returns a new list.
//   - The match expression can be used inside a map to transform elements based on their structure.
object P11 {
  def encodeModified[A](items: List[A]): List[Any] =
    // Use P09.pack to group consecutive duplicates, then map each group:
    //   - If the group has only one element, return the element itself
    //   - If the group has more than one element, return a tuple (count, element)
    P09.pack(items).map {
      case single :: Nil => single
      case group => (group.length, group.head)
    }
}

// Modified run-length encoding:
// If an element has no duplicates, it is simply copied into the result list.
// Only elements with duplicates are transferred as (N, E) terms.
@main def mainP11(): Unit =
  println("Modified run-length encoding of a list.")
  val symbols = List("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e")
  println(s"The list is: $symbols")
  val encoded = P11.encodeModified(symbols)
  println(s"The modified encoded list is: $encoded")
