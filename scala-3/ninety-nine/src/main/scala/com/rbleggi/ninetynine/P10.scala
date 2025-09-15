package com.rbleggi.ninetynine

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
//   - The map method applies a function to each element of a list and returns a new list.
object P10 {
  def encode[A](items: List[A]): List[(Int, A)] =
    // pack consecutive duplicates into sublists, then map each sublist to a tuple (length, head) 
    P09.pack(items).map(sublist => (sublist.length, sublist.head))
}

// Use the result of problem P09 to implement the so-called run-length encoding data compression method.
// Consecutive duplicates of elements are encoded as tuples (N, E)
// where N is the number of duplicates of the element E.
@main def mainP10(): Unit =
  println("Run-length encoding of a list.")
  val symbols = List("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e")
  println(s"The list is: $symbols")
  val encoded = P10.encode(symbols)
  println(s"The encoded list is: $encoded")
