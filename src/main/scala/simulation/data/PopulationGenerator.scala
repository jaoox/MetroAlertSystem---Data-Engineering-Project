package simulation.data

import scala.util.Random

case class DataPerson(id: Int, position: Double, speed: Double)

object PopulationGenerator {
  private val random = new Random()

  def generateInitialPopulation(numPeople: Int, railLength: Double, scenario: String): List[DataPerson] = {
    val initialSpeed = scenario match {
      case "rush_hour" => 5.0 // High speed during rush hours
      case "off_peak" => 3.0 // Slower speed during off-peak hours
      case _ => 4.0 // Default average speed
    }
    (1 to numPeople).map { id =>
      // Random position along the subway rails
      val position = random.nextDouble() * railLength
      val speed = random.nextDouble() * 5
      DataPerson(id, position, speed)
    }.toList
  }
}
