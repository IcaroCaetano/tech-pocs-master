package com.rbleggi.grocerytodolist

trait Command {
  def execute(items: List[GroceryItem]): List[GroceryItem]
  def undo(items: List[GroceryItem]): List[GroceryItem]
}

class AddItemCommand(item: GroceryItem) extends Command {
  override def execute(items: List[GroceryItem]): List[GroceryItem] = {
    if (!items.exists(_.name == item.name)) {
      println(s"Added: ${item.name}")
      items :+ item
    } else {
      println(s"Item already exists: ${item.name}")
      items
    }
  }

  override def undo(items: List[GroceryItem]): List[GroceryItem] = {
    val index = items.indexWhere(_.name == item.name)
    if (index >= 0) {
      println(s"Undone addition: ${item.name}")
      items.patch(index, Nil, 1)
    } else {
      println(s"Item not found for undo: ${item.name}")
      items
    }
  }
}

class RemoveItemCommand(item: GroceryItem) extends Command {
  private var removedItem: Option[GroceryItem] = None

  override def execute(items: List[GroceryItem]): List[GroceryItem] = {
    val index = items.indexWhere(_.name == item.name)
    if (index >= 0) {
      removedItem = Some(items(index))
      println(s"Removed: ${item.name}")
      items.patch(index, Nil, 1)
    } else {
      println(s"Item not found: ${item.name}")
      items
    }
  }

  override def undo(items: List[GroceryItem]): List[GroceryItem] = {
    removedItem match {
      case Some(item) =>
        println(s"Undone removal: ${item.name}")
        items :+ item
      case None =>
        println(s"Cannot undo removal: ${item.name}")
        items
    }
  }
}

class MarkAsDoneCommand(item: GroceryItem) extends Command {
  private var previousState: Option[GroceryItem] = None

  override def execute(items: List[GroceryItem]): List[GroceryItem] = {
    val index = items.indexWhere(_.name == item.name)
    if (index >= 0) {
      previousState = Some(items(index))
      val updatedItem = items(index).markAsDone
      println(s"Marked as done: ${item.name}")
      items.updated(index, updatedItem)
    } else {
      println(s"Item not found: ${item.name}")
      items
    }
  }

  override def undo(items: List[GroceryItem]): List[GroceryItem] = {
    previousState match {
      case Some(prevItem) =>
        val index = items.indexWhere(_.name == item.name)
        if (index >= 0) {
          println(s"Undone mark as done: ${item.name}")
          items.updated(index, prevItem)
        } else {
          println(s"Item not found for undo: ${item.name}")
          items
        }
      case None =>
        println(s"Cannot undo: ${item.name}")
        items
    }
  }
}
