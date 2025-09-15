package com.rbleggi.grocerytodolist

case class GroceryItem(name: String, isDone: Boolean = false) {
  def markAsDone: GroceryItem = this.copy(isDone = true)

  def markAsUndone: GroceryItem = this.copy(isDone = false)

  override def toString: String = s"${if (isDone) "[X]" else "[ ]"} $name"
}
