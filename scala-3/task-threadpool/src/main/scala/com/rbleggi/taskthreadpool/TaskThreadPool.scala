package com.rbleggi.taskthreadpool

import scala.collection.mutable

trait Task {
  def run(): Unit
}

class TaskThreadPool(workerCount: Int) {
  private val taskQueue = new mutable.Queue[Task]()
  @volatile private var isRunning = true
  private val workers = (1 to workerCount).map(_ => new Worker).toList

  workers.foreach(_.start())

  def submit(task: Task): Unit = this.synchronized {
    if (!isRunning) throw new IllegalStateException("ThreadPool is shutting down")
    taskQueue.enqueue(task)
    this.notifyAll()
  }

  def shutdown(): Unit = this.synchronized {
    isRunning = false
    this.notifyAll()
    workers.foreach(_.join())
  }

  private class Worker extends Thread {
    override def run(): Unit = {
      while (isRunning || taskQueue.nonEmpty) {
        val taskOpt = TaskThreadPool.this.synchronized {
          while (taskQueue.isEmpty && isRunning) TaskThreadPool.this.wait()
          if (taskQueue.nonEmpty) Some(taskQueue.dequeue()) else None
        }
        taskOpt.foreach(_.run())
      }
    }
  }
}

@main def run(): Unit = {
  val pool = new TaskThreadPool(4)
  (1 to 10).foreach { i =>
    pool.submit(new Task {
      def run(): Unit = println(s"Task $i running on thread ${Thread.currentThread().getName}")
    })
  }
  Thread.sleep(1000)
  pool.shutdown()
}
