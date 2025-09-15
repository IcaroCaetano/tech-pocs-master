# **Murder Mystery Game**

## **Overview**

This project implements a simple terminal-based Murder Mystery Game in Scala. The player interacts via commands to find clues and accuse suspects. The solution uses the Command Pattern to handle user actions and game logic.

---

## **Tech Stack**

- **Scala 3** → Modern JVM-based language with advanced type safety and functional programming features.
- **SBT** → Scala's official build tool.
- **JDK 21** → Required to run the application.

---

## **Features**

- **Terminal Interaction** → Play the game entirely in the terminal.
- **Command Pattern** → Each user action is encapsulated as a command.
- **Simple Game Logic** → Find clues and accuse suspects to solve the mystery.
- **Single File Implementation** → All logic is contained in one file for simplicity.

---

## **Architecture Diagram**

```mermaid
classDiagram
    direction TB

    class Command {
        <<trait>>
        +execute(game: Game): Game
    }

    class Game {
        -suspects: List[String]
        -clues: List[String]
        -foundClues: List[String]
        -accused: Option[String]
        -finished: Boolean
    }

    class Parser {
        +parse(input: String): Command
    }

    Command <|.. Parser
    Game --> Command
```

---

## **Implementation Details**

- The game uses the Command Pattern: each action (show suspects, find clue, accuse, etc.) is a Command object.
- The main entry point is `@main def run(): Unit =`, which starts the game loop and processes user input.
- All logic is in a single Scala file, with no comments.
- To play, run the application and use commands like `suspects`, `clues`, `find <clue>`, `accuse <suspect>`, `help`, and `exit`.

---

## **Setup Instructions**

### **1️ - Clone the Repository**

```bash
git clone https://github.com/rbleggi/tech-pocs.git
cd scala-3/murder-mistery-game
```

### **2️ - Compile & Run the Application**

```bash
./sbtw compile run
```

### **3️ - Run Tests**

```bash
./sbtw test
```
