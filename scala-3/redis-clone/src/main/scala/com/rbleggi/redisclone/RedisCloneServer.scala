package com.rbleggi.redisclone

import scala.collection.mutable

trait Command {
  def execute(store: RedisStore): String
}

class RedisStore {
  val strings: mutable.Map[String, String] = mutable.Map.empty
  val maps: mutable.Map[String, mutable.Map[String, String]] = mutable.Map.empty
}

case class SetCommand(key: String, value: String) extends Command {
  def execute(store: RedisStore): String = {
    store.strings(key) = value
    "+OK"
  }
}

case class GetCommand(key: String) extends Command {
  def execute(store: RedisStore): String =
    store.strings.getOrElse(key, "(nil)")
}

case class RemoveCommand(key: String) extends Command {
  def execute(store: RedisStore): String = {
    if (store.strings.remove(key).isDefined) "+OK" else "(nil)"
  }
}

case class AppendCommand(key: String, value: String) extends Command {
  def execute(store: RedisStore): String = {
    val newValue = store.strings.getOrElse(key, "") + value
    store.strings(key) = newValue
    newValue
  }
}

case class MapSetCommand(mapKey: String, field: String, value: String) extends Command {
  def execute(store: RedisStore): String = {
    val map = store.maps.getOrElseUpdate(mapKey, mutable.Map.empty)
    map(field) = value
    "+OK"
  }
}

case class MapGetCommand(mapKey: String, field: String) extends Command {
  def execute(store: RedisStore): String =
    store.maps.get(mapKey).flatMap(_.get(field)).getOrElse("(nil)")
}

case class MapKeysCommand(mapKey: String) extends Command {
  def execute(store: RedisStore): String =
    store.maps.get(mapKey).map(_.keys.mkString(",")).getOrElse("(nil)")
}

case class MapValuesCommand(mapKey: String) extends Command {
  def execute(store: RedisStore): String =
    store.maps.get(mapKey).map(_.values.mkString(",")).getOrElse("(nil)")
}

@main def redisCloneServer(): Unit = {
  val store = new RedisStore
  println("Scala Redis Clone PoC. Type commands, Ctrl+C to exit.")
  Iterator.continually(scala.io.StdIn.readLine("> ")).takeWhile(_ != null).foreach { line =>
    val cmd = parseCommand(line)
    val result = cmd.map(_.execute(store)).getOrElse("ERR unknown command")
    println(result)
  }
}

@main def redisCloneServerExample(): Unit = {
  val store = new RedisStore
  val commands = List(
    "set foo bar",
    "get foo",
    "append foo baz",
    "remove foo",
    "get foo",
    "mapset mymap field1 value1",
    "mapget mymap field1",
    "mapkeys mymap",
    "mapvalues mymap"
  )
  commands.foreach { line =>
    println(line)
    val cmd = parseCommand(line)
    val result = cmd.map(_.execute(store)).getOrElse("ERR unknown command")
    println(result)
  }
}

def parseCommand(input: String): Option[Command] = {
  val parts = input.trim.split(" ").toList
  parts match {
    case "set" :: k :: v :: Nil => Some(SetCommand(k, v))
    case "get" :: k :: Nil => Some(GetCommand(k))
    case "remove" :: k :: Nil => Some(RemoveCommand(k))
    case "append" :: k :: v :: Nil => Some(AppendCommand(k, v))
    case "mapset" :: m :: f :: v :: Nil => Some(MapSetCommand(m, f, v))
    case "mapget" :: m :: f :: Nil => Some(MapGetCommand(m, f))
    case "mapkeys" :: m :: Nil => Some(MapKeysCommand(m))
    case "mapvalues" :: m :: Nil => Some(MapValuesCommand(m))
    case _ => None
  }
}