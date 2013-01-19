package controllers

import play.api._
import play.api.mvc._

import com.google.common.io.Closeables.closeQuietly

import java.io.Closeable
import java.util.Set

import org.jclouds.loadbalancer.domain.LoadBalancerMetadata

import util.LoadBalance

object Loadbalancer extends Controller {

  def index = Action { implicit request =>

    val loadBalance = LoadBalance.getLoadBalancer

    val loadBalancers = loadBalance.listLoadBalancers.toArray.map( _.asInstanceOf[LoadBalancerMetadata] )
    val flashMessage = flash.get("success").getOrElse("")

    Ok(views.html.loadbalancer.index(loadBalancers, flashMessage))
  }

  def view(loadBalancerId: String) = TODO

  def create = TODO

  def edit(loadBalancerId: String) = TODO

  def recreate(loadBalancerId: String) = TODO


  def delete(loadBalancerId: String) = TODO

}