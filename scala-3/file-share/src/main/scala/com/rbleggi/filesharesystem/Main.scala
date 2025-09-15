package com.rbleggi.filesharesystem

@main def run(): Unit = {
  val fileManager = new FileManager()
  val invoker = new CommandInvoker()

  val file1 = File("example1.txt", "This is the content of file 1.")
  val file2 = File("example2.txt", "This is the content of file 2.")
  val file3 = File("example3.txt", "This is the content of file 3.")

  invoker.executeCommand(SaveFileCommand(fileManager, file1))
  invoker.executeCommand(SaveFileCommand(fileManager, file2))
  invoker.executeCommand(ListFilesCommand(fileManager))
  invoker.executeCommand(SearchFileCommand(fileManager, "example1"))
  invoker.executeCommand(DeleteFileCommand(fileManager, file1))
  invoker.undo()
  invoker.redo()
  invoker.executeCommand(RestoreFileCommand(fileManager, file3))
  invoker.executeCommand(ListFilesCommand(fileManager))
  invoker.undo()
  invoker.executeCommand(ListFilesCommand(fileManager))
}
