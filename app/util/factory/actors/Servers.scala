package util.factory.actors

import play.api._

import akka.actor._
import libs.concurrent._

import play.api.Play.current
object Servers {

  lazy val actor = Akka.system.actorOf(Props[actors.Servers])

}