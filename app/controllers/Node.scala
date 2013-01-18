package controllers

import play.api._
import play.api.mvc._

import org.jclouds.compute.domain.internal.NodeMetadataImpl
import org.jclouds.compute.domain.NodeMetadata

import util.Compute

object Node extends Controller {

  def index = Action {

    val client = Compute.getClient
    val servers: Array[NodeMetadataImpl]  = client.listNodes().toArray.map(_.asInstanceOf[NodeMetadataImpl])


    Ok(views.html.node.index(servers))
  }

  def view(nodeId: String) = Action {


    val client = Compute.getClient
    val node: NodeMetadata  = client.getNodeMetadata(nodeId)


    Ok(views.html.node.view(node))
  }

  def create = TODO

  def edit(nodeId: String) = TODO

  def recreate(nodeId: String) = TODO

  def delete(nodeId: String) = TODO

  def reboot(nodeId: String) = TODO

  def suspend(nodeId: String) = TODO

  def resume(nodeId: String) = TODO

}