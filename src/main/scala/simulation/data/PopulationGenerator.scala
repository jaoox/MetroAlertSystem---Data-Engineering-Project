package simulation.data

import simulation.model.Person
import scala.util.Random

case class Person(id: Int, position: Double, speed: Double)

object PopulationGenerator {
<<<<<<< HEAD
  private val random = new Random()

  object Speeds {
    val RushHour = 5.0 
    val OffPeak = 3.0 
    val Default = 4.0 
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
=======

  def generateInitialPopulation(numPeople: Int, railLength: Double, scenario: String): List[Person] = {
    // Create a random generator instance
    val rand = new Random()

    // Generate a list of Persons with random positions and speeds
    List.fill(numPeople) {
      val id = rand.nextInt(1000000) // Generate a random id for each person
      val position = rand.nextDouble() * railLength // Generate a random position within the rail length
      val speed = rand.nextDouble() * 10 // Generate a random speed, assuming a max speed of 10 units
      Person(id, position, speed)
    }
>>>>>>> origin/joao
  }
}
