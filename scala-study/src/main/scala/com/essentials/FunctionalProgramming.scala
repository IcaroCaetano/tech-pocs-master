package com.essentials

object FunctionalProgramming extends App {

  // Scala is OO
  class Person(name: String) {
    def apply(age: Int) = println(s"I gave aged $age years")
  }

  val bob = new Person("Bob")
  bob.apply(43)
  bob(43) // INVOKING bob as a function === bob.apply(43)

  /*
    Scala runs on the JVM
    Functional programming:
    - compose functions
    - pass functions as args
    - return functions as results

    Conclusion: FunctionX = Funciton1, Function2, ... Function22
   */

  val simpleIncrementer = new Function1[Int, Int] {
    override def apply(arg: Int): Int = arg + 1
  }

  simpleIncrementer.apply(23) //24
  simpleIncrementer(23) // simpleIncrementer.apply(23)
  //defined a funcaion!

  // ALL SCALA FUNCTIONS ARE INSTANCES OF THESE FUNCTION_X TYPES

  val stringConcatenator = new Function2[String, String, String] {
    override def apply(arg1: String, arg2: String): String = arg1 + arg2
  }
  stringConcatenator("I love", " Scala") // "I love Scala"

  //syntax sugars
  // val doubler: Int => Int = (x: Int)
  // val doubler = (x: Int)
  val doubler: Function1[Int, Int] = (x: Int) => 2 * x
  doubler(4) // 8

  /*
    equivalent to much longer:
    new Function1[Int, Int]{
      override def apply(x: Int) = 2 * x
    }
   */

  // higher-order funcitions: take functions as args/return functions as results
  val aMappedList: List[Int] = List(1, 2, 3).map(x => x + 1) //HOF
  val aFlatMappedList = List(1, 2, 3).flatMap(x => List(x, 2 * x))
  val aFilteredList = List(1, 2, 3, 4, 5).filter(_ <= 3) // equivalent to x => x <= 3

  // all pairs between the numbers 1,2,3 and the letters 'a', 'b', 'c'
  val allPairs = List(1, 2, 3).flatMap(number => List('a', 'b', 'c').map(letter => s"$number-$letter"))


}