package com.rbleggi.grocerytodolist

import scala.collection.mutable

object CommandInvoker {
  private val history = mutable.Stack[Command]()
  private val redoStack = mutable.Stack[Command]()

  def executeCommand(command: Command, items: List[GroceryItem]): List[GroceryItem] = {
    val result = command.execute(items)
    history.push(command)
    redoStack.clear()
    result
  }

  def undo(items: List[GroceryItem]): List[GroceryItem] = {
    if (history.nonEmpty) {
      val command = history.pop()
      val result = command.undo(items)
      redoStack.push(command)
      println("Undo successful")
      result
    } else {
      println("Nothing to undo")
      items
    }
  }

  def redo(items: List[GroceryItem]): List[GroceryItem] = {
    if (redoStack.nonEmpty) {
      val command = redoStack.pop()
      val result = command.execute(items)
      history.push(command)
      println("Redo successful")
      result
    } else {
      println("Nothing to redo")
      items
    }
  }
}
