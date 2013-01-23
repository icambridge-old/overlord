package models

import play.api.Play.current
import play.api._
import play.api.db._
import org.jclouds.compute.domain.internal.NodeMetadataImpl
import org.jclouds.compute.domain.NodeMetadata

import entity.Node

import anorm._

object Nodes {

  def insert(node: NodeMetadataImpl) = {

    DB.withConnection { implicit c =>
      val group = node.getName.split("-")(0)
      SQL(
        """INSERT INTO `cloud_nodes`
                  (`hostname`, `group`, `id`, `status`, `public_ips`, `private_ips`, `hardware`, `location`)
                 VALUES
                 ({hostname}, {group}, {id}, {status}, {public_ips}, {private_ips}, {hardware}, {location})
        """.stripMargin).on("hostname" -> node.getName,
                            "group" -> group,
                            "id" -> node.getId,
                            "status" -> node.getStatus.toString,
                            "public_ips" -> node.getPublicAddresses.toString,
                            "private_ips" ->  node.getPrivateAddresses.toString,
                            "hardware" -> node.getHardware.getName,
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

  def getAll: List[Node] = {
    implicit val connection = DB.getConnection()

    val results = SQL("SELECT * FROM `cloud_nodes`")

    val nodes = results().map {  row =>
      new Node(row[String]("id"),
               row[String]("hostname"),
               row[String]("status"),
               row[String]("private_ips"),
               row[String]("public_ips"),
               row[String]("location"),
               row[String]("hardware")
      )


    }

    connection.close
    nodes.toList

  }


  def getOne(id: String) = {
    implicit val connection = DB.getConnection()

    val query = SQL("SELECT * FROM `cloud_nodes` WHERE `id` = {id}").on("id" -> id)
    val results = query.apply

    if (results.length == 0) {
      throw new RuntimeException("No such cloud node found for id " + id)
    }

    val row = results(0)
    val node = new Node(row[String]("id"),
        row[String]("hostname"),
        row[String]("status"),
        row[String]("private_ips"),
        row[String]("public_ips"),
        row[String]("location"),
        row[String]("hardware")
      )

    connection.close
    node
  }

}