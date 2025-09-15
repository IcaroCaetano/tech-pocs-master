package com.rbleggi.grocerytodolist

import com.rbleggi.grocerytodolist.CommandInvoker._

@main def run(): Unit = {
  println("\nWelcome to Grocery TODO List!")
  println("--------------------------------")

  val manager = new GroceryManager()

  val milkItem = GroceryItem("Milk")
  val breadItem = GroceryItem("Bread")
  val eggsItem = GroceryItem("Eggs")

  manager.applyChanges(executeCommand(new AddItemCommand(milkItem), manager.getItems))
  manager.applyChanges(executeCommand(new AddItemCommand(breadItem), manager.getItems))
  manager.applyChanges(executeCommand(new AddItemCommand(eggsItem), manager.getItems))

  manager.listAll()
  manager.applyChanges(executeCommand(new MarkAsDoneCommand(milkItem), manager.getItems))
  manager.listAll()
  manager.applyChanges(executeCommand(new RemoveItemCommand(breadItem), manager.getItems))
  manager.listAll()
  manager.applyChanges(undo(manager.getItems))
  manager.listAll()
  manager.applyChanges(undo(manager.getItems))
  manager.listAll()
  manager.applyChanges(redo(manager.getItems))
  manager.listAll()
}
