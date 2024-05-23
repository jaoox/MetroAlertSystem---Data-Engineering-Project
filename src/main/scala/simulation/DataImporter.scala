package simulation

import org.mongodb.scala._
import scala.io.Source
import spray.json._
import DefaultJsonProtocol._
import scala.concurrent.Await
import scala.concurrent.duration._

object DataImporter {
  case class MetroData(hour: Int, personId: Int, position: Double, scenario: String, speed: Double, station: String, timestamp: Long)
  object MetroDataJsonProtocol extends DefaultJsonProtocol {
    implicit val metroDataFormat = jsonFormat7(MetroData)
  }

  def main(args: Array[String]): Unit = {
    import MetroDataJsonProtocol._

    // Charger les données depuis le fichier JSON
    val jsonString = Source.fromFile("src/main/scala/resources/data_alert.json").mkString
    val data = jsonString.parseJson.convertTo[List[MetroData]]

    // Connexion à MongoDB
    val mongoClient: MongoClient = MongoClient("mongodb://localhost")
    val database: MongoDatabase = mongoClient.getDatabase("data_engineering")
    val collection: MongoCollection[Document] = database.getCollection("metro_data")

    // Insérer les données dans la base de données
    val documents = data.map(d => Document(
      "hour" -> d.hour,
      "personId" -> d.personId,
      "position" -> d.position,
      "scenario" -> d.scenario,
      "speed" -> d.speed,
      "station" -> d.station,
      "timestamp" -> d.timestamp
    ))

    Await.result(collection.insertMany(documents).toFuture(), 30.seconds)
    println("Data imported into MongoDB")

    mongoClient.close()
  }
}
