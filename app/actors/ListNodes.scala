package actors

import utils.Compute
import models.Nodes

import akka.actor._
import play.api.Play.current
import play.api._
import db.DB
import org.jclouds.compute.domain.internal.NodeMetadataImpl
import org.jclouds.compute.domain.NodeMetadata


import anorm._

class ListNodes extends Actor {
  def receive = {
    case "run" => {

      val client = Compute.getClient
      val nodes: Array[NodeMetadataImpl] = client.listNodes().toArray.map(_.asInstanceOf[NodeMetadataImpl])
      Nodes.truncate
      Logger.info("Node table successfully truncated")

      nodes.map { node =>
         val nodeName = node.getName
         val date = new java.util.Date
         Nodes.insert(node)
          Logger.info("Node " + nodeName + " successfully inserted into database")
      }

    }
  }
}
