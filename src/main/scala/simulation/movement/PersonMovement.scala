package simulation.movement

import simulation.model._

object PersonMovement {
  def updatePersonPosition(people: List[Person], railLength: Double, trafficDensity: Double): List[Person] = {
    people.map { person =>
      val newPosition = person.position + person.speed * trafficDensity
      //Position reste dans les limites des rails de métro
      val boundedPosition = if (newPosition < 0) 0 else if (newPosition > railLength) railLength else newPosition
      person.updatePosition(boundedPosition)
    }
  }
}
