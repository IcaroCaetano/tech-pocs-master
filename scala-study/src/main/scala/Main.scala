import Etl.{Etl, etl}
import pureconfig.ConfigSource

@main def run =
  for config <- ConfigSource.default.load[EtlConfig]
  yield config.etlImpl match
    case EtlImpl.StringImpl => printResult(config, Etl.StringImpl)
    case EtlImpl.IntImpl    => printResult(config, Etl.IntImpl)
    case EtlImpl.JsonImpl   => printResult(config, Etl.JsonImpl)

private def printResult[A, B](config: EtlConfig, etlImpl: Etl[A, B]): Unit =
  etl(config, etlImpl) match
    case Left(error) => println(s"Failure: $error")
    case Right(_)    => println("Success!")

def sayHi(name: String): Unit =
  val otherName = "Bleggi"
  val n: Double = 2.0
  val listN = List(1, 2, 3, 4)
  val listS = List("hi", "hello")
  val things: List[String | Int | Double] = List(1, "two", 3.0)
  println(s"Hi $name")
end sayHi
