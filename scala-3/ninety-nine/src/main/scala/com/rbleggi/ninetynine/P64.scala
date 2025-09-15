package com.rbleggi.ninetynine

// P64 (**) Layout a binary tree (1).
// Adds x (inorder index) and y (depth) to each node.

import com.rbleggi.ninetynine.{Tree, Node, End, PositionedNode}

object P64 {
  // Extension method for Node to layout the tree
  implicit class LayoutOps[A](tree: Tree[A]) {
    def layoutBinaryTree: Tree[A] = {
      def layout(t: Tree[A], depth: Int, nextX: Int): (Tree[A], Int) = t match {
        case End => (End, nextX)
        case Node(v, l, r) =>
          val (lPos, lX) = layout(l, depth + 1, nextX)
          val currX = lX + 1
          val (rPos, rX) = layout(r, depth + 1, currX)
          (PositionedNode(v, lPos, rPos, currX, depth), rX)
      }
      layout(tree, 1, 0)._1
    }
  }

  @main def runP64Demo(): Unit = {
    val tree = Node('a', Node('b', End, Node('c')), Node('d'))
    val positioned = tree.layoutBinaryTree
    println(positioned)
    // Should print: T[3,1](a T[1,2](b . T[2,3](c . .)) T[4,2](d . .))
  }
}
