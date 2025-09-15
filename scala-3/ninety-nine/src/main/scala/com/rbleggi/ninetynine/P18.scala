package com.rbleggi.ninetynine

// Extract a slice from a list.
// Given two indices, I and K, the slice is the list containing the elements from and including the Ith element up to but not including the Kth element of the original list.
// Start counting the elements with 0.
object P18 {
  def slice[A](i: Int, k: Int, items: List[A]): List[A] =
    // Use drop to skip the first i elements, then take the next (k - i) elements
    items.drop(i).take(k - i)
}

@main def mainP18(): Unit =
  println("Extract a slice from a list.")
  val symbols = List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k")
  println(s"The list is: $symbols")
  val sliced = P18.slice(3, 7, symbols)
  println(s"The slice from 3 to 7 is: $sliced")
