package simulation

import simulation.model.{MetroData, MetroDataProtocol}
import spray.json._
import java.nio.file.{Files, Paths}
import scala.util.Random

object Main extends App {
  import MetroDataProtocol._

  def generateRandomMetroData: MetroData = {
    MetroData(
      timestamp = System.currentTimeMillis(),
      station = s"Ligne ${1 + Random.nextInt(10)}",
      personId = Random.nextInt(1000000),
      hour = 1 + Random.nextInt(23),
      position = Random.nextDouble() * 1000,
      speed = Random.nextDouble() * 10,
      scenario = if (Random.nextBoolean()) "rush" else "off"
    )
  }

  val randomDataList = (1 to 2000).map(_ => generateRandomMetroData).toList

  val jsonData = randomDataList.toJson.prettyPrint

  val filePath = "src/main/scala/resources/data_generation.json"

  // Écrire les données générées dans le fichier JSON
  Files.write(Paths.get(filePath), jsonData.getBytes)
  println(s"Data generated and written to $filePath")
}
