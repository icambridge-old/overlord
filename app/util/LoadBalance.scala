package utils


import com.google.common.io.Closeables.closeQuietly

import java.io.Closeable
import java.util.Set

import org.jclouds.ContextBuilder
import org.jclouds.util.Preconditions2
import org.jclouds.openstack.nova.v2_0.extensions._

import  org.jclouds.loadbalancer.LoadBalancerServiceContext
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.NovaAsyncApi;

import play.api.Play

object LoadBalance {

  def getLoadBalancer = {

    val username = Play.current.configuration.getString("rackspace.username").getOrElse("")
    val apiKey   = Play.current.configuration.getString("rackspace.apikey").getOrElse("")
    val context = ContextBuilder.newBuilder("cloudloadbalancers-uk")
      .credentials(username, apiKey)
      .buildView(classOf[LoadBalancerServiceContext])


    val loadBalancer = context.getLoadBalancerService

    loadBalancer
  }

}

