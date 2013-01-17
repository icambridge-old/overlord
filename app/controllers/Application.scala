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

object Application extends Controller {
  
  def index = Action {

    val provider = "rackspace-cloudservers-uk"
    val username = Play.current.configuration.getString("rackspace.username").getOrElse("")
    val apiKey   = Play.current.configuration.getString("rackspace.apikey").getOrElse("")

    val context = ContextBuilder.newBuilder(provider)
      .credentials(username, apiKey)
      .buildView(classOf[ComputeServiceContext])
    val compute = context.getComputeService()
    val servers  = compute.listNodes().toArray.map(_.asInstanceOf[NodeMetadataImpl])


    Ok(views.html.index(servers))
  }
  
}