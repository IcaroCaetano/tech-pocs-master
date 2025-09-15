package com.essentials

object Basics extends App {

  val meaningOfLife: Int = 42

  val aBoolean = false

  val aString = "Scala"
  val aComposedString = "Scala" + " " + "Scala"
  val anInterpolatedString = s"The meaning of life is $meaningOfLife"

  val anExpression = 2 + 3

  val ifExpression = if (meaningOfLife > 43) 56 else 999
  val chainedIfExpression =
    if (meaningOfLife > 43) 56
    else if (meaningOfLife < 0) -2
    else if (meaningOfLife > 999) 78
    else 0

  val aCodeBlock = {
    val aLocalValue = 67
    //value of clock is the value of the last expression
    aLocalValue + 3
  }

  def myFunction(x: Int, y: String): String = y + " " + x

  def myFunctionBlock(x: Int, y: String): String = {
    y + " " + x
  }

  def factorial(n: Int): Int =
    if (n <= 1) 1
    else n * factorial(n - 1)

  // In Scala we don`t use loops or iteration, we use RECURSION!
  // the Unit type = no meaningful value === "void" in other languages
  // type of SIDE EFFECTS

  println("Scala")

  def myUnitReturnFunction(): Unit = {
    println("I don`t love returning Unit")
  }

  val theUnit = ()

}