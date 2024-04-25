package simulation.data

import scala.util.Random
import simulation.model._

import scala.util.Random

case class Person(id: Int, position: Double, speed: Double)

object PopulationGenerator {
  private val random = new Random()

  // Définition des vitesses correspondant à différents scénarios
  object Speeds {
    val RushHour = 5.0 // Vitesse élevée pendant les heures de pointe
    val OffPeak = 3.0 // Vitesse plus lente en période creuse
    val Default = 4.0 // Vitesse moyenne par défaut
  }

  def generateInitialPopulation(numPeople: Int, railLength: Double, scenario: String): List[Person] = {
    val initialSpeed = scenario match {
      case "rush_hour" => Speeds.RushHour
      case "off_peak" => Speeds.OffPeak
      case _ => Speeds.Default
    }
    (1 to numPeople).map { id =>
      // Position aléatoire le long des rails de métro
      val position = random.nextDouble() * railLength
      Person(id, position, initialSpeed)
    }.toList
  }
}
