package com.rbleggi.ninetynine

// Insert an element at a given position into a list.
// Example: insertAt('new, 1, List('a, 'b, 'c, 'd)) == List('a, 'new, 'b, 'c, 'd)
object P21 {
  def insertAt[A](elem: A, k: Int, items: List[A]): List[A] = {
    val (before, after) = items.splitAt(k)
    before ++ (elem :: after)
  }
}

@main def mainP21(): Unit =
  println("Insert an element at a given position into a list.")
  val symbols = List("a", "b", "c", "d")
  println(s"The list is: $symbols")
  val inserted = P21.insertAt("new", 1, symbols)
  println(s"The list after inserting 'new at index 1: $inserted")
