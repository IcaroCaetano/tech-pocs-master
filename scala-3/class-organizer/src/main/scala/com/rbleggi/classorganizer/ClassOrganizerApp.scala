package com.rbleggi.classorganizer

import java.time.LocalDateTime
import com.rbleggi.classorganizer.core.ClassManager
import com.rbleggi.classorganizer.model.{ClassSession, Student}
import com.rbleggi.classorganizer.observers.{NotificationService, ResourceMonitor, ScheduleDisplay}

@main def run(): Unit = {
  println("Teacher's Class Organizer/Optimizer")
  println("==================================")

  val students = List(
    Student("S001", "Alice Smith", "10A"),
    Student("S002", "Bob Johnson", "10A"),
    Student("S003", "Charlie Brown", "10A"),
    Student("S004", "Diana Prince", "10A"),
    Student("S005", "Edward Jones", "10A")
  )

  val classManager = new ClassManager()

  val resourceMonitor = new ResourceMonitor()
  val scheduleDisplay = new ScheduleDisplay()
  val notificationService = new NotificationService()

  classManager.registerObserver(resourceMonitor)
  classManager.registerObserver(scheduleDisplay)
  classManager.registerObserver(notificationService)

  val mathClass = ClassSession(
    id = "MATH101",
    teacherId = "T123",
    subject = "Mathematics",
    room = "A101",
    startTime = LocalDateTime.of(2025, 6, 1, 9, 0),
    endTime = LocalDateTime.of(2025, 6, 1, 10, 30),
    students = students
  )

  val physicsClass = ClassSession(
    id = "PHYS101",
    teacherId = "T456",
    subject = "Physics",
    room = "B201",
    startTime = LocalDateTime.of(2025, 6, 1, 11, 0),
    endTime = LocalDateTime.of(2025, 6, 1, 12, 30),
    students = students.take(3)
  )

  val chemistryClass = ClassSession(
    id = "CHEM101",
    teacherId = "T789",
    subject = "Chemistry",
    room = "C301",
    startTime = LocalDateTime.of(2025, 6, 1, 13, 0),
    endTime = LocalDateTime.of(2025, 6, 1, 14, 30),
    students = students.drop(2)
  )

  println("\nAdding initial class sessions...")
  classManager.addClassSession(mathClass)
  classManager.addClassSession(physicsClass)
  classManager.addClassSession(chemistryClass)

  println("\nAttempting to add a conflicting class session...")
  val conflictingClass = ClassSession(
    id = "ENG101",
    teacherId = "T999",
    subject = "English",
    room = "A101", 
    startTime = LocalDateTime.of(2025, 6, 1, 10, 0), 
    endTime = LocalDateTime.of(2025, 6, 1, 11, 30),
    students = students.take(4)
  )

  val result = classManager.addClassSession(conflictingClass)
  println(s"Added conflicting class? $result")

  println("\nUpdating Physics class to a new room...")
  val updatedPhysics = physicsClass.copy(room = "A102")
  classManager.updateClassSession("PHYS101", updatedPhysics)

  println("\nRunning schedule optimization...")
  classManager.optimizeSchedule()

  println("\nRemoving Chemistry class...")
  classManager.removeClassSession("CHEM101")
}
