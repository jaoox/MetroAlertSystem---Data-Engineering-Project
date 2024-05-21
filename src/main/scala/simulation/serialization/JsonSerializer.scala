package simulation.serialization

import simulation.model.AlertMessage
import simulation.model.GeneratedData

object JsonSerializer {
  def serialize(alertMessage: AlertMessage): String = {
    s"""{"timestamp":${alertMessage.timestamp},"location":"${alertMessage.location}","description":"${alertMessage.description}"}"""
  }

  def serializeList(dataList: List[GeneratedData]): String = {
    dataList.map { data =>
      s"""{"timestamp":${data.timestamp},"station":"${data.station}","personId":${data.personId},"hour":${data.hour},"position":${data.position},"speed":${data.speed}, "scenario":"${data.scenario}"}"""
    }.mkString("[", ",", "]")
  }

  def deserialize(jsonString: String): Option[AlertMessage] = {
    val pattern = """\{"timestamp":(\d+),"location":"([^"]+)","description":"([^"]+)"\}""".r
    jsonString match {
      case pattern(timestamp, location, description) => Some(AlertMessage(timestamp.toLong, location, description))
      case _ => None
    }
  }
}
