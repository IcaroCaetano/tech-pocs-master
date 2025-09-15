package com.rbleggi.filesharesystem

import scala.collection.mutable

class CommandInvoker {
  private val history = mutable.Stack[Command]()
  private val redoStack = mutable.Stack[Command]()

  def executeCommand(command: Command): Unit = {
    command.execute()
    history.push(command)
    redoStack.clear()
  }

  def undo(): Unit = {
    if (history.nonEmpty) {
      val command = history.pop()
      command.undo()
      redoStack.push(command)
    } else {
      println("No actions to undo.")
    }
  }

  def redo(): Unit = {
    if (redoStack.nonEmpty) {
      val command = redoStack.pop()
      command.execute()
      history.push(command)
    } else {
      println("No actions to redo.")
    }
  }
}
