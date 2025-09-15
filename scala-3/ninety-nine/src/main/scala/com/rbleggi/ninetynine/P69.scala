package com.rbleggi.ninetynine

sealed trait Tree69[+A] {
  def toDotstring: String = this match {
    case End69 => "."
    case Node69(v, l, r) => s"$v${l.toDotstring}${r.toDotstring}"
  }
}
case object End69 extends Tree69[Nothing]
case class Node69[+A](value: A, left: Tree69[A] = End69, right: Tree69[A] = End69) extends Tree69[A]

object Tree69 {
  // Parse a dotstring into a Tree69[Char]
  def fromDotstring(s: String): Tree69[Char] = {
    def parse(idx: Int): (Tree69[Char], Int) = {
      if (idx >= s.length) return (End69, idx)
      if (s(idx) == '.') return (End69, idx + 1)
      val v = s(idx)
      val (left, i1) = parse(idx + 1)
      val (right, i2) = parse(i1)
      (Node69(v, left, right), i2)
    }
    parse(0)._1
  }

  // For demo: convert string2Tree from P68
  def string2Tree(s: String): Tree69[Char] = {
    def parse(idx: Int): (Tree69[Char], Int) = {
      if (idx >= s.length || s(idx) == ',' || s(idx) == ')') return (End69, idx)
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
        (Node69(v, left, right), i)
      } else {
        (Node69(v), i)
      }
    }
    parse(0)._1
  }

  @main def runP69Demo(): Unit = {
    val tree = string2Tree("a(b(d,e),c(,f(g,)))")
    val dot = tree.toDotstring
    println(dot) // Should print: abd..e..c.fg...
    val parsed = fromDotstring(dot)
    println(parsed) // Should print the same structure as tree
  }
}

