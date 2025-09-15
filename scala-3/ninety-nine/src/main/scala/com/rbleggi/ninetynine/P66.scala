package com.rbleggi.ninetynine

import com.rbleggi.ninetynine.{Tree, Node, End, PositionedNode}

object P66 {
  // Helper: Get left and right contour (x positions at each depth)
  private def leftContour[A](tree: Tree[A], x: Int, y: Int, acc: Map[Int, Int]): Map[Int, Int] = tree match {
    case End => acc
    case Node(_, l, r) =>
      val accL = leftContour(l, x - 1, y + 1, acc + (y -> math.min(acc.getOrElse(y, x), x)))
      leftContour(r, x + 1, y + 1, accL + (y -> math.min(accL.getOrElse(y, x), x)))
    case PositionedNode(_, l, r, px, py) =>
      val accL = leftContour(l, px - 1, py + 1, acc + (py -> math.min(acc.getOrElse(py, px), px)))
      leftContour(r, px + 1, py + 1, accL + (py -> math.min(accL.getOrElse(py, px), px)))
  }
  private def rightContour[A](tree: Tree[A], x: Int, y: Int, acc: Map[Int, Int]): Map[Int, Int] = tree match {
    case End => acc
    case Node(_, l, r) =>
      val accL = rightContour(l, x - 1, y + 1, acc + (y -> math.max(acc.getOrElse(y, x), x)))
      rightContour(r, x + 1, y + 1, accL + (y -> math.max(accL.getOrElse(y, x), x)))
    case PositionedNode(_, l, r, px, py) =>
      val accL = rightContour(l, px - 1, py + 1, acc + (py -> math.max(acc.getOrElse(py, px), px)))
      rightContour(r, px + 1, py + 1, accL + (py -> math.max(accL.getOrElse(py, px), px)))
  }

  // Main layout function
  private def layout[A](tree: Tree[A], depth: Int, x: Int): (Tree[A], Int, Int) = tree match {
    case End => (End, x, x)
    case Node(v, l, r) =>
      val (lTree, lMin, lMax) = layout(l, depth + 1, x)
      val (rTree, rMin, rMax) = layout(r, depth + 1, x)
      // Compute separation
      val lContour = rightContour(lTree, x - 1, depth + 1, Map())
      val rContour = leftContour(rTree, x + 1, depth + 1, Map())
      val overlap = (lContour.keySet intersect rContour.keySet).map { d =>
        lContour(d) - rContour(d)
      }.foldLeft(0)((acc, v) => math.max(acc, v))
      val sep = if (lTree == End || rTree == End) 1 else overlap / 2 + 1
      val rootX = x + (if (lTree == End) 0 else -sep)
      val leftShifted = shiftTree(lTree, rootX - lMax)
      val rightShifted = shiftTree(rTree, rootX + sep - rMin)
      val minX = math.min(rootX, math.min(getMinX(leftShifted), getMinX(rightShifted)))
      val maxX = math.max(rootX, math.max(getMaxX(leftShifted), getMaxX(rightShifted)))
      (PositionedNode(v, leftShifted, rightShifted, rootX, depth), minX, maxX)
    case PositionedNode(v, l, r, px, py) => (PositionedNode(v, l, r, px, py), px, px)
  }

  // Shift all x positions in a tree
  private def shiftTree[A](tree: Tree[A], dx: Int): Tree[A] = tree match {
    case End => End
    case PositionedNode(v, l, r, x, y) => PositionedNode(v, shiftTree(l, dx), shiftTree(r, dx), x + dx, y)
    case Node(v, l, r) => Node(v, shiftTree(l, dx), shiftTree(r, dx))
  }
  private def getMinX[A](tree: Tree[A]): Int = tree match {
    case End => Int.MaxValue
    case PositionedNode(_, l, r, x, _) => math.min(x, math.min(getMinX(l), getMinX(r)))
    case Node(_, l, r) => math.min(getMinX(l), getMinX(r))
  }
  private def getMaxX[A](tree: Tree[A]): Int = tree match {
    case End => Int.MinValue
    case PositionedNode(_, l, r, x, _) => math.max(x, math.max(getMaxX(l), getMaxX(r)))
    case Node(_, l, r) => math.max(getMaxX(l), getMaxX(r))
  }

  // Extension method for layoutBinaryTree3
  implicit class LayoutOps3[A](tree: Tree[A]) {
    def layoutBinaryTree3: Tree[A] = {
      val (t, minX, _) = layout(tree, 1, 1)
      // Shift so leftmost node is at x=1
      shiftTree(t, 1 - minX)
    }
  }

  @main def runP66Demo(): Unit = {
    val tree = Node('a', Node('b', End, Node('c')), Node('d'))
    val positioned = tree.layoutBinaryTree3
    println(positioned)
    // Should print: T[2,1]('a T[1,2]('b . T[2,3]('c . .)) T[3,2]('d . .))
  }
}

