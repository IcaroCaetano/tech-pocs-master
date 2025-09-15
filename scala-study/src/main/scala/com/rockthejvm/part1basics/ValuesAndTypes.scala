package com.rockthejvm.part1basics

object ValuesAndTypes {

  val meaningOfLife: Int = 42
  // const int meaningOfLife = 42;
  // values cannot be reassigned

  // type inference
  val anInteger = 67 + 64

  // common types
  val aBoolean: Boolean = false // or true
  val aChar: Char = 'a'
  val anInt: Int = 45 // 4 bytes
  val aShort: Short = 5243 // 2 bytes
  val aLong: Long = 5362856327L // 8-byte integers
  val aFloat: Float = 2.4f // 4-byte decimal
  val aDouble: Double = 3.14 // 8-byte decimal

  // string type
  val aString: String = "Scala Rocks"


  def main(args: Array[String]): Unit = {
    println(meaningOfLife)
    println(anInteger)
    println(aBoolean)
    println(aChar)
    println(anInt)
    println(aShort)
    println(aLong)
    println(aFloat)
    println(aDouble)
    println(aString)
  }
}
