package controllers

import play.api._
import play.api.mvc._

import com.google.common.io.Closeables.closeQuietly

import java.io.Closeable
import java.util.Set

import org.jclouds.ContextBuilder
import org.jclouds.compute.ComputeService
import org.jclouds.compute.ComputeServiceContext
import org.jclouds.compute.domain.ComputeMetadata
import org.jclouds.compute.domain.NodeMetadata
import org.jclouds.compute.predicates.NodePredicates
import org.jclouds.util.Preconditions2
import org.jclouds.compute.domain.internal.NodeMetadataImpl
import org.jclouds.compute.domain.NodeMetadata

object Node extends Controller {

  def index = Action {

    val provider = "rackspace-cloudservers-uk"
    val username = Play.current.configuration.getString("rackspace.username").getOrElse("")
    val apiKey   = Play.current.configuration.getString("rackspace.apikey").getOrElse("")

    val context = ContextBuilder.newBuilder(provider)
      .credentials(username, apiKey)
      .buildView(classOf[ComputeServiceContext])
    val compute = context.getComputeService()
    val servers: Array[NodeMetadataImpl]  = compute.listNodes().toArray.map(_.asInstanceOf[NodeMetadataImpl])


    Ok(views.html.node.index(servers))
  }

  def view(nodeId: String) = Action {

    val provider = "rackspace-cloudservers-uk"
    val username = Play.current.configuration.getString("rackspace.username").getOrElse("")
    val apiKey   = Play.current.configuration.getString("rackspace.apikey").getOrElse("")

    val context = ContextBuilder.newBuilder(provider)
      .credentials(username, apiKey)
      .buildView(classOf[ComputeServiceContext])
    val compute = context.getComputeService()
    val node: NodeMetadata  = compute.getNodeMetadata(nodeId)


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