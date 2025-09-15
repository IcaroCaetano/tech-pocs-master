package com.rbleggi.ninetynine

// P55 (**) Construct completely balanced binary trees.
// In a completely balanced binary tree, for every node, the number of nodes in its left and right subtree differ by at most one.
// Tree.cBalanced(n, x) generates all completely balanced binary trees with n nodes, each node containing value x.
// Example:
// Tree.cBalanced(4, "x")
// res0: List[Node[String]] = List(Node(x, Node(x, Empty, Empty), Node(x, Empty, Node(x, Empty, Empty))), ...)

sealed trait TreeBalanced[+A]
case object EmptyBalanced extends TreeBalanced[Nothing]
case class NodeBalanced[+A](value: A, left: TreeBalanced[A], right: TreeBalanced[A]) extends TreeBalanced[A]

object TreeBalanced {
  def cBalanced[A](n: Int, x: A): List[TreeBalanced[A]] = {
    if (n == 0) List(EmptyBalanced)
    else if (n == 1) List(NodeBalanced(x, EmptyBalanced, EmptyBalanced))
    else {
      val trees = for {
        leftSize <- 0 to (n - 1) / 2
        rightSize = n - 1 - leftSize
        leftTrees = cBalanced(leftSize, x)
        rightTrees = cBalanced(rightSize, x)
        // To avoid duplicates when leftSize != rightSize
        treeCombos = if (leftSize == rightSize) {
          for {
            l <- leftTrees
            r <- rightTrees
          } yield NodeBalanced(x, l, r)
        } else {
          (for { l <- leftTrees; r <- rightTrees } yield NodeBalanced(x, l, r)) ++
          (for { l <- rightTrees; r <- leftTrees } yield NodeBalanced(x, l, r))
        }
      } yield treeCombos
      trees.flatten.toList
    }
  }
}

@main def mainP55(): Unit = {
  println("Completely balanced binary trees for n = 4, value = 'x':")
  val trees = TreeBalanced.cBalanced(4, "x")
  trees.zipWithIndex.foreach { case (tree, idx) => println(s"Tree #$idx: $tree") }
}
