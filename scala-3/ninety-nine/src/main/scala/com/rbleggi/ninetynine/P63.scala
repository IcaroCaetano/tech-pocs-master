package com.rbleggi.ninetynine
import com.rbleggi.ninetynine.{Tree, Node, End}

object P63 {
  @main def runP63Demo(): Unit = {
    val tree = Tree.completeBinaryTree(6, "x")
    println(tree)
    // Should print: T(x T(x T(x . .) T(x . .)) T(x T(x . .) .))
  }
}
