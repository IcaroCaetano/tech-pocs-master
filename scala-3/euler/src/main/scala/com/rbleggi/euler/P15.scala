// Project Euler Problem 15
// How many routes are there through a 20x20 grid moving only right and down?

@main def runP15(): Unit = {
  // The number of lattice paths from (0,0) to (n,n) is (2n)! / (n! * n!)
  def factorial(n: BigInt): BigInt = if (n == 0) 1 else n * factorial(n - 1)

  val gridSize = 20
  val result = factorial(2 * gridSize) / (factorial(gridSize) * factorial(gridSize))

  println(s"The number of routes through a 20x20 grid is: $result")
}

