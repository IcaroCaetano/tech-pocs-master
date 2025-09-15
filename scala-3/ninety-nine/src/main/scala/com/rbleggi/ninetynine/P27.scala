package com.rbleggi.ninetynine

// Group the elements of a set into disjoint subsets.
// Example: group3(List("Aldo", ...))
object P27 {
  // Helper: combinations from P26
  def combinations[A](k: Int, items: List[A]): List[List[A]] =
    if (k == 0) List(Nil)
    else
      items match {
        case Nil => Nil
        case head :: tail =>
          val withHead = combinations(k - 1, tail).map(head :: _)
          val withoutHead = combinations(k, tail)
          withHead ++ withoutHead
      }

  // Group into 3 disjoint subsets of sizes n1, n2, n3
  def group3[A](items: List[A]): List[List[List[A]]] = {
    for {
      g1 <- combinations(2, items)
      rest1 = items.diff(g1)
      g2 <- combinations(3, rest1)
      rest2 = rest1.diff(g2)
      g3 = rest2
    } yield List(g1, g2, g3)
  }

  // Generalized grouping: group into disjoint subsets of given sizes
  def group[A](sizes: List[Int], items: List[A]): List[List[List[A]]] = sizes match {
    case Nil => List(Nil)
    case n :: ns =>
      combinations(n, items).flatMap { g =>
        val rest = items.diff(g)
        group(ns, rest).map(g :: _)
      }
  }
}

@main def mainP27(): Unit = {
  println("Group the elements of a set into disjoint subsets of 2, 3, and 4.")
  val people = List("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida")
  println(s"People: $people")
  val groups = P27.group3(people)
  println(s"All possible groupings: ${groups.take(3)} ... (total: ${groups.length})")

  // Generalized example
  println("\nGeneralized grouping (sizes: 2, 2, 5):")
  val genGroups = P27.group(List(2, 2, 5), people)
  println(s"All possible groupings: ${genGroups.take(3)} ... (total: ${genGroups.length})")
}
