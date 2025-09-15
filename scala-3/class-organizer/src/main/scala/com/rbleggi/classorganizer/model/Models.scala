package com.rbleggi.classorganizer.model

import java.time.LocalDateTime

case class Student(
                    id: String,
                    name: String,
                    grade: String
                  )

case class ClassSession(
                         id: String,
                         teacherId: String,
                         subject: String,
                         room: String,
                         startTime: LocalDateTime,
                         endTime: LocalDateTime,
                         students: List[Student]
                       ) {
  def hasConflict(other: ClassSession): Boolean = {
    room == other.room &&
      ((startTime.isAfter(other.startTime) && startTime.isBefore(other.endTime)) ||
        (endTime.isAfter(other.startTime) && endTime.isBefore(other.endTime)) ||
        (startTime.isBefore(other.startTime) && endTime.isAfter(other.endTime)) ||
        (startTime.isEqual(other.startTime) || endTime.isEqual(other.endTime)))
  }

  def duration: Long = {
    java.time.Duration.between(startTime, endTime).toMinutes
  }
}
