package actors

import util.Compute
import models.Nodes

import akka.actor._
import play.api.Play.current
import play.api._
import db.DB
import org.jclouds.compute.domain.internal.NodeMetadataImpl
import org.jclouds.compute.domain.NodeMetadata


import anorm._

class Servers extends Actor {
  def receive = {
    case "run" => {

      val client = Compute.getClient

      Nodes.truncate
      Logger.info("Node table successfully truncated")

      val nodes: Array[NodeMetadataImpl] = client.listNodes().toArray.map(_.asInstanceOf[NodeMetadataImpl])
      nodes.map { node =>
         val nodeName = node.getName
         val date = new java.util.Date
         Nodes.insert(node)
          Logger.info("Node " + nodeName + " successfully inserted into database")
      }

    }
  }
}
