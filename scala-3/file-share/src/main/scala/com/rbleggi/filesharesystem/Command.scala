package com.rbleggi.filesharesystem

trait Command {
  def execute(): Unit
  def undo(): Unit
}

case class SaveFileCommand(fileManager: FileManager, file: File) extends Command {
  override def execute(): Unit = fileManager.saveFile(file)
  override def undo(): Unit = fileManager.deleteFile(file)
}

case class RestoreFileCommand(fileManager: FileManager, file: File) extends Command {
  override def execute(): Unit = fileManager.restoreFile(file)
  override def undo(): Unit = fileManager.deleteFile(file)
}

case class DeleteFileCommand(fileManager: FileManager, file: File) extends Command {
  override def execute(): Unit = fileManager.deleteFile(file)
  override def undo(): Unit = fileManager.saveFile(file)
}

case class ListFilesCommand(fileManager: FileManager) extends Command {
  override def execute(): Unit = fileManager.listFiles()
  override def undo(): Unit = println("Undo not supported for listing files.")
}

case class SearchFileCommand(fileManager: FileManager, query: String) extends Command {
  override def execute(): Unit = fileManager.searchFile(query)
  override def undo(): Unit = println("Undo not supported for searching files.")
}
