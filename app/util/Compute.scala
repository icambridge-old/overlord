package util


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
import org.jclouds.openstack.nova.v2_0.extensions._

import  org.jclouds.loadbalancer.LoadBalancerServiceContext

import com.google.common.collect.ImmutableSet

import java.util.Properties;
import play.api.Play

object Compute {

  def getClient = {
    val provider = Play.current.configuration.getString("rackspace.driver").getOrElse("rackspace-cloudservers-uk")
    val username = Play.current.configuration.getString("rackspace.username").getOrElse("")
    val apiKey   = Play.current.configuration.getString("rackspace.apikey").getOrElse("")

    val context = ContextBuilder.newBuilder(provider)
      .credentials(username, apiKey)
      .buildView(classOf[ComputeServiceContext])
    val compute = context.getComputeService()
    compute
  }


}

