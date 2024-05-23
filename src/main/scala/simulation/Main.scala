package simulation

import simulation.model.{MetroData, MetroDataProtocol}
import spray.json._
import java.io.{File, PrintWriter}
import scala.util.Random
import scala.util.Using

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
  val file = new File("src/main/scala/resources/data_generation.json")

  writeFile(file, jsonData)

  def writeFile(file: File, data: String): Unit = {
    Using(new PrintWriter(file)) { pw =>
      pw.write(data)
      println(s"Data generated and written to ${file.getAbsolutePath}")
    }.recover {
      case e: Exception => println(s"An error occurred: ${e.getMessage}")
    }
  }
}
