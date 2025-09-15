package com.rbleggi.ninetynine

// Drop every Nth element from a list.
// For example, drop(3, List('a, 'b, 'c, 'd, ...)) removes every 3rd element.
object P16 {
  def drop[A](n: Int, items: List[A]): List[A] =
    // Helper function with index tracking
    def loop(lst: List[A], idx: Int): List[A] = lst match {
      case Nil => Nil
      case _ :: tail if idx == n => loop(tail, 1) // Drop the Nth element
      case head :: tail => head :: loop(tail, idx + 1)
    }
    loop(items, 1)
}

@main def mainP16(): Unit =
  println("Drop every Nth element from a list.")
  val symbols = List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k")
  println(s"The list is: $symbols")
  val dropped = P16.drop(3, symbols)
  println(s"The list after dropping every 3rd element: $dropped")
