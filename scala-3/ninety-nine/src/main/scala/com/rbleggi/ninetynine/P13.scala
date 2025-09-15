package com.rbleggi.ninetynine

// Direct run-length encoding (without using pack or previous solutions)
// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
//   - The span method splits a list into a prefix (while predicate is true) and the rest.
object P13 {
  def encodeDirect[A](items: List[A]): List[(Int, A)] =
    items match {
      case Nil => Nil
      case head :: tail =>
        val (same, rest) = tail.span(_ == head)
        ((same.length + 1), head) :: encodeDirect(rest)
    }
}

// Run-length encoding of a list (direct solution):
// Do not use other methods like P09.pack; do all the work directly.
@main def mainP13(): Unit =
  println("Direct run-length encoding of a list.")
  val symbols = List("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e")
  println(s"The list is: $symbols")
  val encoded = P13.encodeDirect(symbols)
  println(s"The directly encoded list is: $encoded")
