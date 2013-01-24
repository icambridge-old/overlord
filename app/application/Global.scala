package application

import play.api._

import akka.actor._
import scala.concurrent.duration._

import play.api._
import play.api.Play.current
import play.api.libs.concurrent._

import akka.util.Timeout
import akka.pattern.ask

import play.api.libs.concurrent.Execution.Implicits._
import util.factory.actors.Servers

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    println("Application has started")



    Akka.system.scheduler.schedule(
      0 seconds,
      5 minutes,
      Servers.actor,
      "run"
    )
  }

  override def onStop(app: Application) {
    println("Application shutdown...")
  }

}