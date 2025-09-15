package com.rbleggi.hibernateslowquerydetector

trait QueryObserver {
  def notify(query: String, durationMs: Long): Unit
}

class SlowQueryDetector(thresholdMs: Long) {
  private var observers: List[QueryObserver] = Nil

  def addObserver(observer: QueryObserver): Unit = {
    observers = observer :: observers
  }

  def executeQuery(query: String, simulatedDurationMs: Long): Unit = {
    if (simulatedDurationMs > thresholdMs) {
      observers.foreach(_.notify(query, simulatedDurationMs))
    }
  }
}

class ConsoleLogger extends QueryObserver {
  def notify(query: String, durationMs: Long): Unit = {
    println(s"SLOW QUERY DETECTED: [$query] took $durationMs ms")
  }
}

@main def run(): Unit = {
  val detector = new SlowQueryDetector(thresholdMs = 100)
  detector.addObserver(new ConsoleLogger)

  detector.executeQuery("SELECT * FROM users", 50)
  detector.executeQuery("SELECT * FROM orders", 200)
  detector.executeQuery("UPDATE products SET price = 10", 300)
}
