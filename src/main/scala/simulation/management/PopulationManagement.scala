package simulation.management

import simulation.model._

object PopulationManagement {
  def managePopulation(people: List[Person]): List[Person] = {
    // Entrée et sortie des personnes à des stations aléatoires
    val random = new scala.util.Random()
    val stationArrivalProbability = 0.1 // Probabilité d'arrivée à une station
    val stationDepartureProbability = 0.05 // Probabilité de sortie d'une station

    // Simulation de l'arrivée à une station
    val newPeople = people.flatMap { person =>
      if (random.nextDouble() < stationArrivalProbability) {
        // Ajouter une nouvelle personne à une station avec une position aléatoire
        val newPosition = random.nextDouble() * 100
        Some(new Person(people.length + 1, newPosition, person.speed))
      } else {
        None
      }
    }

    // Simulation de sortie d'une station
    val updatedPeople = people.filter { person =>
      if (random.nextDouble() < stationDepartureProbability) {
        false
      } else {
        true
      }
    }

    updatedPeople ++ newPeople
  }
}
