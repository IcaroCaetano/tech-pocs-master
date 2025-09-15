package com.rockthejvm.part1basics

object Instructions {

  // instructions vs expressions
  // everything in Scala is an expression
  // do this, do that, repeat(10) { ... } - imperative programming
  // 2 + 3, IO effects, map, flatmap, filter - functional programming

  //in Scala, an instruction is a structure (expression) returning Unit

  val printing: Unit = println("this is an instruction") // side effect
  val theUnit: Unit = () // the only possible value of type Unit

  // instructions - code blocks returning Unit
  val aCodeBlock =
    val aLocalValue = 45
    println("Instruction 1")
    println("Instruction 2")

  // variables in Scala
  var aVariable = 10
  aVariable = 11
  val reassignment: Unit = aVariable += 1

  def main(args: Array[String]): Unit = {
    println(printing == theUnit)

    // loops in Scala
    var theNumber = 1
    while (theNumber <= 10)
      println(theNumber)
      theNumber += 1
  }
}
