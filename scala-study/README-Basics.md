## **Scala Basics**
Scala is a statically-typed, functional, and object-oriented programming language that runs on the JVM. It blends functional programming (FP) with object-oriented programming (OOP), making it a powerful language for building scalable applications.

Key features:
- **Immutable values** by default
- **Type inference** (Scala infers types automatically)
- **Concise syntax**
- **First-class functions** (functions are treated like variables)
- **Pattern Matching** for expressive and powerful control structures

---

## **Variables**
Scala provides two types of variable declarations:

1. **`val` (Immutable Variables)**
    - Once assigned, it cannot be reassigned.
    - Recommended for functional programming.

   ```scala
   val name: String = "Scala"
   val a = 400:Double
   val age = 30 // Type inference
   ```

2. **`var` (Mutable Variables)**
    - Can be reassigned, but mutable variables should be avoided in functional programming.

   ```scala
   var count: Int = 10
   count = 20 // Allowed because it's mutable
   ```

3. **`lazy val` (Lazy Immutable Variables)**
   - lazy val will not evaluated until referenced
   - Any subsequent calls to the val will return the same value when initially called upon
   - There is no such thing as a lazy var
   - lazy val can be forgiving if an exception happens

   ```scala
   var divisor = 0
   lazy val quotient = 40 / divisor
   println(quitient) // throw ArithmeticException: / by zero and give another chance
   divisor = 2
   println(quitient) // ok
   ```

---

## **Functions**
Functions are first-class citizens in Scala, meaning they can be assigned to variables, passed as arguments, and returned from other functions.

### **Defining Functions**
```scala
def add(x: Int, y: Int): Int = x + y
println(add(5, 10)) // Output: 15
```

### **Anonymous Functions (Lambdas)**
```scala
val multiply = (x: Int, y: Int) => x * y
println(multiply(4, 5)) // Output: 20
```

### **Higher-Order Functions**
Functions that take other functions as parameters or return functions.

```scala
def operate(x: Int, y: Int, op: (Int, Int) => Int): Int = op(x, y)

val sum = operate(4, 5, (a, b) => a + b)
println(sum) // Output: 9
```

---

## **Classes**
Classes define the structure of objects and encapsulate data with behavior.

```scala
class Person(val name: String, val age: Int) {
  def greet(): String = s"Hello, my name is $name and I'm $age years old."
}

val person = new Person("Alice", 25)
println(person.greet()) // Output: Hello, my name is Alice and I'm 25 years old.
```

### **Primary Constructor**
```scala
class Car(val brand: String, val model: String, val year: Int)

val car = new Car("Tesla", "Model S", 2022)
println(s"${car.brand} ${car.model} - ${car.year}") // Output: Tesla Model S - 2022
```

---

## **Traits**
Traits are similar to Java interfaces but can contain concrete methods and fields. They support multiple inheritance.

```scala
trait Logger {
  def log(message: String): Unit = println(s"LOG: $message")
}

class Service extends Logger {
  def process(): Unit = {
    log("Processing started...")
  }
}

val service = new Service()
service.process() // Output: LOG: Processing started...
```

### **Multiple Traits**
```scala
trait Database {
  def save(): Unit = println("Saving to database...")
}

trait Network {
  def send(): Unit = println("Sending data over network...")
}

class AppService extends Database with Network

val app = new AppService()
app.save()  // Output: Saving to database...
app.send()  // Output: Sending data over network...
```

---

## **Objects**
Objects in Scala are singletons. They are used to define utility methods, factory patterns, or as a main entry point.

```scala
object Utils {
  def greet(name: String): String = s"Hello, $name!"
}

println(Utils.greet("John")) // Output: Hello, John!
```

---

## **Case Classes**
Case classes provide immutable data structures with built-in functionality for equality, copy operations, and pattern matching.

```scala
case class User(name: String, age: Int)

val user1 = User("Alice", 30)
val user2 = user1.copy(age = 31)

println(user1) // Output: User(Alice,30)
println(user2) // Output: User(Alice,31)
```

### **Advantages of Case Classes:**
- Automatically generates `equals`, `hashCode`, and `toString`
- Immutable by default
- Supports pattern matching

---

## **Case Objects**
A **case object** is similar to a case class but without parameters. It’s useful for representing singletons in pattern matching.

```scala
sealed trait Command
case object Start extends Command
case object Stop extends Command

def execute(command: Command): Unit = command match {
  case Start => println("Starting...")
  case Stop  => println("Stopping...")
}

execute(Start) // Output: Starting...
```

---

## **Sealed Traits**
A **sealed trait** ensures that all subclasses must be defined in the same file. This is useful for exhaustive pattern matching.

```scala
sealed trait Shape
case class Circle(radius: Double) extends Shape
case class Rectangle(width: Double, height: Double) extends Shape

def describe(shape: Shape): String = shape match {
  case Circle(r)     => s"A circle with radius $r"
  case Rectangle(w, h) => s"A rectangle with width $w and height $h"
}

val shape: Shape = Circle(5.0)
println(describe(shape)) // Output: A circle with radius 5.0
```

### **Why Use Sealed Traits?**
- Ensures pattern matching is exhaustive.
- Prevents the extension of the trait outside the file.

---

## **Pattern Matching**
Pattern Matching in Scala is similar to a `switch` statement in other languages but much more powerful. It allows matching on types, values, and even complex structures.

### **Example: Matching on Values**
```scala
val number = 5

val result = number match {
  case 1 => "One"
  case 2 => "Two"
  case _ => "Something else"
}

println(result) // Output: Something else
```

### **Example: Matching on Case Classes**
```scala
sealed trait Animal
case class Dog(name: String) extends Animal
case class Cat(name: String) extends Animal

def describe(animal: Animal): String = animal match {
  case Dog(name) => s"This is a dog named $name"
  case Cat(name) => s"This is a cat named $name"
}

println(describe(Dog("Buddy"))) // Output: This is a dog named Buddy
```

---

## **Tuples**
A tuple is a collection of elements that can hold different types. It is useful when you need to return multiple values from a function.

```scala
val person: (String, Int) = ("Alice", 25)
println(person._1) // Output: Alice
println(person._2) // Output: 25
```

### **Returning Multiple Values from a Function**
```scala
def getCoordinates(): (Int, Int) = (10, 20)

val (x, y) = getCoordinates()
println(s"X: $x, Y: $y") // Output: X: 10, Y: 20
```

---

## **Generics**
Generics allow creating reusable classes, traits, and methods that work with different types.

```scala
class Box[T](val value: T) {
  def getValue: T = value
}

val intBox = new Boxox = new Box[String]("Scala")

println(intBox.getValue) // Output: 10
println(strBox.getValue) // Output: Scala
```

### **Generic Methods**
```scala
def printValue[T](value: T): Unit = println(value)

printValue(100)     // Output: 100
printValue("Hello") // Output: Hello
```

---

## **Monads**
A Monad is a functional programming concept that represents computations as sequences of operations.

### **Monad Laws:**
1. **Left Identity**: `pure(a).flatMap(f) == f(a)`
2. **Right Identity**: `m.flatMap(pure) == m`
3. **Associativity**: `m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))`

**Examples of Monads in Scala:** `Option`, `Try`, `Either`, `Future`

---

## **Option**
`Option[T]` represents a value that may or may not be present (`Some(value)` or `None`).

```scala
def findUser(id: Int): Option[String] = {
  if (id == 1) Some("Alice") else None
}

val user = findUser(1)
println(user.getOrElse("Unknown")) // Output: Alice
```

### **Using `map` and `flatMap` with Option**
```scala
val nameOpt: Option[String] = Some("Scala")
val upperName = nameOpt.map(_.toUpperCase)
println(upperName) // Output: Some(SCALA)
```

---

## **Try**
`Try[T]` is used for handling exceptions in a functional way. It can be either `Success(value)` or `Failure(exception)`.

```scala
import scala.util.{Try, Success, Failure}

def divide(x: Int, y: Int): Try[Int] = Try(x / y)

val result = divide(10, 2)
result match {
  case Success(value) => println(s"Result: $value") // Output: Result: 5
  case Failure(ex)    => println(s"Error: ${ex.getMessage}")
}
```

---

## **Either**
`Either[A, B]` represents a value that is either `Left(A)` (an error) or `Right(B)` (a success).

```scala
def divide(a: Int, b: Int): Either[String, Int] = {
  if (b == 0) Left("Cannot divide by zero")
  else Right(a / b)
}

val result = divide(10, 2)
println(result) // Output: Right(5)

val error = divide(10, 0)
println(error) // Output: Left(Cannot divide by zero)
```

---

## **Future**
`Future[T]` is used for asynchronous programming, allowing computations that may complete in the future.

```scala
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

val futureResult: Future[Int] = Future {
  Thread.sleep(1000)
  42
}

futureResult.map(result => println(s"Result: $result"))
```

---

## **List**
A `List` is an immutable sequence of elements.

```scala
val numbers = List(1, 2, 3, 4, 5)
println(numbers.head) // Output: 1
println(numbers.tail) // Output: List(2, 3, 4, 5)
```

### **Using `map` and `filter`**
```scala
val doubled = numbers.map(_ * 2)
println(doubled) // Output: List(2, 4, 6, 8, 10)

val evens = numbers.filter(_ % 2 == 0)
println(evens) // Output: List(2, 4)
```

---

## **Map**
A `Map` is a collection of key-value pairs.

```scala
val userAges = Map("Alice" -> 25, "Bob" -> 30)

println(userAges("Alice")) // Output: 25
println(userAges.getOrElse("Charlie", 0)) // Output: 0
```

### **Updating a Map**
```scala
val updatedMap = userAges + ("Charlie" -> 35)
println(updatedMap) // Output: Map(Alice -> 25, Bob -> 30, Charlie -> 35)
```

---

### **For Comprehensions**
For comprehensions provide a concise way to work with monadic structures like `Option`, `List`, and `Future`. They are syntactic sugar for `flatMap` and `map` operations.

#### **Example: Working with Option**
```scala
val maybeName: Option[String] = Some("Alice")
val maybeAge: Option[Int] = Some(25)

val result = for {
  name <- maybeName
  age <- maybeAge
} yield s"$name is $age years old"

println(result) // Output: Some(Alice is 25 years old)
```

#### **Example: Working with List**
```scala
val numbers = List(1, 2, 3)
val result = for (n <- numbers if n % 2 == 0) yield n * 2
println(result) // Output: List(4)
```

---

### **Mutable and Immutable Collections**
Scala has both **immutable** and **mutable** collections.

#### **Immutable Collections (Recommended)**
Immutable collections are preferred in functional programming because they prevent unintended modifications.

```scala
val immutableList = List(1, 2, 3)
val newList = immutableList :+ 4
println(newList) // Output: List(1, 2, 3, 4)
```

#### **Mutable Collections (Avoid if possible)**
Mutable collections allow in-place modifications.

```scala
import scala.collection.mutable.ListBuffer
val mutableList = ListBuffer(1, 2, 3)
mutableList += 4
println(mutableList) // Output: ListBuffer(1, 2, 3, 4)
```

---

### **ScalaTest**
ScalaTest is a popular testing framework for Scala.

#### **Basic Test Example**
```scala
import org.scalatest.funsuite.AnyFunSuite

class ExampleTest extends AnyFunSuite {
  test("Addition works") {
    assert(2 + 2 == 4)
  }
}
```

---

### **ScalaCheck**
ScalaCheck is a property-based testing library that generates test cases automatically.

#### **Example: Checking Properties**
```scala
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

object StringProperties extends Properties("String") {
  property("concatenation length") = forAll { (a: String, b: String) =>
    (a + b).length == a.length + b.length
  }
}
```

---

### **Higher Order Functions**
Higher-order functions take other functions as arguments or return functions.

#### **Example: Function as Argument**
```scala
def applyFunction(f: Int => Int, x: Int): Int = f(x)

val result = applyFunction(_ * 2, 10)
println(result) // Output: 20
```

---

### **Currying**
Currying transforms a function that takes multiple arguments into a sequence of functions.

```scala
def add(x: Int)(y: Int): Int = x + y

val add10 = add(10) _ // Partial application
println(add10(5)) // Output: 15
```

---

### **Partial Functions**
A Partial Function is a function that is only defined for certain inputs.

```scala
val divide: PartialFunction[Int, Int] = {
  case x if x != 0 => 10 / x
}

println(divide(2)) // Output: 5
```

---

### **Type Classes**
Type classes allow adding behavior to types without modifying them.

#### **Example: Defining a Type Class**
```scala
trait Show[T] {
  def show(value: T): String
}

object ShowInstances {
  implicit val intShow: Show[Int] = (value: Int) => s"Int: $value"
}

def printValue[T](value: T)(implicit show: Show[T]): Unit =
  println(show.show(value))

import ShowInstances._
printValue(42) // Output: Int: 42
```

---

### **Tail Recursion**
Tail recursion optimizes recursive calls to prevent stack overflow.

```scala
import scala.annotation.tailrec

def factorial(n: Int): Int = {
  @tailrec
  def loop(acc: Int, n: Int): Int =
    if (n <= 1) acc else loop(acc * n, n - 1)

  loop(1, n)
}

println(factorial(5)) // Output: 120
```

---

### **Reflection**
Reflection allows runtime introspection of classes and objects.

```scala
import scala.reflect.runtime.universe._

case class Person(name: String)
val personType = typeOf[Person]
println(personType) // Output: Person
```

---

### **Implicits**
Implicits allow automatic resolution of values or conversions.

#### **Example: Implicit Parameters**
```scala
def greet(name: String)(implicit greeting: String): String =
  s"$greeting, $name"

implicit val defaultGreeting: String = "Hello"

println(greet("Scala")) // Output: Hello, Scala
```

#### **Example: Implicit Conversions**
```scala
implicit def intToString(n: Int): String = n.toString

val result: String = 10 // Implicitly converted
println(result) // Output: "10"
```