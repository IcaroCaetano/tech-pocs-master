package com.rbleggi.ninetynine

// Split a list into two parts.
// The length of the first part is given. Use a Tuple for your result.
object P17 {
  def split[A](n: Int, items: List[A]): (List[A], List[A]) =
    // Use the built-in splitAt method, which splits a list at the given index
    items.splitAt(n)
}

@main def mainP17(): Unit =
  println("Split a list into two parts.")
  val symbols = List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k")
  println(s"The list is: $symbols")
  val (first, second) = P17.split(3, symbols)
  println(s"First part: $first")
  println(s"Second part: $second")
