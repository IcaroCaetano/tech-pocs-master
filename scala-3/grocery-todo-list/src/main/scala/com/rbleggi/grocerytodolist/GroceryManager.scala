package com.rbleggi.grocerytodolist

class GroceryManager {
  private var items: List[GroceryItem] = List.empty

  def getItems: List[GroceryItem] = items

  def applyChanges(newItems: List[GroceryItem]): Unit = {
    items = newItems
  }

  def listAll(): Unit = {
    println("\nGrocery List:")
    if (items.isEmpty) {
      println("No items in the list.")
    } else {
      items.foreach(item => println(item))
    }
  }
}
