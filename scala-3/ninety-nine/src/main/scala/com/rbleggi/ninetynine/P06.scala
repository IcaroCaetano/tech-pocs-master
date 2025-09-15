package com.rbleggi.ninetynine

import scala.annotation.tailrec

// In Scala pattern matching:
//   - The '::' operator splits a list into its head (first element) and tail (rest of the list).
//   - The '|' operator is used to combine multiple patterns as alternatives (logical OR).
// A palindrome is a sequence that reads the same forwards and backwards.
// For lists, this means the first element equals the last, the second equals the second-to-last, and so on.
object P06 {
  // The @tailrec annotation ensures that the function is tail-recursive.
  // If the recursion is not in tail position, the compiler will show an error.
  // Tail recursion allows the compiler to optimize the function into a loop, preventing stack overflow and improving performance.
  @tailrec
  def isPalindrome[A](items: List[A]): Boolean =
    println(s"Recursion step: $items")
    items match
      // Empty or single-element list is a palindrome
      case Nil | _ :: Nil => true
      // Compare first and last, then recurse on the middle
      case head :: tail
        if head == tail.last => isPalindrome(tail.init)
      case _ => false // If first and last don't match, not a palindrome
}

@main def mainP06(): Unit =
  println("Find out whether a list is a palindrome.")
  val palindromeList = List(1, 2, 3, 2, 1)
  println(s"The list is: $palindromeList")
  val resultPalindrome = P06.isPalindrome(palindromeList)
  println(s"Is the list a palindrome? $resultPalindrome")
  println()
  val nonPalindromeList = List(1, 2, 3, 4, 5)
  println(s"The list is: $nonPalindromeList")
  val resultNonPalindrome = P06.isPalindrome(nonPalindromeList)
  println(s"Is the list a palindrome? $resultNonPalindrome")
