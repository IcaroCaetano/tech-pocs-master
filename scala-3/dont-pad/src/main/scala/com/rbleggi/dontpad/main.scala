package com.rbleggi.dontpad

import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets

trait Command {
  def execute(): Unit
}

class NotePad(val key: String) {
  private var notes: List[String] = loadNotes()

  private def filePath: String = s"notes_$key.txt"

  private def loadNotes(): List[String] = {
    val path = Paths.get(filePath)
    if (Files.exists(path)) {
      Files.readAllLines(path, StandardCharsets.UTF_8).toArray(new Array[String](0)).toList
    } else Nil
  }

  private def saveNotes(): Unit = {
    Files.write(Paths.get(filePath), notes.mkString("\n").getBytes(StandardCharsets.UTF_8))
  }

  def getAllText: String = notes.mkString("\n")
  def setAllText(text: String): Unit = {
    notes = if (text.isEmpty) Nil else text.split("\n").toList
    saveNotes()
  }
}

class LoadNoteCommand(pad: NotePad) extends Command {
  def execute(): Unit = {
    println("\nCurrent note:")
    println("--------------------")
    println(pad.getAllText)
    println("--------------------")
  }
}

class AppendNoteCommand(pad: NotePad, newText: String) extends Command {
  def execute(): Unit = {
    val updatedText = if (pad.getAllText.isEmpty) newText else pad.getAllText + "\n" + newText
    pad.setAllText(updatedText)
    println("Note updated and saved.")
  }
}

class NoOpCommand extends Command {
  def execute(): Unit = println("Note unchanged.")
}

@main def run(): Unit = {
  println("Welcome to Local DontPad!")
  print("Enter the dontpad URL (e.g. /mypage): ")
  val url = scala.io.StdIn.readLine().trim
  val key = if (url.startsWith("/")) url.drop(1) else url
  val pad = new NotePad(key)

  new LoadNoteCommand(pad).execute()

  println("\nType your new note below. The current note will be loaded above. Your input will be appended after the existing text. For multi-line input, finish with Ctrl+D then Enter.")
  val newText = scala.io.Source.stdin.getLines().mkString("\n")
  if (newText.nonEmpty) {
    new AppendNoteCommand(pad, newText).execute()
  } else {
    new NoOpCommand().execute()
  }
}