package com.rockthejvm.part2oop

object Inheritance {

  class VideoGame {
    val gameType = "interactive"

    def play(): Unit = println("game running!")
  }

  // single-class inheritance
  class Shooter extends VideoGame {
    // can define my own fields/methods
    def multiplayer(): Unit =
      play()
      println("boom, boom!")
  }

  val crysis = new Shooter

  // inherit primary constructor
  class Person(val name: String, age: Int)

  class Adult(name: String, age: Int) extends Person(name, age) // MUST specify constructor arguments

  val daniel = new Adult("Daniel", 99)

  // overriding = provide a new impl for methods in derived classes
  class RPG extends VideoGame {
    override val gameType = "role-playing"

    override def play(): Unit = println("level up!")
  }

  // subtype polimorphism

  val wow: VideoGame = new RPG // declare a value of a parent type, provide an instance of a derived type

  // annonymous classes
  val psychonauts = new VideoGame {
    override val gameType: String = "platformer"
  }

  /*
    class AnonClass$1234 extends VideoGame{
      override val gameType: String = "platformer"
    }

    val psychonauts: VideoGame = new AnonClass$1234
   */

  def main(args: Array[String]): Unit = {
    println(crysis.gameType)
    crysis.multiplayer()
    println(daniel.name)

    println(wow.gameType)
    wow.play()
  }
}
