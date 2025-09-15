package com.rbleggi.ninetynine

// P57 (**) Binary search trees (dictionaries).
// Implement addValue and fromList for BST, and isSymmetric as in P56.

sealed trait TreeBST[+A] {
  def addValue[U >: A](x: U)(implicit ord: Ordering[U]): TreeBST[U] = this match {
    case EndBST => NodeBST(x)
    case NodeBST(value, left, right) =>
      if (ord.lt(x, value)) NodeBST(value, left.addValue(x), right)
      else NodeBST(value, left, right.addValue(x))
  }

  def isMirrorOf[B >: A](other: TreeBST[B]): Boolean = (this, other) match {
    case (EndBST, EndBST) => true
    case (NodeBST(_, l1, r1), NodeBST(_, l2, r2)) => l1.isMirrorOf(r2) && r1.isMirrorOf(l2)
    case _ => false
  }

  def isSymmetric: Boolean = this match {
    case EndBST => true
    case NodeBST(_, l, r) => l.isMirrorOf(r)
  }
}

case object EndBST extends TreeBST[Nothing]
case class NodeBST[+A](value: A, left: TreeBST[A] = EndBST, right: TreeBST[A] = EndBST) extends TreeBST[A]

object TreeBST {
  def fromList[A](xs: List[A])(implicit ord: Ordering[A]): TreeBST[A] =
    xs.foldLeft(EndBST: TreeBST[A])((tree, x) => tree.addValue(x))
}

@main def mainP57(): Unit = {
  val t1 = EndBST.addValue(2)
  println(t1) // NodeBST(2,EndBST,EndBST)

  val t2 = t1.addValue(3)
  println(t2) // NodeBST(2,EndBST,NodeBST(3,EndBST,EndBST))

  val t3 = t2.addValue(0)
  println(t3) // NodeBST(2,NodeBST(0,EndBST,EndBST),NodeBST(3,EndBST,EndBST))

  val t4 = TreeBST.fromList(List(3, 2, 5, 7, 1))
  println(t4)

  val t5 = TreeBST.fromList(List(5, 3, 18, 1, 4, 12, 21))
  println(t5.isSymmetric) // true

  val t6 = TreeBST.fromList(List(3, 2, 5, 7, 4))
  println(t6.isSymmetric) // false
}

