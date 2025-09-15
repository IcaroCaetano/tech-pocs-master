package com.rbleggi.ninetynine

// P70: Multiway trees
case class MTree[+T](value: T, children: List[MTree[T]]) {
  def this(value: T) = this(value, List())
  override def toString: String =
    s"M($value {${children.map(_.toString).mkString(",")}})"

  def nodeCount: Int = 1 + children.map(_.nodeCount).sum
}

object MTree {
  def apply[T](value: T): MTree[T] = new MTree(value, List())
  def apply[T](value: T, children: List[MTree[T]]): MTree[T] = new MTree(value, children)

  @main def demoMTree(): Unit = {
    val tree = MTree('a', List(
      MTree('f', List(MTree('g'))),
      MTree('c'),
      MTree('b', List(MTree('d'), MTree('e')))
    ))
    println(tree)
  }

  @main def demoNodeCount(): Unit = {
    val tree = MTree('a', List(MTree('f')))
    println(s"Node count: ${tree.nodeCount}") // Should print 2
  }
}
