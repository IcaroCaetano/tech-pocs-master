## **1. SBT (Simple Build Tool)**
SBT is the **build tool** for Scala, similar to Maven or Gradle in Java. It handles **dependency management, compiling, testing, and packaging**.

### **Basic `build.sbt` File**
```scala
name := "MyScalaProject"
version := "0.1"
scalaVersion := "2.13.12"

// Dependencies
libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"
```

### **Compiling & Running**
```sh
sbt compile
sbt run
```

---

## **2. Gson (Google JSON)**
Gson is a Java-based library for working with JSON. It can be used in Scala for serializing and deserializing JSON.

### **Example: Converting JSON to Object**
```scala
import com.google.gson.Gson

case class Person(name: String, age: Int)

val gson = new Gson()
val json = """{"name": "Alice", "age": 30}"""
val person = gson.fromJson(json, classOf[Person])

println(person) // Output: Person(Alice,30)
```

---

## **3. Spring Data JDBC**
Spring Data JDBC is a lightweight alternative to JPA for database interactions.

### **Example: Defining an Entity**
```scala
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
case class User(@Id id: Long, name: String)
```

### **Repository Interface**
```scala
import org.springframework.data.repository.CrudRepository

trait UserRepository extends CrudRepository[User, Long]
```

---

## **4. Pekko (Akka Fork)**
Pekko is a fork of **Akka** for building concurrent, distributed systems.

### **Example: Actor System**
```scala
import org.apache.pekko.actor.{Actor, ActorSystem, Props}

class HelloActor extends Actor {
  def receive: Receive = {
    case "hello" => println("Hello, Pekko!")
    case _       => println("Unknown message")
  }
}

val system = ActorSystem("MyActorSystem")
val actorRef = system.actorOf(Props[HelloActor], "helloActor")

actorRef ! "hello"
```

---

## **5. Json4S**
Json4S is a JSON library for Scala, providing **immutable JSON support**.

### **Example: JSON Serialization**
```scala
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write

case class Person(name: String, age: Int)
implicit val formats = Serialization.formats(NoTypeHints)

val person = Person("Alice", 25)
val json = write(person)

println(json) // Output: {"name":"Alice","age":25}
```

---

## **6. Slick**
Slick is a **functional relational mapping (FRM) library** for Scala.

### **Example: Connecting to a Database**
```scala
import slick.jdbc.H2Profile.api._

case class User(id: Int, name: String)

class Users(tag: Tag) extends Table[User](tag, "USERS") {
  def id = column[Int]("ID", O.PrimaryKey)
  def name = column[String]("NAME")
  def * = (id, name) <> (User.tupled, User.unapply)
}

val db = Database.forConfig("h2mem1")
val users = TableQuery[Users]
```

---

## **7. Cats (Category Theory Library)**
Cats provides **functional programming abstractions** like Monads and Functors.

### **Example: Using `OptionT` for Composition**
```scala
import cats.data.OptionT
import cats.implicits._

val result: OptionT[Either[String, *], Int] = OptionT(Right(Some(10)))
println(result.value) // Output: Right(Some(10))
```

---

## **8. ZIO (Zero Dependency IO)**
ZIO is a high-performance, asynchronous effect system.

### **Example: Asynchronous Computation**
```scala
import zio._

val program: ZIO[Any, Nothing, Unit] = ZIO.succeed(println("Hello, ZIO!"))

Runtime.default.unsafeRun(program)
```

---

## **9. Scalaz**
Scalaz is similar to **Cats**, providing functional programming constructs.

### **Example: Using `\/` (Either Alternative)**
```scala
import scalaz._
import Scalaz._

val success: String \/ Int = \/-(42)
val failure: String \/ Int = -\/("Error")

println(success) // Output: \/-(42)
println(failure) // Output: -\/(Error)
```

---

## **10. Http4s**
Http4s is a **functional HTTP server and client** library for Scala.

### **Example: Creating a Simple HTTP Server**
```scala
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.blaze._
import cats.effect._

object Http4sExample extends IOApp {
  val service = HttpRoutes.of[IO] {
    case GET -> Root / "hello" => Ok("Hello, Http4s!")
  }

  val httpApp = service.orNotFound

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
```

---

## **11. Circe**
Circe is a JSON library known for **automatic derivation of encoders and decoders**.

### **Example: Encoding and Decoding JSON**
```scala
import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._

case class Person(name: String, age: Int)

val person = Person("Alice", 25)
val json = person.asJson.noSpaces

println(json) // Output: {"name":"Alice","age":25}
```

---

## **12. Specs2**
Specs2 is a **testing framework** for Scala.

### **Example: Writing a Unit Test**
```scala
import org.specs2.mutable._

class ExampleSpec extends Specification {
  "An addition function" should {
    "return 4 when adding 2 + 2" in {
      (2 + 2) mustEqual 4
    }
  }
}
```

---

## **13. Tapir**
Tapir helps define **type-safe HTTP endpoints**.

### **Example: Defining an Endpoint**
```scala
import sttp.tapir._
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter

val helloEndpoint = endpoint.get
  .in("hello")
  .out(stringBody)

println(helloEndpoint.show) // Output: GET /hello -> String
```