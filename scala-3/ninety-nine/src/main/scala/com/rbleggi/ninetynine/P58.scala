package com.rbleggi.ninetynine

// P58 (**) Generate-and-test paradigm.
// Construct all symmetric, completely balanced binary trees with a given number of nodes.

sealed trait Tree[+A]
case object End extends Tree[Nothing]
case class Node[+A](value: A, left: Tree[A] = End, right: Tree[A] = End) extends Tree[A]
case class PositionedNode[+A](
  value: A,
  left: Tree[A],
  right: Tree[A],
  x: Int,
  y: Int
) extends Tree[A] {
  override def toString: String =
    s"T[$x,$y]($value ${left.toString} ${right.toString})"
}

object Tree {
  // Generate all completely balanced binary trees with n nodes
  def cBalanced[A](n: Int, x: A): List[Tree[A]] = {
    if (n == 0) List(End)
    else if (n % 2 == 1) {
      val sub = cBalanced((n - 1) / 2, x)
      for (l <- sub; r <- sub) yield Node(x, l, r)
    } else {
      val sub1 = cBalanced(n / 2, x)
      val sub2 = cBalanced(n / 2 - 1, x)
      (for (l <- sub1; r <- sub2) yield Node(x, l, r)) ++
      (for (l <- sub2; r <- sub1) yield Node(x, l, r))
    }
  }

  // Check if a tree is symmetric
  def isMirrorOf[A](t1: Tree[A], t2: Tree[A]): Boolean = (t1, t2) match {
    case (End, End) => true
    case (Node(_, l1, r1), Node(_, l2, r2)) => isMirrorOf(l1, r2) && isMirrorOf(r1, l2)
    case _ => false
  }

  def isSymmetric[A](tree: Tree[A]): Boolean = tree match {
    case End => true
    case Node(_, l, r) => isMirrorOf(l, r)
  }

  // Generate all symmetric, completely balanced binary trees with n nodes
  def symmetricBalancedTrees[A](n: Int, x: A): List[Tree[A]] =
    cBalanced(n, x).filter(isSymmetric)

  // P63: Construct a complete binary tree
  def completeBinaryTree[A](n: Int, x: A): Tree[A] = {
    def build(addr: Int): Tree[A] = {
      if (addr > n) End
      else Node(x, build(2 * addr), build(2 * addr + 1))
    }
    build(1)
  }
}

// Extension methods for Tree/Node
object TreeExtensions {
  implicit class RichTree[T](tree: Tree[T]) {
    // P62: Collect internal nodes
    def internalList: List[T] = tree match {
      case Node(value, left, right) =>
        (left, right) match {
          case (End, End) => Nil
          case _ =>
            value :: left.internalList ++ right.internalList
        }
      case End => Nil
    }

    // P62B: Collect nodes at a given level
    def atLevel(level: Int): List[T] = tree match {
      case Node(value, left, right) =>
        if (level < 1) Nil
        else if (level == 1) List(value)
        else left.atLevel(level - 1) ++ right.atLevel(level - 1)
      case End => Nil
    }
  }
}

@main def mainP58(): Unit = {
  val trees = Tree.symmetricBalancedTrees(5, "x")
  trees.foreach(println)
}
