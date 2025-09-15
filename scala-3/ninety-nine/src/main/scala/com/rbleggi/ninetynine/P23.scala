package com.rbleggi.ninetynine

import scala.util.Random

// Extract a given number of randomly selected elements from a list.
// The same element will not be selected twice (sampling without replacement).
object P23 {
  def randomSelect[A](n: Int, items: List[A]): List[A] = {
    def selectRec(count: Int, source: List[A], rand: Random): List[A] =
      if (count <= 0 || source.isEmpty) Nil
      else {
        val idx = rand.nextInt(source.length)
        val (before, atAndAfter) = source.splitAt(idx)
        atAndAfter.head :: selectRec(count - 1, before ++ atAndAfter.tail, rand)
      }
    selectRec(n, items, new Random)
  }
}

@main def mainP23(): Unit = {
  println("Extract a given number of randomly selected elements from a list.")
  val symbols = List("a", "b", "c", "d", "f", "g", "h")
  println(s"The list is: $symbols")
  val selected = P23.randomSelect(3, symbols)
  println(s"Randomly selected elements: $selected")
}
