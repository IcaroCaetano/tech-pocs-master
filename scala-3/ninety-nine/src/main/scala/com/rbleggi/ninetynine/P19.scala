package com.rbleggi.ninetynine

// Rotate a list N places to the left.
// If N is negative, rotate to the right.
object P19 {
  def rotate[A](n: Int, items: List[A]): List[A] = {
    if (items.isEmpty) items
    else {
      val len = items.length
      // Normalize n to be within the bounds of the list length
      val n1 = if (len == 0) 0 else ((n % len) + len) % len
      val (first, second) = items.splitAt(n1)
      second ++ first
    }
  }
}

@main def mainP19(): Unit =
  println("Rotate a list N places to the left.")
  val symbols = List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k")
  println(s"The list is: $symbols")
  val rotated3 = P19.rotate(3, symbols)
  println(s"The list rotated 3 places: $rotated3")
  val rotatedNeg2 = P19.rotate(-2, symbols)
  println(s"The list rotated -2 places: $rotatedNeg2")
