# json4s Deep Dive
A Practical Guide for Scala 3 Developers

---

## 1. Advanced Usage: Deep Dive

### 1.1 Custom Serializers (for ADTs, Enums, Custom Types)
Custom serializers let you control how your types are (de)serialized. Useful for ADTs, enums, and legacy formats.

```scala
import org.json4s._

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

implicit val formats: Formats = DefaultFormats + AnimalSerializer

val dogJson = org.json4s.native.Serialization.write(Dog("Rex"))
val catObj = org.json4s.native.Serialization.read[Animal](dogJson)
```

---

### 1.2 Advanced JValue Transformations
You can deeply transform, filter, diff, merge, and patch JSON trees.

```scala
import org.json4s._
import org.json4s.native.JsonMethods._

val json = parse("""{"foo":1,"bar":{"baz":2}}""")

// Deeply update all field names to uppercase
val upper = json transformField { case (name, value) => (name.toUpperCase, value) }

// Remove all fields named "foo"
val noFoo = json filterField { case (name, _) => name != "foo" }

// Merge two JSONs
defaults = parse("""{"foo":0,"bar":{"baz":0,"qux":3}}""")
val merged = json merge defaults

// Diff two JSONs
val diff = json diff defaults
```

---

### 1.3 Handling Option, Either, and Sealed Traits
json4s handles Option and Either out of the box, and with custom serializers, you can handle sealed traits (ADTs).

```scala
case class MaybeValue(opt: Option[String])
val j = parse("""{"opt":null}""")
val mv = j.extract[MaybeValue] // mv.opt == None
```

---

### 1.4 Using json4s-ext for Dates, UUID, etc.
json4s-ext provides serializers for JodaTime, Java8 time, UUID, and more.

```scala
import org.json4s.ext.JavaTypesSerializers
implicit val formats: Formats = DefaultFormats ++ JavaTypesSerializers.all
```

---

### 1.5 Error Handling and Validation
Always wrap extraction in try/catch or use extractOpt for safe extraction.

```scala
val safeInt: Option[Int] = (jValue \ "maybeInt").extractOpt[Int]
try {
  val person = jValue.extract[Person]
} catch {
  case e: MappingException => println(s"Invalid JSON: ${e.getMessage}")
}
```

---

### 1.6 Performance Tips
- Use `compact(render(jValue))` for fast serialization.
- Use `pretty(render(jValue))` for human-readable output.
- For very large JSON, use `JsonParser.parse` with a Reader/InputStream.

---

### 1.7 Integration with HTTP Frameworks
json4s integrates with Play, Akka HTTP, http4s, etc. Example with Akka HTTP:

```scala
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import org.json4s.native.Serialization

implicit val formats = DefaultFormats
val jsonString = Serialization.write(myCaseClass)
```

---

### 1.8 Streaming and Large JSON Files
For huge files, use `JsonParser.parse` with a stream:

```scala
import org.json4s.native.JsonParser
val reader = new java.io.FileReader("big.json")
val jValue = JsonParser.parse(reader)
```

---

### 1.9 Custom Formats and Extraction
You can define your own Formats for custom (de)serialization logic.

```scala
implicit val formats: Formats = DefaultFormats.withBigDecimal
```

---

### 1.10 Testing JSON with json4s
You can compare JValue trees directly in tests:

```scala
import org.scalatest.flatspec.AnyFlatSpec
import org.json4s._
import org.json4s.native.JsonMethods._

class JsonSpec extends AnyFlatSpec {
  "My JSON" should "match expected" in {
    val actual = parse("""{"foo":1}""")
    val expected = JObject("foo" -> JInt(1))
    assert(actual == expected)
  }
}
```

---

# 2. References & Further Reading
- [json4s Documentation](https://json4s.org/)
- [json4s-ext](https://github.com/json4s/json4s#json4s-ext)
- [json4s Source Code](https://github.com/json4s/json4s)
- [Scala 3 Migration Guide](https://docs.scala-lang.org/scala3/guides/migration/compatibility-intro.html)
