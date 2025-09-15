package com.rockthejvm.part3fp

object TuplesMapsDemo {

  //tuples
  val aTuple: (Int, String) = (2, "rock the jvm")
  val firstField = aTuple._1
  val secondField = aTuple._2

  // tuple of 2 elements have some syntax sugar
  val aTuple_v2 = 2 -> "rock the jvm" //same as aTuple

  // maps: keys -> values, unique keys
  val aMap = Map()
  val phonebook = Map(
    "jim" -> 555,
    "daniel" -> 921,
    "jane" -> 123
  )

  //core methods
  // key exists in the maps
  val hasDaniel = phonebook contains "daniel"
  val danielNumber = phonebook("Daniel") // 921, but can crash if the key is not in the map

  //add a new pair - returns a new map
  val newPair = "Maey" -> 768
  val newPhonebook = phonebook + newPair //new map

  //remove a key
  val noDaniel = phonebook - "Daniel" // new map

  // lists of tuples <-> maps
  val listOfEntries = List(
    "jim" -> 555,
    "daniel" -> 921,
    "jane" -> 123
  )
  val phonebooke_v2 = listOfEntries.toMap
  val listOfEntries_v2 = phonebook.toList

  //set of keys
  val allNames = phonebook.keySet //["jim","daniel","jane]
  val allNumbers = phonebook.values.toList // [555,921,123] - not necessarily in ths order

  def main(args: Array[String]): Unit = {

  }
}
