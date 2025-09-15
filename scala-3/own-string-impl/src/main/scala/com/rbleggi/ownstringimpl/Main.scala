package com.rbleggi.ownstringimpl

class MyString(val value: String) {
  def toArray: Array[Char] = {
    val arr = new Array[Char](value.length)
    var i = 0
    while (i < value.length) {
      arr(i) = value.charAt(i)
      i += 1
    }
    arr
  }
  def foreach(f: Char => Unit): Unit = {
    var i = 0
    while (i < value.length) {
      f(value.charAt(i))
      i += 1
    }
  }
  def reverse: MyString = {
    val arr = new Array[Char](value.length)
    var i = 0
    while (i < value.length) {
      arr(i) = value.charAt(value.length - 1 - i)
      i += 1
    }
    MyString(new String(arr))
  }
  def iterator: Iterator[Char] = new Iterator[Char] {
    private var i = 0
    def hasNext: Boolean = i < value.length
    def next(): Char = {
      val c = value.charAt(i)
      i += 1
      c
    }
  }
  def length: Int = {
    var i = 0
    try {
      while (true) {
        value.charAt(i)
        i += 1
      }
      i
    } catch {
      case _: IndexOutOfBoundsException => i
    }
  }
  def charAt(idx: Int): Char = {
    if (idx < 0 || idx >= value.length) throw new IndexOutOfBoundsException()
    var i = 0
    var c = '\u0000'
    while (i <= idx) {
      c = value.charAt(i)
      i += 1
    }
    c
  }
  def equals(other: Any): Boolean = other match {
    case s: MyString => {
      if (length != s.length) false
      else {
        var i = 0
        var eq = true
        while (i < length && eq) {
          if (charAt(i) != s.charAt(i)) eq = false
          i += 1
        }
        eq
      }
    }
    case s: String => {
      if (length != s.length) false
      else {
        var i = 0
        var eq = true
        while (i < length && eq) {
          if (charAt(i) != s.charAt(i)) eq = false
          i += 1
        }
        eq
      }
    }
    case _ => false
  }
  def isEmpty: Boolean = length == 0
  def replace(oldChar: Char, newChar: Char): MyString = {
    val arr = new Array[Char](length)
    var i = 0
    while (i < length) {
      arr(i) = if (charAt(i) == oldChar) newChar else charAt(i)
      i += 1
    }
    MyString(new String(arr))
  }
  def substring(start: Int, end: Int): MyString = {
    if (start < 0 || end > length || start > end) throw new IndexOutOfBoundsException()
    val arr = new Array[Char](end - start)
    var i = start
    while (i < end) {
      arr(i - start) = charAt(i)
      i += 1
    }
    MyString(new String(arr))
  }
  def trim: MyString = {
    var start = 0
    var end = length - 1
    while (start <= end && (charAt(start) == ' ' || charAt(start) == '\t' || charAt(start) == '\n')) start += 1
    while (end >= start && (charAt(end) == ' ' || charAt(end) == '\t' || charAt(end) == '\n')) end -= 1
    if (start > end) MyString("") else substring(start, end + 1)
  }
  def toJson: String = {
    val sb = new StringBuilder
    sb.append('{')
    sb.append('"'); sb.append("value"); sb.append('"'); sb.append(':'); sb.append('"')
    var i = 0
    while (i < length) {
      val c = charAt(i)
      if (c == '"' || c == '\\') sb.append('\\')
      sb.append(c)
      i += 1
    }
    sb.append('"'); sb.append('}')
    sb.toString
  }
  def indexOf(c: Char): Int = {
    var i = 0
    var idx = -1
    while (i < length && idx == -1) {
      if (charAt(i) == c) idx = i
      i += 1
    }
    idx
  }
  override def hashCode: Int = {
    var h = 0
    var i = 0
    while (i < length) {
      h = 31 * h + charAt(i).toInt
      i += 1
    }
    h
  }
  override def toString: String = {
    val arr = new Array[Char](length)
    var i = 0
    while (i < length) {
      arr(i) = charAt(i)
      i += 1
    }
    new String(arr)
  }
}
object MyString {
  def apply(s: String): MyString = new MyString(s)
}

@main def run(): Unit = {
  val s = MyString("  Hello, Scala!  ")
  println(s"toArray: ${s.toArray.mkString(",")}")
  print("foreach: "); s.foreach(c => print(c)); println()
  println(s"reverse: ${s.reverse}")
  println(s"iterator: ${s.iterator.mkString}")
  println(s"length: ${s.length}")
  println(s"charAt(1): ${s.charAt(1)}")
  println(s"equals: ${s.equals(MyString("  Hello, Scala!  "))}")
  println(s"isEmpty: ${s.isEmpty}")
  println(s"replace: ${s.replace('l', 'x')}")
  println(s"substring(2,7): ${s.substring(2,7)}")
  println(s"trim: ${s.trim}")
  println(s"toJson: ${s.toJson}")
  println(s"indexOf('S'): ${s.indexOf('S')}")
  println(s"hashCode: ${s.hashCode}")
}
