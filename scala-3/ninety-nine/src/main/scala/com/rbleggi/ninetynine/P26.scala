package com.rbleggi.ninetynine

// Generate the combinations of K distinct objects chosen from the N elements of a list.
object P26 {
  def combinations[A](k: Int, items: List[A]): List[List[A]] =
    if (k == 0) List(Nil)
    else
      items match {
        case Nil => Nil
        case head :: tail =>
          // For each combination that includes head, prepend head
          val withHead = combinations(k - 1, tail).map(head :: _)
          // For each combination that does not include head
          val withoutHead = combinations(k, tail)
          withHead ++ withoutHead
      }
}

@main def mainP26(): Unit = {
  println("Generate the combinations of K distinct objects chosen from the N elements of a list.")
  val symbols = List("a", "b", "c", "d", "f")
  println(s"The list is: $symbols")
  val combs = P26.combinations(3, symbols)
  println(s"All combinations of 3: $combs")
}
