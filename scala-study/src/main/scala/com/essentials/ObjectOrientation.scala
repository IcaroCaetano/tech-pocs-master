package com.essentials

object ObjectOrientation extends App {

  // java public static boid main(String[] args)

  //class and instance
  class Animal {
    val age: Int = 0

    def eat() = println("Eating")
  }

  val anAnimal = new Animal

  // inheritance
  class Dog extends Animal

  class Cat(name: String) extends Animal

  val aCat = new Cat("name")

  // constructor arguments are NOT fields: need to put a val before the constructor argument
  class Bird(val name: String) extends Animal

  //subtype polymorrphism
  val aDeclaredAnimal: Animal = new Cat("name2")
  aDeclaredAnimal.eat() // the most derived method will be called at runtime

  abstract class WalkingAnimal {
    val hasLegs = true // by default public, can restrict by adding private or protected

    def walk(): Unit
  }

  // interface = ultimate abstract type
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  trait Philosopher {
    def ?!(thought: String): Unit //valid method name
  }

  // single-class inheritance, multi-trait "mixing"
  class Crocodile extends Animal with Carnivore with Philosopher {
    override def eat(animal: Animal): Unit = println("Eating animal")

    override def ?!(thought: String): Unit = println(s"I was thinking: $thought")
  }

  val aCroc = new Crocodile
  aCroc.eat(aCat)
  aCroc eat aCat // infix notation = object method argument, only available for methods with ONE argument
  aCroc ?! "What if we could fly?"

  //operators in Scala are actually methods
  val basicMath = 1 + 2
  val anotherBasicMath = 1.+(2)

  //anonymous classes
  val dinosaur = new Carnivore {
    override def eat(animal: Animal): Unit = println("I am a dinosaur so I can eat pretty much anything")
  }

  /*
    what you tell the compiler:
    class Carnivore_Anonymous_35728 extends Carnivore{
      override def eat(animal: Animal): Unit = println("I am a dinosaur so I can eat pretty much anything")
    }

    val dinosaur = new Carnivore_Anonymous_54728
   */

  // singleton object
  object MySingleton { // the only instance of the MySingleton type
    val mySpecialValue = 53728

    def mySpecialMethod(): Int = 5327

    def apply(x: Int): Int = x + 1
  }

  MySingleton.mySpecialMethod()
  MySingleton.apply(65)
  MySingleton(65) // equivalent to MySingleton.apply(65)

  object Animal { // companions - companion object
    // companions can access each other's private fields/methods
    // singleton Animal and instances of Animal are different things
    val canLiveIndefinitely = false
  }

  val animalsCanLiveForever = Animal.canLiveIndefinitely // static fields/methods

  /*
  case classes = lightweight data structures with some boilerplate
  - sensible equals and hash code
  - serialization
  - companion with apply
  - pattern matching
   */
  case class Person(name: String, age: Int)

  // may be constructed without new
  val bob = Person("Bob", 54) // Person.apply("Bob",54)

  // exceptions
  try {
    // code that can throw
    val x: String = null
    x.length
  } catch {
    case e: Exception => "Some faulty error message"
  } finally {
    // execute some code no matter what
  }

  // generics
  abstract class MyList[T] {
    def head: T

    def tail: MyList[T]
  }

  // using a generic with a concrete type
  val aList: List[Int] = List(1, 2, 3) // List.apply(1,2,3)
  val first = aList.head
  val rest = aList.tail
  val aStringList = List("hello", "scala")
  val firstString = aStringList.head // string

  // Point #1: in Scala we usually operate with IMMUTABLE values/objects
  // Any modification to an object must return ANOTHER object
  /*
  Benefits:
  1) works miracles in multithreaded/distributed env
  2) helps making sense of the code ("reasoning about")
   */
  val reversedList = aList.reverse // returns a NEW List

  //Point #2: Scala is closest to the 00(object orientation) ideal
}