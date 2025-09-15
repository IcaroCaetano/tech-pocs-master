package com.rbleggi.ninetynine

// Shared definitions for height-balanced binary trees
sealed trait TreeHBal[+A]
case object EndHBal extends TreeHBal[Nothing]
case class NodeHBal[+A](value: A, left: TreeHBal[A] = EndHBal, right: TreeHBal[A] = EndHBal) extends TreeHBal[A]

