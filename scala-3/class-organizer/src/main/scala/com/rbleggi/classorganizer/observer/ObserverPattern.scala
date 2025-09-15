package com.rbleggi.classorganizer.observer

import com.rbleggi.classorganizer.model.ClassSession

trait Subject {
  def registerObserver(observer: Observer): Unit

  def removeObserver(observer: Observer): Unit

  def notifyObservers(): Unit
}

trait Observer {
  def update(subject: Subject): Unit
}
