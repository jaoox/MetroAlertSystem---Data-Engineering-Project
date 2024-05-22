package simulation

import simulation.alert.AlertHandler
import simulation.analysis.DataAnalysis
import simulation.model.{MetroData, MetroDataProtocol}
import java.io._
import scala.util.Random
import spray.json._

object Main extends App {
  import MetroDataProtocol._

  // Générateur de données aléatoires pour MetroData
  def generateRandomMetroData: MetroData = {
    MetroData(
      timestamp = Random.nextLong.abs,
      station = "Ligne " + (1 + Random.nextInt(10)),
      personId = Random.nextInt(1000000),
      hour = 1 + Random.nextInt(23),
      position = Random.nextDouble() * 1000,
      speed = Random.nextDouble() * 10,
      scenario = if (Random.nextBoolean()) "rush" else "off"
    )
  }

  // Générer 2000 données aléatoires
  val randomDataList = (1 to 2000).map(_ => generateRandomMetroData)

  // Convertir la liste des données en JSON
  val jsonData = randomDataList.toJson.prettyPrint

  // Écrire le JSON dans un fichier au dossier racine du projet
  val file = new File("data_generation.json")  // Création d'un fichier à la racine du projet
  val pw = new PrintWriter(file)
  pw.write(jsonData)
  pw.close()

  println(s"Data generated and written to ${file.getAbsolutePath}")
}
