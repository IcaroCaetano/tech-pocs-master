package com.rbleggi.ninetynine

// P60 (**) Construct height-balanced binary trees with a given number of nodes.
// Functions: minHbalNodes, maxHbalHeight, hbalTreesWithNodes

import com.rbleggi.ninetynine.{TreeHBal, EndHBal, NodeHBal}

object TreeHbalOpsP60 {
  // Minimum number of nodes for height-balanced tree of height h
  def minHbalNodes(h: Int): Int = {
    if (h == 0) 0
    else if (h == 1) 1
    else 1 + minHbalNodes(h - 1) + minHbalNodes(h - 2)
  }

  // Maximum height for height-balanced tree with n nodes
  def maxHbalHeight(n: Int): Int = {
    def loop(h: Int): Int =
      if (minHbalNodes(h) > n) h - 1 else loop(h + 1)
    loop(1)
  }

  // All height-balanced trees with n nodes and value x
  def hbalTreesWithNodes[A](n: Int, x: A): List[TreeHBal[A]] = {
    def trees(h: Int): List[TreeHBal[A]] = {
      if (h == 0) List(EndHBal)
      else if (h == 1) List(NodeHBal(x))
      else {
        val result = for {
          l <- trees(h - 1)
          r <- trees(h - 1)
        } yield NodeHBal(x, l, r)
        val left = for {
          l <- trees(h - 1)
          r <- trees(h - 2)
        } yield NodeHBal(x, l, r)
        val right = for {
          l <- trees(h - 2)
          r <- trees(h - 1)
        } yield NodeHBal(x, l, r)
        result ++ left ++ right
      }
    }
    val minH = {
      def loop(h: Int): Int = if (minHbalNodes(h) > n) h - 1 else loop(h + 1)
      loop(1)
    }
    val maxH = maxHbalHeight(n)
    (minH to maxH).flatMap { h =>
      trees(h).filter(countNodes(_) == n)
    }.toList
  }

  // Helper to count nodes in a tree
  def countNodes[A](tree: TreeHBal[A]): Int = tree match {
    case EndHBal => 0
    case NodeHBal(_, l, r) => 1 + countNodes(l) + countNodes(r)
  }
}

@main def mainP60(): Unit = {
  println(s"minHbalNodes(3) = ${TreeHbalOpsP60.minHbalNodes(3)}")
  println(s"maxHbalHeight(4) = ${TreeHbalOpsP60.maxHbalHeight(4)}")
  val trees = TreeHbalOpsP60.hbalTreesWithNodes(4, "x")
  trees.foreach(println)
  println(s"Total trees with 4 nodes: ${trees.size}")
  val trees15 = TreeHbalOpsP60.hbalTreesWithNodes(15, "x")
  println(s"Total trees with 15 nodes: ${trees15.size}")
}
