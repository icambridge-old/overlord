package controllers

import play.api._
import play.api.mvc._

import org.jclouds.compute.domain.internal.NodeMetadataImpl
import org.jclouds.compute.domain.NodeMetadata

import util.Compute

object Node extends Controller {

  def index = Action { implicit request =>

    val client = Compute.getClient
    val servers: Array[NodeMetadataImpl]  = client.listNodes().toArray.map(_.asInstanceOf[NodeMetadataImpl])
    val flashMessage = flash.get("success").getOrElse("")

    Ok(views.html.node.index(servers, flashMessage))
  }

  def view(nodeId: String) = Action {


    val client = Compute.getClient
    val node: NodeMetadata  = client.getNodeMetadata(nodeId)


    Ok(views.html.node.view(node))
  }

  def create = TODO

  def edit(nodeId: String) = TODO

  def recreate(nodeId: String) = TODO

  def delete(nodeId: String) = Action {

    val client = Compute.getClient
    val node: NodeMetadata  = client.getNodeMetadata(nodeId)

    Ok(views.html.node.delete(node))

  }

  def deleteConfirm(nodeId: String) = Action { implicit request =>

    val client = Compute.getClient
    client.destroyNode(nodeId)

    Redirect(routes.Node.index).flashing("success" -> "The node was successfully deleted")

  }

  def reboot(nodeId: String) = TODO

  def suspend(nodeId: String) = TODO

  def resume(nodeId: String) = TODO

}