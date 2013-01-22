package actors

import util.Compute
import akka.actor._
import play.api.Play.current
import play.api._
import org.jclouds.compute.domain.internal.NodeMetadataImpl
import org.jclouds.compute.domain.NodeMetadata


import anorm._

class Servers extends Actor {
  def receive = {
    case "run" => {

      val client = Compute.getClient
      play.api.db.DB.withConnection {
        implicit c =>

          // TODO change to partition.
          SQL("TRUNCATE TABLE `cloud_nodes`").execute()

          val servers: Array[NodeMetadataImpl] = client.listNodes().toArray.map(_.asInstanceOf[NodeMetadataImpl])
          servers.map {
            server =>
              val group = server.getName.split("-")(0)
              SQL(
                """INSERT INTO `cloud_nodes`
                (`hostname`, `group`, `cloud_id`, `status`, `public_ips`, `private_ips`, `type`, `location`)
               VALUES
               ({hostname}, {group}, {cloud_id}, {status}, {public_ips}, {private_ips}, {type}, {location})
                """.stripMargin).on("hostname" -> server.getName,
                                    "group" -> group,
                                    "cloud_id" -> server.getId,
                                    "status" -> server.getStatus.toString,
                                    "public_ips" -> server.getPublicAddresses.toString,
                                    "private_ips" ->  server.getPrivateAddresses.toString,
                                    "type" -> server.getHardware.getName,
                                    "location" -> server.getLocation.getParent.getDescription
               ).executeInsert()


              Logger.info("Node stored")
          }
      }
    }
  }
}
