package com.rbleggi.ninetynine

// P62 (*) Collect the leaves of a binary tree in a list.
// A leaf is a node with no successors. Write a method leafList to collect them in a list.

sealed trait Tree61A[+A] {
  def leafList: List[A]
}
case object End62 extends Tree61A[Nothing] {
  def leafList: List[Nothing] = Nil
}
case class Node62[+A](value: A, left: Tree61A[A] = End62, right: Tree61A[A] = End62) extends Tree61A[A] {
  def leafList: List[A] = (left, right) match {
    case (End62, End62) => List(value)
    case _ => left.leafList ++ right.leafList
  }
}

@main def mainP62(): Unit = {
  val tree = Node62('a', Node62('b'), Node62('c', Node62('d'), Node62('e')))
  println(s"Leaf list: ${tree.leafList}") // Should print List(b, d, e)
}

