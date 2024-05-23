package simulation

import scala.io.Source
import spray.json._
import DefaultJsonProtocol._
import java.io.{File, PrintWriter}
import scala.util.Using

object DataAlertGenerator {
  case class MetroData(hour: Int, personId: Int, position: Double, scenario: String, speed: Double, station: String, timestamp: Long)

  object MetroDataProtocol extends DefaultJsonProtocol {
    implicit val metroDataFormat = jsonFormat7(MetroData)
  }

  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    // Load data from the JSON file
    val dataPath = "src/main/scala/resources/data_generation.json"
    val lines = readFile(dataPath)

    // Convert JSON to a list of MetroData
    val data = lines.parseJson.convertTo[List[MetroData]]

    // Filter data to find alert situations
    val alerts = data.filter(d => d.position > 800 && d.speed > 5)

    // Convert filtered alerts to JSON
    val jsonAlerts = alerts.toJson.prettyPrint

    // Save the filtered alerts to a new JSON file
    val alertPath = "src/main/scala/resources/data_alert.json"
    writeFile(alertPath, jsonAlerts)
  }

  def readFile(filePath: String): String = {
    Using(Source.fromFile(filePath)) { source =>
      source.mkString
    }.getOrElse {
      throw new FileNotFoundException(s"File not found: $filePath")
    }
  }

  def writeFile(filePath: String, data: String): Unit = {
    Using(new PrintWriter(new File(filePath))) { pw =>
      pw.write(data)
      println(s"Alert data written to $filePath")
    }.recover {
      case e: Exception => println(s"An error occurred: ${e.getMessage}")
    }
  }
}
