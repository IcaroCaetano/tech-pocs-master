package com.rbleggi.ninetynine

sealed trait Tree68[+A] {
  def preorder: List[A] = this match {
    case End68 => Nil
    case Node68(v, l, r) => v :: l.preorder ::: r.preorder
  }
  def inorder: List[A] = this match {
    case End68 => Nil
    case Node68(v, l, r) => l.inorder ::: v :: r.inorder
  }
}
case object End68 extends Tree68[Nothing]
case class Node68[+A](value: A, left: Tree68[A] = End68, right: Tree68[A] = End68) extends Tree68[A]

object Tree68 {
  // Parse a string into a Tree68[Char] (same as P67)
  def string2Tree(s: String): Tree68[Char] = {
    def parse(idx: Int): (Tree68[Char], Int) = {
      if (idx >= s.length || s(idx) == ',' || s(idx) == ')') return (End68, idx)
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
        (Node68(v, left, right), i)
      } else {
        (Node68(v), i)
      }
    }
    parse(0)._1
  }

  // Build tree from preorder and inorder sequences
  def preInTree[A](pre: List[A], in: List[A]): Tree68[A] = {
    if (pre.isEmpty || in.isEmpty) End68
    else {
      val root = pre.head
      val idx = in.indexOf(root)
      if (idx == -1) End68 // root not found in inorder
      else {
        val leftIn = in.take(idx)
        val rightIn = in.drop(idx + 1)
        val leftPre = pre.tail.take(leftIn.length)
        val rightPre = pre.tail.drop(leftIn.length)
        Node68(root, preInTree(leftPre, leftIn), preInTree(rightPre, rightIn))
      }
    }
  }

  @main def runP68Demo(): Unit = {
    val tree = string2Tree("a(b(d,e),c(,f(g,)))")
    println(tree.preorder) // List(a, b, d, e, c, f, g)
    println(tree.inorder)  // List(d, b, e, a, c, g, f)
    val rebuilt = preInTree(List('a', 'b', 'd', 'e', 'c', 'f', 'g'), List('d', 'b', 'e', 'a', 'c', 'g', 'f'))
    println(rebuilt)
    val ambiguous = preInTree(List('a', 'b', 'a'), List('b', 'a', 'a'))
    println(ambiguous)
  }
}

