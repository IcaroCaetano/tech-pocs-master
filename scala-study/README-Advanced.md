
### **Advanced Monads**
Monads in Scala allow sequencing of computations while abstracting away effects like optionality, errors, and state management. Advanced monads include `IO`, `State`, `Reader`, `Writer`, and `Free`, which are used for functional effect management.

---

### **IO Monad**
The `IO` monad is used to handle side effects (e.g., I/O operations) in a functional way. Libraries like **cats-effect** provide an `IO` monad that allows referential transparency (functions remain pure even with side effects).

#### **Example: Using Cats Effect’s IO Monad**
```scala
import cats.effect.IO

val program: IO[Unit] = IO(println("Hello, world!"))

program.unsafeRunSync() // Runs the effectful computation
```
- `IO` defers execution until explicitly run.
- `unsafeRunSync()` executes the effect but should be avoided in production.

---

### **State Monad**
The `State[S, A]` monad models computations that modify state.

#### **Example: Using the State Monad**
```scala
import cats.data.State

val increment: State[Int, Int] = State { s =>
  (s + 1, s)
}

val result = increment.run(10).value
println(result) // Output: (11, 10)
```
- The first element (`11`) is the new state.
- The second element (`10`) is the old state.

#### **Composing State Transitions**
```scala
val computation = for {
  a <- increment
  b <- increment
} yield a + b

println(computation.run(10).value) // Output: (12, 11)
```

---

### **Reader Monad**
The `Reader` monad represents computations that depend on some shared environment.

#### **Example: Dependency Injection with Reader**
```scala
import cats.data.Reader

case class Config(apiUrl: String)

val getApiUrl: Reader[Config, String] = Reader(_.apiUrl)

val result = getApiUrl.run(Config("https://example.com"))
println(result) // Output: https://example.com
```
- Useful for dependency injection without manually passing parameters.

---

### **Writer Monad**
The `Writer` monad helps in accumulating logs or other side-channel information.

#### **Example: Logging with Writer**
```scala
import cats.data.Writer
import cats.instances.vector._

val computation: Writer[Vector[String], Int] = Writer(Vector("Started"), 42)

val result = computation.run
println(result) // Output: (Vector(Started), 42)
```

#### **Chaining Computations**
```scala
val program = for {
  _ <- Writer(Vector("Step 1"), 10)
  _ <- Writer(Vector("Step 2"), 20)
} yield 30

println(program.run) // Output: (Vector(Step 1, Step 2), 30)
```

---

### **Free Monad**
The `Free` monad allows defining computations without specifying how they are executed, enabling **interpreters** to decide execution strategies.

#### **Example: Defining an Algebra**
```scala
import cats.free.Free
import cats.{Id, ~>}

// Define an Algebra
sealed trait Console[A]
case class PrintLine(msg: String) extends Console[Unit]
case object ReadLine extends Console[String]

// Lift operations into Free
type ConsoleFree[A] = Free[Console, A]

def print(msg: String): ConsoleFree[Unit] = Free.liftF(PrintLine(msg))
def read: ConsoleFree[String] = Free.liftF(ReadLine)

// Interpreter
object ConsoleInterpreter extends (Console ~> Id) {
  def apply[A](fa: Console[A]): Id[A] = fa match {
    case PrintLine(msg) => println(msg)
    case ReadLine       => scala.io.StdIn.readLine()
  }
}

val program = for {
  _ <- print("What is your name?")
  name <- read
  _ <- print(s"Hello, $name!")
} yield ()

program.foldMap(ConsoleInterpreter)
```
- `Free` allows deferring execution.
- Interpreters define how effects are executed.

---

### **Parser Combinators**
Scala provides **parser combinators** to construct complex parsers from smaller ones. The **`scala.util.parsing.combinator`** library helps build these.

#### **Example: Basic Arithmetic Parser**
```scala
import scala.util.parsing.combinator._

class ArithmeticParser extends RegexParsers {
  def number: Parser[Int] = """\d+""".r ^^ { _.toInt }
  def plus: Parser[Int] = number ~ "+" ~ number ^^ { case a ~ _ ~ b => a + b }

  def expr: Parser[Int] = plus | number
}

val parser = new ArithmeticParser
println(parser.parseAll(parser.expr, "3 + 4")) // Output: [1.5] parsed: 7
```
- Combinators like `~`, `|`, and `^^` help in parsing structured text.

---

### **Macros**
Scala Macros allow code generation at compile time, enabling metaprogramming.

#### **Example: A Simple Macro**
```scala
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object Macros {
  def debug(param: Any): String = macro debugImpl

  def debugImpl(c: blackbox.Context)(param: c.Expr[Any]): c.Expr[String] = {
    import c.universe._
    val paramRep = show(param.tree)
    c.Expr[String](q"$paramRep + ' = ' + $param")
  }
}

println(Macros.debug(42)) // Output: 42 = 42
```
- Macros allow introspection and code transformation at compile time.
- They should be used cautiously, as they can make debugging harder.

---