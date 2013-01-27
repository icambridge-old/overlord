package models

import play.api.Play.current
import play.api._
import play.api.db._

import entity.{Node, Group}

import anorm._

class Groups {

  def save(group: entity.Group) = {

    implicit val connection = DB.getConnection()
    val query = SQL(
      """INSERT INTO `groups` (`name`, `hardware_id`, `image_id`, `loadbalancer_id`)
         VALUES ({name}, {hardware_id}, {image_id}, {loadbalancer_id})""").on(
      "name" -> group.name,
      "hardware_id" -> group.hardwareId,
      "image_id" -> group.imageId,
      "loadbalancer_id" -> group.loadBalancerId
    ).executeInsert()
    connection.close

  }

  def getAll = {

    implicit val connection = DB.getConnection()

    val results = SQL("SELECT * FROM `groups`")

    val groups = results().map {  row =>
      new Group(
        id = Some(row[Int]("id")),
        name = row[String]("name"),
        hardwareId = row[String]("hardware_id"),
        loadBalancerId = row[String]("loadbalancer_id"),
        imageId = row[String]("image_id")
      )

    }

    connection.close
    groups.toList
  }

}