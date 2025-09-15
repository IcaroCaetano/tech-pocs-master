package com.rbleggi.restaurantqueue

trait DishCommand:
  def name: String

  def prepTime(): Int

class BurgerCommand extends DishCommand:
  def name: String = "Burger"

  def prepTime(): Int = 12

class PastaCommand extends DishCommand:
  def name: String = "Pasta"

  def prepTime(): Int = 15

class SaladCommand extends DishCommand:
  def name: String = "Salad"

  def prepTime(): Int = 5

class SushiCommand extends DishCommand:
  def name: String = "Sushi"

  def prepTime(): Int = 20

class KitchenQueue:
  private var commands: List[DishCommand] = List()

  def addCommand(cmd: DishCommand): Unit =
    commands = commands :+ cmd

  def runQueue(): Unit =
    println("\nkitchen queue\n")
    var totalTime = 0
    commands.foreach { cmd =>
      val time = cmd.prepTime()
      println(s"Preparing ${cmd.name} takes $time minutes.")
      totalTime += time
    }
    println(s"\nAll dishes prepared in $totalTime minutes!")

@main def runRestaurantQueue(): Unit =
  val queue = KitchenQueue()

  queue.addCommand(BurgerCommand())
  queue.addCommand(PastaCommand())
  queue.addCommand(SaladCommand())
  queue.addCommand(SushiCommand())

  queue.runQueue()
