package com.rbleggi.ninetynine

// P61 (*) Count the leaves of a binary tree.
// A leaf is a node with no successors. Write a method leafCount to count them.

sealed trait Tree61[+A] {
  def leafCount: Int
}
case object End61 extends Tree61[Nothing] {
  def leafCount: Int = 0
}
case class Node61[+A](value: A, left: Tree61[A] = End61, right: Tree61[A] = End61) extends Tree61[A] {
  def leafCount: Int = (left, right) match {
    case (End61, End61) => 1
    case _ => left.leafCount + right.leafCount
  }
}

@main def mainP61(): Unit = {
  val tree = Node61('x', Node61('x'), End61)
  println(s"Leaf count: ${tree.leafCount}") // Should print 1
}

