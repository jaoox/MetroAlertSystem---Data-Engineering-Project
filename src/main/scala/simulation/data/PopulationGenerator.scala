package simulation.data

import scala.util.Random
import simulation.model._

object PopulationGenerator {
  def generateInitialPopulation(numPeople: Int, railLength: Double, scenario: String): List[Person] = {
    val random = new Random()
    val initialSpeed = scenario match {
      case "rush_hour" => 5.0 // Vitesse élevée pendant les heures de pointe
      case "off_peak" => 3.0 // Vitesse plus lente en période creuse
      case _ => 4.0 // Vitesse moyenne par défaut
    }
    (1 to numPeople).map { id =>
      // Position aléatoire le long des rails de métro
      val position = random.nextDouble() * railLength
      new Person(id, position, initialSpeed)
    }.toList
  }
}
