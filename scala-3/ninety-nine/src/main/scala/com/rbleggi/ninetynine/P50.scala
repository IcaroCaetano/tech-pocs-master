package com.rbleggi.ninetynine

// P50 (***) Huffman code.
// Given a list of (symbol, frequency) tuples, construct the Huffman code for each symbol.
// Example:
// huffman(List(("a", 45), ("b", 13), ("c", 12), ("d", 16), ("e", 9), ("f", 5)))
// res0: List[(String, String)] = List((a,0), (b,101), (c,100), (d,111), (e,1101), (f,1100))

object P50 {
  // Node definition for Huffman tree
  sealed trait Node {
    def freq: Int
  }
  case class Leaf(symbol: String, freq: Int) extends Node
  case class Internal(left: Node, right: Node, freq: Int) extends Node

  // Main Huffman function
  def huffman(freqs: List[(String, Int)]): List[(String, String)] = {
    // Build initial forest
    var nodes: List[Node] = freqs.map { case (s, f) => Leaf(s, f) }
    // Build Huffman tree
    while (nodes.length > 1) {
      val sorted = nodes.sortBy(_.freq)
      val left = sorted.head
      val right = sorted.tail.head
      val merged = Internal(left, right, left.freq + right.freq)
      nodes = merged :: sorted.drop(2)
    }
    // Traverse tree to assign codes
    def assignCodes(node: Node, prefix: String): List[(String, String)] = node match {
      case Leaf(s, _) => List((s, prefix))
      case Internal(l, r, _) =>
        assignCodes(l, prefix + "0") ++ assignCodes(r, prefix + "1")
    }
    assignCodes(nodes.head, "")
  }

  @main def mainP50(): Unit = {
    println("Huffman code for example input:")
    val input = List(("a", 45), ("b", 13), ("c", 12), ("d", 16), ("e", 9), ("f", 5))
    val result = P50.huffman(input)
    result.foreach { case (sym, code) => println(s"$sym: $code") }
  }
}
