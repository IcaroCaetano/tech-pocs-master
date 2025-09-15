package com.rbleggi.euler

@main def mainP06(): Unit = {
  val sumOfSquares10 = (1 to 10).map(n => n * n).sum
  val squareOfSum10 = math.pow((1 to 10).sum, 2).toLong
  val difference10 = squareOfSum10 - sumOfSquares10
  println(s"Difference for first 10 natural numbers: $difference10")

  val sumOfSquares100 = (1 to 100).map(n => n * n).sum
  val squareOfSum100 = math.pow((1 to 100).sum, 2).toLong
  val difference100 = squareOfSum100 - sumOfSquares100
  println(s"Difference for first 100 natural numbers: $difference100")
}

