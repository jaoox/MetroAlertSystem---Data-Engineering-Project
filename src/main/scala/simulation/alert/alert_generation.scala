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
    // Chemin absolu pour charger les données depuis le fichier JSON
    val dataPath = "/Data_Engineering/Data_Engenieur_project/data_generation.json"
    val source = Source.fromFile(dataPath)
    val lines = try source.mkString finally source.close()

    // Convertir le JSON en liste de MetroData
    val data = lines.parseJson.convertTo[List[MetroData]]

    // Filtrer les données pour trouver les situations d'alerte
    val alerts = data.filter(d => d.position > 800 && d.speed > 5)

    // Convertir les alertes filtrées en JSON
    val jsonAlerts = alerts.toJson.prettyPrint

    // Chemin absolu pour sauvegarder les alertes filtrées dans un nouveau fichier JSON
    val alertPath = "/Users/anas/Doc/Data_Engineering/Data_Engenieur_project/data_generation.json"
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
