package com.rbleggi.json4s

import org.json4s.*
import org.json4s.ext.JavaTypesSerializers
import org.joda.time.DateTime
import org.json4s.jackson.{JsonMethods, Serialization}

import java.io.StringReader

// 1.1 Custom Serializers (for ADTs, Enums, Custom Types)
sealed trait Animal
case class Dog(name: String) extends Animal
case class Cat(name: String) extends Animal
object AnimalSerializer extends CustomSerializer[Animal](format => (
  {
    case JObject(JField("type", JString("dog")) :: JField("name", JString(name)) :: Nil) => Dog(name)
    case JObject(JField("type", JString("cat")) :: JField("name", JString(name)) :: Nil) => Cat(name)
  },
  {
    case Dog(name) => JObject(JField("type", JString("dog")), JField("name", JString(name)))
    case Cat(name) => JObject(JField("type", JString("cat")), JField("name", JString(name)))
  }
))

// 1.3 Handling Option, Either, and Sealed Traits
case class MaybeValue(opt: Option[String])
// For error handling demo
case class Person(name: String)

@main def json4sDeepDiveDemo(): Unit = {
  // 1.1 Custom Serializers
  println("=== 1.1 Custom Serializers (ADTs, Enums, Custom Types) ===")
  {
    implicit val formats: Formats = DefaultFormats + AnimalSerializer

    val dogJson = Serialization.writePretty(Dog("Rex"))
    println(s"Dog as JSON (pretty):\n$dogJson")
    val catObj = Serialization.read[Animal](dogJson)
    println(s"Dog from JSON: $catObj\n")
  }

  // 1.2 Advanced JValue Transformations
  println("=== 1.2 Advanced JValue Transformations ===")
  val json = JsonMethods.parse("""{
    "foo": 1,
    "bar": {
      "baz": 2
    }
  }""")
  val upper = json transformField { case (name, value) => (name.toUpperCase, value) }
  println(s"Uppercase fields (pretty):\n${JsonMethods.pretty(JsonMethods.render(upper))}")
  val noFoo = json filterField { case (name, _) => name != "foo" }
  println(s"No 'foo' field (pretty):\n${JsonMethods.pretty(JsonMethods.render(JObject(noFoo)))})")
  val defaults = JsonMethods.parse("""{
    "foo": 0,
    "bar": {
      "baz": 0,
      "qux": 3
    }
  }""")
  val merged = json merge defaults
  println(s"Merged (pretty):\n${JsonMethods.pretty(JsonMethods.render(merged))}")
  val diff = json diff defaults
  println(s"Diff (pretty):\n  added:   ${JsonMethods.pretty(JsonMethods.render(diff.added))}\n  changed: ${JsonMethods.pretty(JsonMethods.render(diff.changed))}\n  deleted: ${JsonMethods.pretty(JsonMethods.render(diff.deleted))}\n")

  // 1.3 Handling Option, Either, and Sealed Traits
  println("=== 1.3 Handling Option, Either, and Sealed Traits ===")
  {
    implicit val formats: Formats = DefaultFormats
    val j = JsonMethods.parse("""{"opt":null}""")
    val mv = j.extract[MaybeValue]
    println(s"MaybeValue extracted: $mv\n")
  }

  // 1.4 Using json4s-ext for Dates, UUID, etc.
  println("=== 1.4 Using json4s-ext for Dates, UUID, etc. ===")
  {
    implicit val formats: Formats = DefaultFormats ++ JavaTypesSerializers.all
    val dateJson = Extraction.decompose(DateTime.now)
    println(s"DateTime as JSON (pretty):\n${JsonMethods.pretty(JsonMethods.render(dateJson))}")
    val dateBack = dateJson.extract[DateTime]
    println(s"DateTime from JSON: $dateBack\n")
  }

  // 1.5 Error Handling and Validation
  println("=== 1.5 Error Handling and Validation ===")
  {
    implicit val formats: Formats = DefaultFormats
    val j = JsonMethods.parse("""{"opt":null}""")
    val safeInt: Option[Int] = (j \\ "maybeInt").extractOpt[Int]
    println(s"Safe int extraction: $safeInt")
    try {
      val person = j.extract[Person]
      println(person)
    } catch {
      case e: MappingException => println(s"Caught MappingException: ${e.getMessage}")
    }
    println()
  }

  // 1.6 Performance Tips
  println("=== 1.6 Performance Tips ===")
  println(s"Compact: ${JsonMethods.compact(JsonMethods.render(json))}")
  println(s"Pretty: ${JsonMethods.pretty(JsonMethods.render(json))}\n")

  // 1.7 Integration with HTTP Frameworks (Serialization Example)
  println("=== 1.7 Integration with HTTP Frameworks (Serialization Example) ===")
  {
    implicit val formats: Formats = DefaultFormats
    case class MyCaseClass(foo: String, bar: Int)
    val myObj = MyCaseClass("abc", 123)
    val jsonString = Serialization.writePretty(myObj)
    println(s"Serialized for HTTP (pretty):\n$jsonString\n")
  }

  // 1.8 Streaming and Large JSON Files (Simulated)
  println("=== 1.8 Streaming and Large JSON Files (Simulated) ===")
  val bigJsonStr = """{
    "big": [1, 2, 3, 4, 5]
  }"""
  val reader = new StringReader(bigJsonStr)
  val bigJValue = JsonMethods.parse(reader)
  println(s"Parsed from stream (pretty):\n${JsonMethods.pretty(JsonMethods.render(bigJValue))}\n")

  // 1.9 Custom Formats and Extraction
  println("=== 1.9 Custom Formats and Extraction ===")
  {
    implicit val formats: Formats = DefaultFormats.withBigDecimal
    val bdJson = JsonMethods.parse("""{
      "amount": 123.45
    }""")
    val amount = (bdJson \\ "amount").extract[BigDecimal]
    println(s"BigDecimal extracted: $amount\nJSON (pretty):\n${JsonMethods.pretty(JsonMethods.render(bdJson))}\n")
  }

  // 1.10 Testing JSON with json4s (Simple Assert)
  println("=== 1.10 Testing JSON with json4s (Simple Assert) ===")
  val actual = JsonMethods.parse("""{"foo":1}""")
  val expected = JObject("foo" -> JInt(1))
  assert(actual == expected, "JSON trees should match")
  println(s"JSON trees match!\nActual (pretty):\n${JsonMethods.pretty(JsonMethods.render(actual))}\nExpected (pretty):\n${JsonMethods.pretty(JsonMethods.render(expected))}\n")
}
