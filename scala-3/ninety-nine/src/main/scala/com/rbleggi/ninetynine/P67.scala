package com.rbleggi.ninetynine

sealed trait Tree67[+A] {
  override def toString: String = this match {
    case End67 => ""
    case Node67(v, l, r) =>
      val leftStr = l.toString
      val rightStr = r.toString
      if (leftStr.isEmpty && rightStr.isEmpty) s"$v"
      else s"$v($leftStr,$rightStr)"
  }
}
case object End67 extends Tree67[Nothing]
case class Node67[+A](value: A, left: Tree67[A] = End67, right: Tree67[A] = End67) extends Tree67[A]

object Tree67 {
  // Parse a string into a Tree67[Char]
  def fromString(s: String): Tree67[Char] = {
    def parse(idx: Int): (Tree67[Char], Int) = {
      if (idx >= s.length || s(idx) == ',' || s(idx) == ')') return (End67, idx)
      val v = s(idx)
      var i = idx + 1
      if (i < s.length && s(i) == '(') {
        i += 1
        val (left, i1) = parse(i)
        i = i1
        if (i < s.length && s(i) == ',') i += 1
        val (right, i2) = parse(i)
        i = i2
        if (i < s.length && s(i) == ')') i += 1
        (Node67(v, left, right), i)
      } else {
        (Node67(v), i)
      }
    }
    parse(0)._1
  }

  @main def runP67Demo(): Unit = {
    val tree = Node67('a', Node67('b', Node67('d'), Node67('e')), Node67('c', End67, Node67('f', Node67('g'), End67)))
    val str = tree.toString
    println(str) // Should print: a(b(d,e),c(,f(g,)))
    val parsed = fromString(str)
    println(parsed) // Should print the same structure
  }
}

