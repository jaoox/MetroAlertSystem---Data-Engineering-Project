package simulation.movement

import simulation.model.Person

object PersonMovement {
  def updatePersonPosition(person: Person, trafficDensity: Double): Person = {
    val newSpeed = person.speed * (1 - trafficDensity)
    val newPosition = person.position + newSpeed
    person.copy(position = newPosition, speed = newSpeed)
  }

  def updatePopulationPositions(population: List[Person], trafficDensity: Double): List[Person] = {
    population.map { person =>
      updatePersonPosition(person, trafficDensity)
    }
  }
}
