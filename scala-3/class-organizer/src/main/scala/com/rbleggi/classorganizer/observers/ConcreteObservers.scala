package com.rbleggi.classorganizer.observers

import com.rbleggi.classorganizer.observer.{Observer, Subject}
import com.rbleggi.classorganizer.core.ClassManager
import com.rbleggi.classorganizer.model.ClassSession

class ResourceMonitor extends Observer {
  private var availableRooms: Set[String] = Set("A101", "A102", "B201", "B202", "C301")

  override def update(subject: Subject): Unit = {
    subject match {
      case manager: ClassManager =>
        val sessions = manager.getClassSessions
        val occupiedRooms = sessions.map(_.room).toSet
        val currentlyAvailable = availableRooms -- occupiedRooms

        println(s"Resource Monitor Update:")
        println(s"Currently occupied rooms: ${occupiedRooms.mkString(", ")}")
        println(s"Available rooms: ${currentlyAvailable.mkString(", ")}")

        checkResourceConflicts(sessions)
      case _ => println("Unknown subject type")
    }
  }

  private def checkResourceConflicts(sessions: List[ClassSession]): Unit = {
    val roomUsage = sessions.groupBy(_.room)

    roomUsage.foreach { case (room, roomSessions) =>
      if (roomSessions.size > 1) {
        for {
          i <- 0 until roomSessions.size
          j <- i + 1 until roomSessions.size
          session1 = roomSessions(i)
          session2 = roomSessions(j)
          if session1.hasConflict(session2)
        } {
          println(s"CONFLICT DETECTED: Room $room has conflicting sessions:")
          println(s"  - ${session1.subject} (${session1.startTime} to ${session1.endTime})")
          println(s"  - ${session2.subject} (${session2.startTime} to ${session2.endTime})")
        }
      }
    }
  }
}

class ScheduleDisplay extends Observer {
  override def update(subject: Subject): Unit = {
    subject match {
      case manager: ClassManager =>
        val sessions = manager.getClassSessions

        println("\nCurrent Class Schedule:")
        println("======================")

        if (sessions.isEmpty) {
          println("No classes scheduled")
        } else {
          val sortedSessions = sessions.sortBy(_.startTime)

          sortedSessions.foreach { session =>
            println(s"${session.subject} (ID: ${session.id})")
            println(s"  Room: ${session.room}")
            println(s"  Time: ${session.startTime} to ${session.endTime}")
            println(s"  Teacher: ${session.teacherId}")
            println(s"  Students: ${session.students.size}")
            println()
          }
        }
      case _ => println("Unknown subject type")
    }
  }
}

class NotificationService extends Observer {
  override def update(subject: Subject): Unit = {
    subject match {
      case manager: ClassManager =>
        println("\nNotification Service:")
        println("====================")
        println("Changes detected in class schedule.")
        println("Notifications would be sent to affected teachers and students.")
      case _ => println("Unknown subject type")
    }
  }
}
