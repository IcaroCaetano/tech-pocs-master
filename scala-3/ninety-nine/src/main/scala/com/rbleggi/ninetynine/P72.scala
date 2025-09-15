package com.rbleggi.ninetynine

// In Scala, case class is used for immutable data structures with automatic equals/hashCode/toString.
// The constructor with default children allows easy creation of leaf nodes.
// This class is for multiway trees, as used in P70-P72.
case class MTreeP72[+T](value: T, children: List[MTreeP72[T]]) {
  // Secondary constructor for leaf nodes
  def this(value: T) = this(value, List())

  // postorder: recursively collects the postorder sequence of the tree nodes
  // In postorder, we visit all children first, then the node itself
  def postorder: List[T] = {
    // For each child, get its postorder, then append this node's value
    children.flatMap(_.postorder) :+ value
  }
}

object MTreeP72 {
  // Factory methods for convenience
  def apply[T](value: T): MTreeP72[T] = new MTreeP72(value, List())
  def apply[T](value: T, children: List[MTreeP72[T]]): MTreeP72[T] = new MTreeP72(value, children)

  // Helper to parse a string in the format used in P70 (e.g. "afg^^c^bd^e^^^") into a multiway tree
  // This format is a preorder traversal with '^' marking the end of a subtree
  def fromString(s: String): MTreeP72[Char] = {
    def parse(chars: List[Char]): (MTreeP72[Char], List[Char]) = chars match {
      case Nil => throw new IllegalArgumentException("Empty string")
      case c :: rest if c == '^' => throw new IllegalArgumentException("Unexpected '^' at root")
      case c :: rest =>
        def childrenLoop(cs: List[Char], acc: List[MTreeP72[Char]]): (List[MTreeP72[Char]], List[Char]) = cs match {
          case Nil => (acc, Nil)
          case '^' :: tail => (acc, tail)
          case _ =>
            val (child, afterChild) = parse(cs)
            childrenLoop(afterChild, acc :+ child)
        }
        val (children, afterChildren) = childrenLoop(rest, Nil)
        (MTreeP72(c, children), afterChildren)
    }
    parse(s.toList)._1
  }

  // @main annotation marks the entry point for running this demo as a standalone app.
  @main def demoPostorder(): Unit = {
    println("P72: Construct the postorder sequence of the tree nodes.")
    val treeStr = "afg^^c^bd^e^^^"
    println(s"Input tree string: $treeStr")
    val tree = fromString(treeStr)
    println(s"Multiway tree: $tree")
    val post = tree.postorder
    println(s"Postorder sequence: $post") // Should print List(g, f, c, d, e, b, a)
  }
}

