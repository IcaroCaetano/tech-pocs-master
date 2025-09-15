package com.rbleggi.ninetynine

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
object P08 {
  def compress[A](items: List[A]): List[A] =
    println(s"Recursion step: items = $items")
    items match
      // case Nil: matches an empty list, returns Nil
      case Nil => Nil
      // case head :: tail: matches a non-empty list, splits into head and tail
      case head :: tail =>
        tail match
          // case next :: rest: matches if tail is non-empty, splits into next and rest
          case next :: rest
            // if head == next: skips the duplicate by calling compress(head :: rest)
            if head == next => compress(head :: rest)
          // case _: matches any other case (tail is empty or next != head), keeps head and continues compressing
          case _ => head :: compress(tail)
}

// If a list contains repeated elements they should be replaced with a single copy of the element.
// The order of the elements should not be changed.
@main def mainP08(): Unit =
  println("Eliminate consecutive duplicates of list elements.")
  val symbols = List("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e")
  println(s"The list is: $symbols")
  val compressed = P08.compress(symbols)
  println(s"The compressed list is: $compressed")
