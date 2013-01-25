package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import utils.{Compute, LoadBalance}


import org.jclouds.loadbalancer.domain.LoadBalancerMetadata

object Group extends Controller {

  val form = Form(
    mapping(
      "id" -> optional(number),
      "name" -> nonEmptyText,
      "loadBalancerId" -> nonEmptyText,
      "hardwareId" -> nonEmptyText,
      "imageId" -> nonEmptyText
    )(entity.Group.apply)(entity.Group.unapply))

  def index = TODO

  def create = Action { implicit request =>

    val client = Compute.getClient
    val loadBalance = LoadBalance.getLoadBalancer

    val loadBalancers = loadBalance.listLoadBalancers.toArray.map({ item =>
      val loadbalancer = item.asInstanceOf[LoadBalancerMetadata]
      (loadbalancer.getId, loadbalancer.getName)
    }).toSeq


    val images = client.listImages.toArray.map({ item =>
        val image = item.asInstanceOf[org.jclouds.compute.domain.Image]
        (image.getId, image.getName)
    }).toSeq


    val hardwares = client.listHardwareProfiles.toArray.map({ item =>
     val hardware = item.asInstanceOf[org.jclouds.compute.domain.Hardware]
     (hardware.getId,hardware.getName)
    }).toSeq


    Ok(views.html.groups.create(form ,loadBalancers, images, hardwares))
  }

  def createConfirm = TODO

  def edit(groupId: String) = TODO

  def editConfirm(groupId: String) = TODO

  def view(groupId: String) = TODO

}