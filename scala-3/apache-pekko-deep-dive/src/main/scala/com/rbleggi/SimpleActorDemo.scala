package com.rbleggi

import org.apache.pekko.actor.typed._
import org.apache.pekko.actor.typed.scaladsl._

object GreeterDemo {
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])

  final case class Greeted(whom: String)

  def greeter: Behavior[Greet] = Behaviors.receiveMessage[Greet] {
    msg =>
      println(s"Hello ${msg.whom}!")
      msg.replyTo ! Greeted(msg.whom)
      Behaviors.same
  }

  def replier: Behavior[Greeted] = Behaviors.receiveMessage[Greeted] {
    msg =>
      println(s"Received greeting from ${msg.whom}!")
      Behaviors.same
  }

  def main(args: Array[String]): Unit = {
    val system = ActorSystem(Behaviors.setup[Any] { ctx =>
      val replierActor = ctx.spawn(replier, "replier")
      val greeterActor = ctx.spawn(greeter, "greeter")
      greeterActor ! Greet("Roger", replierActor)
      Behaviors.empty
    }, "HelloSystem")
  }
}
