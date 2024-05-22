package simulation

import scala.io.Source
import spray.json._
import DefaultJsonProtocol._
import java.io._

object DataAlertGenerator {
  case class MetroData(hour: Int, personId: Int, position: Double, scenario: String, speed: Double, station: String, timestamp: Long)

  object MetroDataProtocol extends DefaultJsonProtocol {
    implicit val metroDataFormat = jsonFormat7(MetroData)
  }

  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    // Correct relative path to load data from the JSON file
    val dataPath = "src/main/scala/resources/data_generation.json"
    val source = Source.fromFile(dataPath)
    val lines = try source.mkString finally source.close()

    // Convert the JSON to a list of MetroData
    val data = lines.parseJson.convertTo[List[MetroData]]

    // Filter data to find alert situations
    val alerts = data.filter(d => d.position > 800 && d.speed > 5)

    // Convert filtered alerts to JSON
    val jsonAlerts = alerts.toJson.prettyPrint

    // Correct relative path to save the filtered alerts to a new JSON file
    val alertPath = "src/main/scala/resources/data_alert.json"
    val file = new File(alertPath)
    val pw = new PrintWriter(file)
    try {
      pw.write(jsonAlerts)
      println(s"Alert data written to ${file.getAbsolutePath}")
    } finally {
      pw.close()
    }
  }
}
