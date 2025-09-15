package com.rbleggi.ninetynine

// Create a list containing all integers within a given range.
// Example: range(4, 9) == List(4, 5, 6, 7, 8, 9)
object P22 {
  def range(start: Int, end: Int): List[Int] =
    // Use the built-in to method to create a range (inclusive)
    (start to end).toList
}

@main def mainP22(): Unit =
  println("Create a list containing all integers within a given range.")
  val r = P22.range(4, 9)
  println(s"The range from 4 to 9 is: $r")

