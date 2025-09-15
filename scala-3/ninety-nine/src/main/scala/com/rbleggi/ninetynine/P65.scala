package com.rbleggi.ninetynine

import com.rbleggi.ninetynine.{Tree, Node, End, PositionedNode}

object P65 {
  // Calculate the height of the tree
  def height[A](tree: Tree[A]): Int = tree match {
    case End => 0
    case Node(_, l, r) => 1 + math.max(height(l), height(r))
  }

  // Extension method for layoutBinaryTree2
  implicit class LayoutOps2[A](tree: Tree[A]) {
    def layoutBinaryTree2: Tree[A] = {
      val h = height(tree)
      def layout(t: Tree[A], depth: Int, x: Int, offset: Int): Tree[A] = t match {
        case End => End
        case Node(v, l, r) =>
          val nextOffset = offset / 2
          val leftX = x - nextOffset
          val rightX = x + nextOffset
          val leftPos = layout(l, depth + 1, leftX, nextOffset)
          val rightPos = layout(r, depth + 1, rightX, nextOffset)
          PositionedNode(v, leftPos, rightPos, x, depth)
      }
      val initialOffset = math.pow(2, h - 2).toInt
      layout(tree, 1, initialOffset + 1, initialOffset)
    }
  }

  @main def runP65Demo(): Unit = {
    val tree = Node('a', Node('b', End, Node('c')), Node('d'))
    val positioned = tree.layoutBinaryTree2
    println(positioned)
    // Should print: T[3,1]('a T[1,2]('b . T[2,3]('c . .)) T[5,2]('d . .))
  }
}

