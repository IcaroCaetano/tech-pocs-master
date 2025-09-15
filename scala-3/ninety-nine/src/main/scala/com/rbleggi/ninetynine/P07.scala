package com.rbleggi.ninetynine

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
//   - The '++' operator concatenates two lists.
object P07 {
  def flatten(items: List[Any]): List[Any] =
    println(s"Recursion step: items = $items")
    items match
      case Nil => Nil // Returns Nil(empty list) when the input list is empty.
      // '(head: List[_])' uses type matching to check if head is a List of any type.
      case (head: List[_]) :: tail =>
        flatten(head) ++ flatten(tail)
      case head :: tail =>
        // If head is not a list, add it to the result and flatten the tail.
        head :: flatten(tail)
}

@main def mainP07(): Unit =
  println("Flatten a nested list structure.")
  val nestedList = List(List(1, 1), 2, List(3, List(5, 8)))
  println(s"The list is: $nestedList")
  val flatList = P07.flatten(nestedList)
  println(s"The flattened list is: $flatList")
