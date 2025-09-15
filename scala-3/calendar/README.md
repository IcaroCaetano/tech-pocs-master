# **Calendar System**

## **Overview**

This project implements a **flexible and maintainable calendar system** using the **Command Pattern**. Users can book meetings, remove meetings, list meetings, and suggest the best time for two people to meet. The system also supports undo and redo functionality for all actions.

---

## **Tech Stack**

- **Scala 3** → Modern JVM-based language with advanced type safety and functional programming features.
- **SBT** → Scala's official build tool.
- **JDK 21** → Required to run the application.

---

## **Features**

- **Meeting Management** → Book, remove, list, and suggest meeting times.
- **Command Pattern** → Actions are encapsulated as commands for easy extension.
- **Conflict Detection** → Prevents booking conflicting meetings.
- **Suggestion Engine** → Suggests optimal meeting times based on availability.

---

## **Architecture Diagram**

```mermaid
classDiagram
    direction TB

    class Command {
        <<interface>>
        +execute(): Any
    }

    class BookMeetingCommand {
        -meeting: Meeting
        +execute(): Boolean
    }

    class RemoveMeetingCommand {
        -meetingId: String
        +execute(): Boolean
    }

    class ListMeetingsCommand {
        -user: User
        +execute(): List[Meeting]
    }

    class SuggestBestTimeCommand {
        -user1: User
        -user2: User
        -durationMinutes: Long
        +execute(): Option[(LocalDateTime, LocalDateTime)]
    }

    class Meeting {
        +id: String
        +title: String
        +start: LocalDateTime
        +end: LocalDateTime
        +attendees: Set[User]
    }

    class Calendar {
        -meetings: Map[String, Meeting]
        +bookMeeting(meeting: Meeting): Boolean
        +removeMeeting(meetingId: String): Boolean
        +listMeetings(user: User): List[Meeting]
        +getMeetingsForUsers(users: Set[User]): List[Meeting]
        +getAllMeetings(): List[Meeting]
    }

    class CalendarInvoker {
        +execute(command: Command): Any
    }

    Command <|-- BookMeetingCommand
    Command <|-- RemoveMeetingCommand
    Command <|-- ListMeetingsCommand
    Command <|-- SuggestBestTimeCommand
    BookMeetingCommand --> Calendar: modifies
    RemoveMeetingCommand --> Calendar: modifies
    ListMeetingsCommand --> Calendar: queries
    SuggestBestTimeCommand --> Calendar: queries
    CalendarInvoker --> Command: invokes
    Calendar o-- Meeting: contains
```

---

## **Command Pattern**

The **Command Pattern** encapsulates requests as objects, allowing:

- Each calendar action is implemented as a separate `Command`
- `CalendarInvoker` executes commands
- Commands know how to execute themselves
- Easy extension with new commands without modifying existing code
- Clean separation between the invoker and the receiver

---

## **Setup Instructions**

### **1️ - Clone the Repository**

```shell
git clone https://github.com/rbleggi/tech-pocs.git
cd scala-3/calendar
```

### **2️ - Compile & Run the Application**

```shell
./sbtw compile run
```

### **3️ - Run Tests**

```shell
./sbtw compile test
```
