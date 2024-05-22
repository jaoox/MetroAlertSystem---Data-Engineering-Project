package simulation.serialization

import spray.json._
import DefaultJsonProtocol._
import simulation.model.{GeneratedData, Person}

object JsonSerializer extends DefaultJsonProtocol {
  implicit val personFormat = jsonFormat3(Person)
  implicit val generatedDataFormat = jsonFormat7(GeneratedData)

  def serializeList(data: List[GeneratedData]): String = {
    data.toJson.prettyPrint
  }

  def deserializeList(jsonStr: String): List[GeneratedData] = {
    jsonStr.parseJson.convertTo[List[GeneratedData]]
  }
}
