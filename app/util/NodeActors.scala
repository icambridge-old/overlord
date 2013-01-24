package utils

import play.api._

import akka.actor._
import libs.concurrent._

import play.api.Play.current


object NodeActors {

  lazy val list = Akka.system.actorOf(Props[actors.ListNodes])

  lazy val create = Akka.system.actorOf(Props[actors.CreateNode])

}