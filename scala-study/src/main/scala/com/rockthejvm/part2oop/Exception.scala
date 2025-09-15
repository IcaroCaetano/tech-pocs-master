package com.rockthejvm.part2oop

object Exception {

  /**
   * Hierarchy
   * Throwable
   *  - Error - JVM-related crashes (out of memory, stack overflow)
   *  - Exception - user-facing crashes
   *    - RuntimeException
   *    ^^ can define new exceptions
   */

  class MyException extends RuntimeException("Too big of a number - overflowing Int!")

  def sumN(n: Int): Int =
    if (n < 0) throw new IllegalArgumentException("Negative numbers unsupported!")
    else if (n >= 50000) throw new MyException
    else if (n == 0) 0
    else n + sumN(n - 1)

  // try-catch
  val attempt = try {
    println(sumN(-10))
  } catch {
    case e: IllegalArgumentException => -1
    case e: MyException => Int.MaxValue
  }

  val attempt_v2 =
    try
      println(sumN(-10))
    catch
      case e: IllegalArgumentException => -1
      case e: RuntimeException => Int.MaxValue

  def main(args: Array[String]): Unit = {
    println(attempt)
    println(attempt_v2)
  }
}
