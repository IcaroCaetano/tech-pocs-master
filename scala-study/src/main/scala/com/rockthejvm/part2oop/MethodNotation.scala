package com.rockthejvm.part2oop

object MethodNotation {

  class Person(val name: String) {
    def greet(another: Person): String =
      s"$name says: Hi, ${another.name}, how are you?"

    // explicit infix to other devs
    infix def likes(movie: String): String =
      s"$name says: I adore the movie $movie!!!"

    infix def ?!(another: Person): String =
      s"$name says: Hey ${another.name}, how could you do this?!"

    def apply(progrLang: String): Unit =
      println(s"[$name] Programming in $progrLang")

    def apply(progrLang: String, algorithm: String): Unit =
      println(s"[$name] Programming in $progrLang, applying the algorithm $algorithm")
  }

  val alice = Person("Alice")
  val bob = Person("Bob")

  def main(args: Array[String]): Unit = {
    println(alice.greet(bob))
    println(alice greet bob) // exactly the same thing - infix notation
    // infix notation ONLY works on methods with ONE argument

    println(alice.likes("Forrest Gump"))
    println(alice likes "Forrest Gump")

    println(alice.?!(bob))
    println(alice ?! bob) // Akka/Pekko. Cats, ZIO

    alice.apply("Scala")
    alice("Scala") // same as alice.apply("Scala")
    alice("Scala", "Dynamic Programmimg")
  }
}
