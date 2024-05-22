package simulation.model

import spray.json.DefaultJsonProtocol._
import spray.json._

case class MetroData(timestamp: Long, station: String, personId: Int, hour: Int, position: Double, speed: Double, scenario: String)

object MetroDataProtocol extends DefaultJsonProtocol {
  implicit val metroDataFormat: RootJsonFormat[MetroData] = jsonFormat7(MetroData)
}
