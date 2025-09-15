package com.rbleggi.loggerrouter

import com.rbleggi.loggerbuilderroutersystem.LogLevel.{ERROR, INFO}

import scala.concurrent.{ExecutionContext, Future}
import java.io.{BufferedWriter, FileWriter}

enum LogLevel:
  case INFO, ERROR

trait LogStrategy:
  def log(level: LogLevel, msg: String): Unit

class FileSystemStrategy(path: String) extends LogStrategy:
  override def log(level: LogLevel, msg: String): Unit =
    val bw = BufferedWriter(FileWriter(path, true))
    bw.write(s"[$level] $msg\n")
    bw.close()

class ELKStrategy(endpoint: String) extends LogStrategy:
  override def log(level: LogLevel, msg: String): Unit =
    println(s"[ELK @ $endpoint] $level: $msg")

class ConsoleStrategy extends LogStrategy:
  override def log(level: LogLevel, msg: String): Unit =
    println(s"[Console] $level: $msg")

class AsyncStrategy(inner: LogStrategy, ec: ExecutionContext) extends LogStrategy:
  override def log(level: LogLevel, msg: String): Unit =
    Future {
      inner.log(level, msg)
    }(ec)

class LoggerRouter(strategies: List[LogStrategy]):
  def log(level: LogLevel, msg: String): Unit =
    strategies.foreach(_.log(level, msg))

@main def runLoggerExample(): Unit =
  val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  val strategies: List[LogStrategy] = List(
    FileSystemStrategy("app.log"),
    AsyncStrategy(ELKStrategy("http://localhost:9200"), ec),
    ConsoleStrategy()
  )

  val logger = LoggerRouter(strategies)

  logger.log(INFO, "User login successful")
  logger.log(ERROR, "Database connection failed")
