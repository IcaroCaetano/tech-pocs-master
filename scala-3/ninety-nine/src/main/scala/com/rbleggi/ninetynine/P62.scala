package com.rbleggi.ninetynine
import com.rbleggi.ninetynine.{Tree, Node, End}
import com.rbleggi.ninetynine.TreeExtensions._

object P62 {
  @main def runP62Demo(): Unit = {
    val tree = Node('a', Node('b'), Node('c', Node('d'), Node('e')))
    println(tree.internalList) // Should print List(a, c)
  }
}
