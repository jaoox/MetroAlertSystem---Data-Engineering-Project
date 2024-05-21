package simulation.data

import simulation.model.Person
import scala.util.Random

object PopulationGenerator {

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
  }
}
