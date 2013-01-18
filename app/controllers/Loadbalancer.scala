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

object Loadbalancer extends Controller {

  def index = TODO

  def view(loadBalancerId: String) = TODO

  def create = TODO

  def edit(loadBalancerId: String) = TODO

  def recreate(loadBalancerId: String) = TODO

}