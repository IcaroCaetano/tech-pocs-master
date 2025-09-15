package com.rbleggi.ninetynine
import com.rbleggi.ninetynine.{Tree, Node, End}
import com.rbleggi.ninetynine.TreeExtensions._

object P62B {
  @main def runP62BDemo(): Unit = {
    val tree = Node('a', Node('b'), Node('c', Node('d'), Node('e')))
    println(tree.atLevel(2)) // Should print List(b, c)
  }
}
