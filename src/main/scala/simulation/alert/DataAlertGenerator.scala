package simulation

import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._
import scala.io.Source
import spray.json._
import DefaultJsonProtocol._
import java.nio.file.{Files, Paths}

object DataAlertGenerator {
  case class MetroData(hour: Int, personId: Int, position: Double, scenario: String, speed: Double, station: String, timestamp: Long)

  object MetroDataProtocol extends DefaultJsonProtocol {
    implicit val metroDataFormat = jsonFormat7(MetroData)
  }

  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    // Initialiser SparkSession
    val spark = SparkSession.builder()
      .appName("DataAlertGenerator")
      .config("spark.master", "local")
      .getOrCreate()

    import spark.implicits._

    // Charger les données depuis le fichier JSON
    val dataPath = "src/main/scala/resources/data_generation.json"
    val jsonString = new String(Files.readAllBytes(Paths.get(dataPath)))
    val data = jsonString.parseJson.convertTo[List[MetroData]]

    // Convertir les données en DataFrame
    val df = spark.createDataset(data).toDF()

    // Filtrer les données pour trouver les situations d'alerte
    val alertDF = df.filter($"position" > 800 && $"speed" > 5)

    // Convertir les alertes filtrées en JSON
    val jsonAlerts = alertDF.toJSON.collect().mkString("[", ",", "]")

    // Chemin vers le fichier d'alertes
    val alertPath = "src/main/scala/resources/data_alert.json"

    // Écrire les alertes filtrées dans un nouveau fichier JSON
    Files.write(Paths.get(alertPath), jsonAlerts.getBytes)
    println(s"Alert data written to $alertPath")

    // Arrêter la session Spark
    spark.stop()
  }
}
