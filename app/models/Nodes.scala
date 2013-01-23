package models

import play.api.Play.current
import play.api._
import play.api.db._
import org.jclouds.compute.domain.internal.NodeMetadataImpl
import org.jclouds.compute.domain.NodeMetadata

import anorm._

object Nodes {

  def insert(node: NodeMetadataImpl) = {

    DB.withConnection { implicit c =>
      val group = node.getName.split("-")(0)
      SQL(
        """INSERT INTO `cloud_nodes`
                  (`hostname`, `group`, `cloud_id`, `status`, `public_ips`, `private_ips`, `type`, `location`)
                 VALUES
                 ({hostname}, {group}, {cloud_id}, {status}, {public_ips}, {private_ips}, {type}, {location})
        """.stripMargin).on("hostname" -> node.getName,
                            "group" -> group,
                            "cloud_id" -> node.getId,
                            "status" -> node.getStatus.toString,
                            "public_ips" -> node.getPublicAddresses.toString,
                            "private_ips" ->  node.getPrivateAddresses.toString,
                            "type" -> node.getHardware.getName,
                            "location" -> node.getLocation.getParent.getDescription
                          ).executeInsert()
      }

    }


  def truncate = {

    DB.withConnection { implicit c =>
    // TODO change to partition.
      SQL("TRUNCATE TABLE `cloud_nodes`").execute()
    }
  }
}