package com.rbleggi.observabilityframework

import scala.collection.mutable.ListBuffer

trait LatencyStrategy:
  def calculateLatency(startTime: Long, endTime: Long): Long

class MillisecondsLatencyStrategy extends LatencyStrategy:
  override def calculateLatency(startTime: Long, endTime: Long): Long =
    (endTime - startTime) / 1_000_000L

class NanosecondsLatencyStrategy extends LatencyStrategy:
  override def calculateLatency(startTime: Long, endTime: Long): Long =
    endTime - startTime

class LatencyTracker(strategy: LatencyStrategy):
  private val latencies = ListBuffer[Long]()

  def recordLatency(startTime: Long, endTime: Long): Unit =
    val latency = strategy.calculateLatency(startTime, endTime)
    latencies += latency

  def getAverageLatency: Double =
    if latencies.isEmpty then 0.0
    else latencies.sum.toDouble / latencies.size

  def getAllLatencies: List[Long] = latencies.toList

object MetricManager:
  private val trackers = scala.collection.mutable.Map[String, LatencyTracker]()

  def getTracker(name: String, strategy: LatencyStrategy): LatencyTracker =
    trackers.getOrElseUpdate(name, new LatencyTracker(strategy))

  def resetTracker(name: String): Unit =
    trackers.remove(name)

  def getAllTrackerNames: List[String] = trackers.keys.toList

@main def run(): Unit =
  println("Starting Observability Core Framework POC...")

  val msStrategy = new MillisecondsLatencyStrategy()
  val requestTrackerMs = MetricManager.getTracker("httpRequestLatencyMs", msStrategy)

  println("\nRecording latencies with Milliseconds Strategy:")
  var i = 1
  while i <= 5 do
    val startTime = System.nanoTime()
    Thread.sleep(100 + i * 10)
    val endTime = System.nanoTime()
    requestTrackerMs.recordLatency(startTime, endTime)
    println(s"  Recorded latency for request $i: ${msStrategy.calculateLatency(startTime, endTime)} ms")
    i += 1

  println(s"Average HTTP Request Latency (ms): ${requestTrackerMs.getAverageLatency}")
  println(s"All HTTP Request Latencies (ms): ${requestTrackerMs.getAllLatencies}")

  val nsStrategy = new NanosecondsLatencyStrategy()
  val dbQueryTrackerNs = MetricManager.getTracker("dbQueryLatencyNs", nsStrategy)

  println("\nRecording latencies with Nanoseconds Strategy:")
  i = 1
  while i <= 3 do
    val startTime = System.nanoTime()
    val dummy = (1 to 1000).map(_ * 2).sum
    val endTime = System.nanoTime()
    dbQueryTrackerNs.recordLatency(startTime, endTime)
    println(s"  Recorded latency for DB query $i: ${nsStrategy.calculateLatency(startTime, endTime)} ns")
    i += 1

  println(s"Average DB Query Latency (ns): ${dbQueryTrackerNs.getAverageLatency}")
  println(s"All DB Query Latencies (ns): ${dbQueryTrackerNs.getAllLatencies}")

  println("\nAll registered trackers:")
  MetricManager.getAllTrackerNames.foreach(name => println(s"- $name"))
