package simulation.alert

import spray.json._
import simulation.model.{MetroData, MetroDataProtocol}

object AlertHandler {
  import MetroDataProtocol._

  def handleAlert(record: String): Unit = {
    val data = record.parseJson.convertTo[MetroData]
    if (data.scenario == "off" && data.position < 300) {
      println(s"Alert triggered for data: $data")
    } else {
      println(s"Received data: $data")
    }
  }
}
