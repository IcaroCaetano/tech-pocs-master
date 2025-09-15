package com.rbleggi.ninetynine

// Generate a random permutation of the elements of a list.
// Hint: Use the solution of problem P23.
object P25 {
  def randomPermute[A](items: List[A]): List[A] =
    P23.randomSelect(items.length, items)
}

@main def mainP25(): Unit = {
  println("Generate a random permutation of the elements of a list.")
  val symbols = List("a", "b", "c", "d", "f")
  println(s"The list is: $symbols")
  val permuted = P25.randomPermute(symbols)
  println(s"Random permutation: $permuted")
}
