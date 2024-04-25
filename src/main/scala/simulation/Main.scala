package simulation

import simulation.model._
import simulation.data._
import simulation.movement._
import simulation.management._
import simulation.traffic._

object Main {
  def main(args: Array[String]): Unit = {
    val numPeople = 100
    val railLength = 1000.0

    var population = PopulationGenerator.generateInitialPopulation(numPeople, railLength)

    var iteration = 0
    while (iteration < 10) {
      println(s"Iteration $iteration:")
      // MAJ position des personnes en fonction de leur vitesse de déplacement
      population = PersonMovement.updatePersonPosition(population)

      // Gérer l'ajout/suppression de personnes en fonction des événements de la simulation
      population = PopulationManagement.managePopulation(population)

      // Trafic le long des rails de métro
      TrafficConditions.updateTrafficConditions()

      // Afficher les positions des personnes pour cette itération
      population.foreach { person =>
        println(s"Person ${person.id}: Position = ${person.position}, Speed = ${person.speed}")
      }

      iteration += 1
    }
  }
}
