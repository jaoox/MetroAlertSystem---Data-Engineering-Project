package simulation

import simulation.model.{MetroData, MetroDataProtocol}
import spray.json._
import java.io._
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

  val file = new File("src/main/scala/resources/data_generation.json")
  val pw = new PrintWriter(file)
  try {
    pw.write(jsonData)
    println(s"Data generated and written to ${file.getAbsolutePath}")
  } finally {
    pw.close()
  }
}
