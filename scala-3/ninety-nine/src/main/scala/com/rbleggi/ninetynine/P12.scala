package com.rbleggi.ninetynine

// In Scala:
//   - The flatMap method applies a function to each element and flattens the result.
//   - List.fill(n)(elem) creates a list with n copies of elem.
object P12 {
  def decode[A](items: List[(Int, A)]): List[A] =
    // For each (count, element), create a list with 'count' copies of 'element', then flatten the result
    items.flatMap { case (count, elem) => List.fill(count)(elem) }
}

// Decode a run-length encoded list.
// Given a run-length code list generated as specified in problem P10, construct its uncompressed version.
@main def mainP12(): Unit =
  println("Decode a run-length encoded list.")
  val encoded = List((4, "a"), (1, "b"), (2, "c"), (2, "a"), (1, "d"), (4, "e"))
  println(s"The encoded list is: $encoded")
  val decoded = P12.decode(encoded)
  println(s"The decoded list is: $decoded")
