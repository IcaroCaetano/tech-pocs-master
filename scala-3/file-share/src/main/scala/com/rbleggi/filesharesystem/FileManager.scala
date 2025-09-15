package com.rbleggi.filesharesystem

import scala.collection.mutable

class FileManager {
  private val files = mutable.Map[String, File]()

  def saveFile(file: File): Unit = {
    files(file.name) = file
    println(s"File '${file.name}' saved.")
  }

  def restoreFile(file: File): Unit = {
    if (files.contains(file.name)) {
      println(s"File '${file.name}' restored.")
    } else {
      println(s"File '${file.name}' does not exist.")
    }
  }

  def deleteFile(file: File): Unit = {
    if (files.remove(file.name).isDefined) {
      println(s"File '${file.name}' deleted.")
    } else {
      println(s"File '${file.name}' does not exist.")
    }
  }

  def listFiles(): Unit = {
    if (files.isEmpty) {
      println("\nNo files available.")
    } else {
      println("\nFiles:")
      files.keys.foreach(println)
    }
  }

  def searchFile(query: String): Unit = {
    val results = files.keys.filter(_.contains(query))
    if (results.isEmpty) {
      println(s"\nNo files found matching '$query'.")
    } else {
      println(s"\nFiles matching '$query':")
      results.foreach(println)
    }
  }
}
