package com.rbleggi.murdermisterygame

import scala.io.StdIn.readLine

trait Command {
  def execute(game: Game): Game
}

case class Game(suspects: List[String], clues: List[String], foundClues: List[String], accused: Option[String], finished: Boolean)

object Commands {
  case object ShowSuspects extends Command {
    def execute(game: Game): Game = {
      println("Suspects:")
      game.suspects.foreach(println)
      game
    }
  }
  case object ShowClues extends Command {
    def execute(game: Game): Game = {
      println("Clues:")
      game.clues.foreach(println)
      game
    }
  }
  case class FindClue(clue: String) extends Command {
    def execute(game: Game): Game = {
      if (game.clues.contains(clue) && !game.foundClues.contains(clue)) {
        println(s"You found the clue: $clue")
        game.copy(foundClues = clue :: game.foundClues)
      } else {
        println("Clue not found or already discovered.")
        game
      }
    }
  }
  case class Accuse(suspect: String) extends Command {
    def execute(game: Game): Game = {
      if (game.suspects.contains(suspect)) {
        println(s"You accused $suspect!")
        val solved = suspect == "Ms. Scarlet"
        if (solved) println("Correct! Ms. Scarlet is the murderer.") else println("Wrong suspect!")
        game.copy(accused = Some(suspect), finished = true)
      } else {
        println("Suspect not found.")
        game
      }
    }
  }
  case object Help extends Command {
    def execute(game: Game): Game = {
      println("Commands: suspects, clues, find <clue>, accuse <suspect>, help, exit")
      game
    }
  }
  case object Exit extends Command {
    def execute(game: Game): Game = {
      println("Exiting game.")
      game.copy(finished = true)
    }
  }
}

object Parser {
  def parse(input: String): Command = {
    input.trim match {
      case "suspects" => Commands.ShowSuspects
      case "clues" => Commands.ShowClues
      case s if s.startsWith("find ") => Commands.FindClue(s.stripPrefix("find "))
      case s if s.startsWith("accuse ") => Commands.Accuse(s.stripPrefix("accuse "))
      case "help" => Commands.Help
      case "exit" => Commands.Exit
      case _ => Commands.Help
    }
  }
}

@main def run(): Unit = {
  val suspects = List("Ms. Scarlet", "Col. Mustard", "Prof. Plum", "Mrs. Peacock")
  val clues = List("red scarf", "mustard stain", "plum pie", "peacock feather")
  var game = Game(suspects, clues, Nil, None, false)
  println("Welcome to the Murder Mystery Game!")
  Commands.Help.execute(game)
  while (!game.finished) {
    val input = readLine("> ")
    val cmd = Parser.parse(input)
    game = cmd.execute(game)
  }
}

