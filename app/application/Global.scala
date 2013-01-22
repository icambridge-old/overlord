package application

import play.api._

import akka.actor._
import scala.concurrent.duration._

import play.api._
import play.api.libs.json._
import play.api.libs.iteratee._
import play.api.libs.concurrent._

import akka.util.Timeout
import akka.pattern.ask

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    println("Application has started")


    val serverActor = Akka.system.actorOf(Props[actors.Servers])
    Akka.system.scheduler.schedule(
      0 seconds,
      5 minutes,
      serverActor,
      "run"
    )
  }

  override def onStop(app: Application) {
    println("Application shutdown...")
  }

}