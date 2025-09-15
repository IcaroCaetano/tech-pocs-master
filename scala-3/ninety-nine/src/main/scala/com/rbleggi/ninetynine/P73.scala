// P73 (**) Lisp-like tree representation.
// This exercise demonstrates how to represent multiway trees in Lisp-like notation and parse them back.
// Key Scala features used: case classes, companion objects, pattern matching, recursion, and @main for demo.

package com.rbleggi.ninetynine

// Multiway tree definition
case class MTreeP73[+T](value: T, children: List[MTreeP73[T]]) {
  // Secondary constructor for leaf nodes
  def this(value: T) = this(value, List())

  // lispyTree: Converts the tree to Lisp-like string representation
  // Example: (a (f g) c (b d e))
  def lispyTree: String = {
    if (children.isEmpty) value.toString
    else s"(${value.toString} ${children.map(_.lispyTree).mkString(" ")})"
  }
}

object MTreeP73 {
  // Factory methods for convenience
  def apply[T](value: T): MTreeP73[T] = new MTreeP73(value, List())
  def apply[T](value: T, children: List[MTreeP73[T]]): MTreeP73[T] = new MTreeP73(value, children)

  // Parses a Lisp-like string into an MTreeP73
  // Example input: (a (f g) c (b d e))
  def fromLispyString(s: String): MTreeP73[String] = {
    // Tokenize the string into atoms and parentheses
    val tokens = s.replace("(", " ( ").replace(")", " ) ").split(" ").filter(_.nonEmpty).toList
    def parse(tokens: List[String]): (MTreeP73[String], List[String]) = tokens match {
      case Nil => throw new IllegalArgumentException("Empty input")
      case "(" :: atom :: rest =>
        def childrenLoop(ts: List[String], acc: List[MTreeP73[String]]): (List[MTreeP73[String]], List[String]) = ts match {
          case ")" :: tail => (acc, tail)
          case Nil => throw new IllegalArgumentException("Unmatched parenthesis")
          case _ =>
            val (child, afterChild) = parse(ts)
            childrenLoop(afterChild, acc :+ child)
        }
        val (children, afterChildren) = childrenLoop(rest, Nil)
        (MTreeP73(atom, children), afterChildren)
      case atom :: rest if atom != "(" && atom != ")" => (MTreeP73(atom), rest)
      case ")" :: rest => throw new IllegalArgumentException("Unexpected closing parenthesis")
    }
    val (tree, remaining) = parse(tokens)
    if (remaining.nonEmpty) throw new IllegalArgumentException("Extra tokens after parsing")
    tree
  }

  // Demo using @main
  @main def demoLispyTree(): Unit = {
    println("P73: Lisp-like tree representation.")
    val tree = MTreeP73("a", List(
      MTreeP73("f", List(MTreeP73("g"))),
      MTreeP73("c"),
      MTreeP73("b", List(MTreeP73("d"), MTreeP73("e")))
    ))
    println("Original tree: " + tree)
    val lispy = tree.lispyTree
    println("Lisp-like string: " + lispy)
    val parsed = fromLispyString(lispy)
    println("Parsed tree from Lisp-like string: " + parsed)
  }
}

