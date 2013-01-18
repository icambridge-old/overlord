package controllers

import play.api._
import play.api.mvc._

import util.Compute

object Image extends Controller {

  def index = Action { implicit Request =>

     val client = Compute.getClient
     val images = client.listImages.toArray.map(_.asInstanceOf[org.jclouds.compute.domain.Image])
     val flashMessage = flash.get("success").getOrElse("")

     Ok(views.html.image.index(images, flashMessage))
  }

  def view(imageId: String) = TODO

  def create = TODO

  def delete(imageId: String) = TODO

}