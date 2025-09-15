package com.rbleggi.classorganizer.core

import com.rbleggi.classorganizer.model.ClassSession
import com.rbleggi.classorganizer.observer.{Observer, Subject}
import scala.collection.mutable.{ListBuffer, Map}

class ClassManager extends Subject {
  private val observers: ListBuffer[Observer] = ListBuffer()
  private val classes: Map[String, ClassSession] = Map()

  override def registerObserver(observer: Observer): Unit = {
    observers += observer
  }

  override def removeObserver(observer: Observer): Unit = {
    observers -= observer
  }

  override def notifyObservers(): Unit = {
    observers.foreach(_.update(this))
  }

  def addClassSession(session: ClassSession): Boolean = {
    val hasConflict = classes.values.exists(existingSession =>
      existingSession.hasConflict(session)
    )

    if (hasConflict) {
      false
    } else {
      classes += (session.id -> session)
      notifyObservers()
      true
    }
  }

  def updateClassSession(id: String, updatedSession: ClassSession): Boolean = {
    if (!classes.contains(id)) {
      return false
    }

    val currentSession = classes(id)
    classes -= id

    val hasConflict = classes.values.exists(existingSession =>
      existingSession.hasConflict(updatedSession)
    )

    if (hasConflict) {
      classes += (id -> currentSession)
      false
    } else {
      classes += (updatedSession.id -> updatedSession)
      notifyObservers()
      true
    }
  }

  def removeClassSession(id: String): Boolean = {
    if (classes.contains(id)) {
      classes -= id
      notifyObservers()
      true
    } else {
      false
    }
  }

  def getClassSessions: List[ClassSession] = {
    classes.values.toList
  }

  def getClassSession(id: String): Option[ClassSession] = {
    classes.get(id)
  }

  def optimizeSchedule(): Unit = {
    val sortedClasses = classes.values.toList.sortBy(-_.duration)
    classes.clear()

    sortedClasses.foreach { session =>
      addClassSession(session)
    }

    notifyObservers()
  }
}
