package com.rbleggi.ninetynine

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
//   - The span returns a tuple: (prefix, suffix), where prefix is the longest prefix of elements satisfying the predicate
//     For example: List(1, 1, 2, 3).span(_ == 1) == (List(1, 1), List(2, 3))
object P09 {
  def pack[A](items: List[A]): List[List[A]] =
    println(s"Recursion step: items = $items")
    items match
      // case Nil: matches an empty list, returns Nil
      case Nil => Nil
      // case head :: tail: matches a non-empty list, splits into head and tail
      case head :: tail =>
        // tail.span(_ == head): splits tail into a prefix of elements equal to head (packed) and the rest
        val (packed, rest) = tail.span(_ == head)
        // (head :: packed): creates a sublist of consecutive duplicates
        // pack(rest): recursively packs the remaining elements
        (head :: packed) :: pack(rest)
}

// If a list contains repeated elements they should be placed in separate sublists.
@main def mainP09(): Unit =
  println("Pack consecutive duplicates of list elements into sublists.")
  val symbols = List("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e")
  println(s"The list is: $symbols")
  val packed = P09.pack(symbols)
  println(s"The packed list is: $packed")
