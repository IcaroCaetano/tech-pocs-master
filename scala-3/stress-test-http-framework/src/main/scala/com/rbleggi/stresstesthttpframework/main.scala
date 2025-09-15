package com.rbleggi.stresstesthttpframework

import scala.concurrent.*
import scala.util._
import java.net.http.*
import java.net.URI
import java.time.Duration
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.Duration.Inf

abstract class StressTestTemplate {
  def prepare(): Unit
  def execute(): Unit
  def report(): Unit
  final def runTest(): Unit = {
    prepare()
    execute()
    report()
  }
}

class HttpStressTest(url: String, requests: Int, concurrency: Int) extends StressTestTemplate {
  private var results: Seq[Long] = Seq.empty
  private implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(concurrency))
  private val client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()

  def prepare(): Unit = {
    results = Seq.empty
  }

  def execute(): Unit = {
    val futures = (1 to requests).map { _ =>
      Future {
        val start = System.nanoTime()
        val request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build()
        client.send(request, HttpResponse.BodyHandlers.discarding())
        val end = System.nanoTime()
        end - start
      }
    }
    val aggregated = Future.sequence(futures)
    Await.result(aggregated, Inf)
    results = aggregated.value.get.get
  }

  def report(): Unit = {
    val total = results.size
    val avg = if (total > 0) results.sum / total else 0L
    println(s"Total requests: $total")
    println(s"Average response time (ns): $avg")
    println(s"Min response time (ns): ${if (results.nonEmpty) results.min else 0L}")
    println(s"Max response time (ns): ${if (results.nonEmpty) results.max else 0L}")
  }
}

@main def run(): Unit = {
  val url = "https://httpbin.org/get"
  val requests = 50
  val concurrency = 10
  val test = new HttpStressTest(url, requests, concurrency)
  test.runTest()
}