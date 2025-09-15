package com.rbleggi.notetaking

import scala.collection.mutable
import java.io.{BufferedWriter, FileWriter, File}
import scala.io.Source

case class Note(id: Int, title: String, content: String)

class NoteManager:
  private val notes = mutable.Map[Int, Note]()
  private var nextId = 1

  def addNote(title: String, content: String): Note =
    val note = Note(nextId, title, content)
    notes(nextId) = note
    nextId += 1
    note

  def editNote(id: Int, newTitle: Option[String], newContent: Option[String]): Option[Note] =
    notes.get(id).map { note =>
      val updatedNote = note.copy(
        title = newTitle.getOrElse(note.title),
        content = newContent.getOrElse(note.content)
      )
      notes(id) = updatedNote
      updatedNote
    }

  def deleteNote(id: Int): Boolean = notes.remove(id).isDefined

  def listNotes(): List[Note] = notes.values.toList

  def saveNotes(filePath: String): Unit =
    val bw = BufferedWriter(FileWriter(filePath))
    notes.values.foreach { note =>
      bw.write(s"${note.id}|${note.title}|${note.content}\n")
    }
    bw.close()

  def loadNotes(filePath: String): Unit =
    if File(filePath).exists then
      val lines = Source.fromFile(filePath).getLines()
      for line <- lines do
        val Array(id, title, content) = line.split("\\|", 3)
        notes(id.toInt) = Note(id.toInt, title, content)
        nextId = Math.max(nextId, id.toInt + 1)

trait Command:
  def execute(): Unit

class AddNoteCommand(manager: NoteManager, title: String, content: String) extends Command:
  override def execute(): Unit = manager.addNote(title, content)

class EditNoteCommand(manager: NoteManager, id: Int, newTitle: Option[String], newContent: Option[String]) extends Command:
  override def execute(): Unit = manager.editNote(id, newTitle, newContent)

class DeleteNoteCommand(manager: NoteManager, id: Int) extends Command:
  override def execute(): Unit = manager.deleteNote(id)

class SaveNotesCommand(manager: NoteManager, filePath: String) extends Command:
  override def execute(): Unit = manager.saveNotes(filePath)

class LoadNotesCommand(manager: NoteManager, filePath: String) extends Command:
  override def execute(): Unit = manager.loadNotes(filePath)

class CommandManager:
  private val history = mutable.Stack[Command]()

  def executeCommand(command: Command): Unit =
    command.execute()
    history.push(command)

@main def runNoteTakingSystem(): Unit =
  val manager = NoteManager()
  val commandManager = CommandManager()

  commandManager.executeCommand(AddNoteCommand(manager, "First Note", "This is the content of the first note."))
  commandManager.executeCommand(AddNoteCommand(manager, "Second Note", "This is the content of the second note."))
  commandManager.executeCommand(SaveNotesCommand(manager, "notes.txt"))
  println("Notes saved to file.")

  commandManager.executeCommand(LoadNotesCommand(manager, "notes.txt"))
  println("Notes loaded from file:")
  manager.listNotes().foreach(println)

