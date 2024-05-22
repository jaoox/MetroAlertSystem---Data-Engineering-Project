package simulation.analysis

import spray.json._
import simulation.model.{MetroData, MetroDataProtocol}

object DataAnalysis {
  import MetroDataProtocol._

  def analyze(record: String): MetroData = {
    record.parseJson.convertTo[MetroData]
  }
}
