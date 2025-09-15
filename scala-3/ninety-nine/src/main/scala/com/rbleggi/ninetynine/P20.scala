package com.rbleggi.ninetynine

// Remove the Kth element from a list.
// Return the list and the removed element in a Tuple. Elements are numbered from 0.
object P20 {
  def removeAt[A](k: Int, items: List[A]): (List[A], A) = {
    require(k >= 0 && k < items.length, "Index out of bounds")
    val (before, atAndAfter) = items.splitAt(k)
    (before ++ atAndAfter.tail, atAndAfter.head)
  }
}

@main def mainP20(): Unit =
  println("Remove the Kth element from a list.")
  val symbols = List("a", "b", "c", "d")
  println(s"The list is: $symbols")
  val (rest, removed) = P20.removeAt(1, symbols)
  println(s"The list after removing index 1: $rest, removed element: $removed")
