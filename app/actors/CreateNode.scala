package actors

import utils.Compute
import entity.Template

import akka.actor._
import play.api._
import org.jclouds.compute.domain.NodeMetadata

class CreateNode extends Actor {
  def receive = {
    case Template(imageId, hardwareId, group) => {

      Logger.info("Starting node creation")
      val client = Compute.getClient
      val image = client.getImage(imageId)
      val hardwares = client.listHardwareProfiles.toArray.map(_.asInstanceOf[org.jclouds.compute.domain.Hardware])
      val hardwareIndex = hardwares.indexWhere( hardware => hardware.getId == hardwareId )
      val hardware = hardwares(hardwareIndex)
      val location =  client.listAssignableLocations().iterator().next().getId();

      val template = client.templateBuilder()
        .locationId(location)
        .fromHardware(hardware)
        .fromImage(image)
        .build();
      //
      val nodes = client.createNodesInGroup(group, 1, template).toArray.map(_.asInstanceOf[NodeMetadata])
      val node = nodes(0)
      Logger.info("Node " + node.getName + " created")
    }
  }
}
