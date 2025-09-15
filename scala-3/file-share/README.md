# **FileShare System**

## **Overview**

This project implements a **flexible and maintainable file-sharing system** using the **Command Pattern**. Users can save files, restore files, delete files, list all files, and search for specific files. The system also supports undo and redo functionality for all actions.

---

## **Tech Stack**

- **Scala 3** → Modern JVM-based language with advanced type safety and functional programming features.
- **SBT** → Scala's official build tool.
- **JDK 21** → Required to run the application.

---

## **Features**

- **File Management** → Save, restore, delete, list, and search files
- **Action History** → Undo and redo functionality
- **Command Pattern** → Actions are encapsulated as commands for easy extension
- **Encryption** → Files are encrypted for secure storage
- **Immutable Data** → Leverages Scala's immutability for safer code

---

## **Architecture Diagram**

```mermaid
classDiagram
    direction TB

    class Command {
        <<interface>>
        +execute(): Unit
        +undo(): Unit
    }

    class SaveFileCommand {
        -file: File
        +execute(): Unit
        +undo(): Unit
    }

    class RestoreFileCommand {
        -file: File
        +execute(): Unit
        +undo(): Unit
    }

    class DeleteFileCommand {
        -file: File
        +execute(): Unit
        +undo(): Unit
    }

    class ListFilesCommand {
        +execute(): Unit
        +undo(): Unit
    }

    class SearchFileCommand {
        -query: String
        +execute(): Unit
        +undo(): Unit
    }

    class File {
        +name: String
        +content: String
        +isEncrypted: Boolean
    }

    class FileManager {
        -files: List[File]
        +saveFile(file: File): Unit
        +restoreFile(file: File): Unit
        +deleteFile(file: File): Unit
        +listFiles(): Unit
        +searchFile(query: String): Unit
    }

    class CommandInvoker {
        -history: Stack[Command]
        -redoStack: Stack[Command]
        +executeCommand(command: Command): Unit
        +undo(): Unit
        +redo(): Unit
    }

    Command <|-- SaveFileCommand
    Command <|-- RestoreFileCommand
    Command <|-- DeleteFileCommand
    Command <|-- ListFilesCommand
    Command <|-- SearchFileCommand
    SaveFileCommand --> FileManager: modifies
    RestoreFileCommand --> FileManager: modifies
    DeleteFileCommand --> FileManager: modifies
    ListFilesCommand --> FileManager: queries
    SearchFileCommand --> FileManager: queries
    CommandInvoker --> Command: invokes
    FileManager o-- File: contains
```

---

## **Command Pattern**

The **Command Pattern** encapsulates requests as objects, allowing:

- Each file management action is implemented as a separate `Command`
- `CommandInvoker` maintains history for undo/redo functionality
- Commands know how to execute and undo themselves
- Easy extension with new commands without modifying existing code
- Clean separation between the invoker and the receiver

---

## **Setup Instructions**

### **1️ - Clone the Repository**

```shell
git clone https://github.com/rbleggi/tech-pocs.git
cd scala-3/file-share-system
```

### **2️ - Compile & Run the Application**

```shell
./sbtw compile run
```

### **3️ - Run Tests**

```shell
./sbtw compile test
```
