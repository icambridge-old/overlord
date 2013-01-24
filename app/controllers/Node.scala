package controllers

import _root_.util.factory.actors.Servers
import play.libs.Akka._

import play.api._
import play.api.libs.concurrent._
import play.api.mvc._

import org.jclouds.compute.domain.internal.NodeMetadataImpl
import org.jclouds.compute.domain.NodeMetadata

import util.Compute
import models.Nodes
import play.libs.Akka
import akka.actor.Props

object Node extends Controller {

  def index = Action { implicit request =>

    val servers = Nodes.getAll
    val flashMessage = flash.get("success").getOrElse("")

    Ok(views.html.node.index(servers, flashMessage))
  }


  def view(nodeId: String) = Action {  implicit request =>


    val client = Compute.getClient
    val node = Nodes.getOne(nodeId)

    val flashMessage = flash.get("success").getOrElse("")

    Ok(views.html.node.view(node, flashMessage))
  }

  def create = Action {implicit request =>


    val client = Compute.getClient
    val hardwares = client.listHardwareProfiles.toArray.map(_.asInstanceOf[org.jclouds.compute.domain.Hardware])
    val images = client.listImages.toArray.map(_.asInstanceOf[org.jclouds.compute.domain.Image])

    Ok(views.html.node.create(hardwares,images))
  }

  def createConfirm = Action { implicit request =>
    val postData = request.body.asFormUrlEncoded.get
    val emptyValue = Seq("")
    val imageId = postData.get("image").getOrElse(emptyValue)(0)
    val hardwareId = postData.get("hardware").getOrElse(emptyValue)(0)

    val client = Compute.getClient
    val image = client.getImage(imageId)
    val hardwares = client.listHardwareProfiles.toArray.map(_.asInstanceOf[org.jclouds.compute.domain.Hardware])
    val hardwareIndex = hardwares.indexWhere( hardware => hardware.getId == hardwareId )
    val hardware = hardwares(hardwareIndex)
    val location =  client.listAssignableLocations().iterator().next().getId();

    val template = client.templateBuilder()
      .locationId(location)
      .fromHardware(hardware)
      .fromImage(image)
      .build();
    client.createNodesInGroup("test", 1, template)
    println(template.getClass)
    Redirect(routes.Node.index).flashing("success" -> "The node was successfully created")

  }

  def edit(nodeId: String) = TODO

  def recreate(nodeId: String) = TODO

  def delete(nodeId: String) = Action { implicit request =>

    val client = Compute.getClient
    val node = Nodes.getOne(nodeId)

    Ok(views.html.node.delete(node))

  }

  def deleteConfirm(nodeId: String) = Action { implicit request =>

    val client = Compute.getClient
    client.destroyNode(nodeId)

    Redirect(routes.Node.index).flashing("success" -> "The node was successfully deleted")

  }

  def reboot(nodeId: String) = Action {  implicit request =>

    val client = Compute.getClient
    val node = Nodes.getOne(nodeId)

    Ok(views.html.node.reboot(node))

  }

  def rebootConfirm(nodeId: String) = Action { implicit request =>

    val client = Compute.getClient
    client.rebootNode(nodeId)
    val encodedNodeId = java.net.URLEncoder.encode(nodeId, "UTF-8")
    Redirect(routes.Node.view(encodedNodeId)).flashing("success" -> "The node was successfully rebooted")

  }

  // Need to figure out Admin Actions extension for these two.
  def resume(nodeId: String) = TODO

  def suspend(nodeId: String) = TODO

}