package simulation

import simulation.model._
import simulation.data._
import simulation.movement._
import simulation.traffic._

object Main {
  def main(args: Array[String]): Unit = {
    val numPeople = 100
    val railLength = 1000.0
    val scenarios = List("rush_hour", "off_peak")

    scenarios.foreach { scenario =>
      println(s"Simulation for scenario: $scenario")
      var population = PopulationGenerator.generateInitialPopulation(numPeople, railLength, scenario)

      val numIterations = 10
      (1 to numIterations).foreach { iteration =>
        println(s"Iteration $iteration:")
        val trafficDensity = TrafficConditions.updateTrafficConditions()
        population = PersonMovement.updatePersonPosition(population, railLength, trafficDensity)

        population.foreach { person =>
          println(s"Person ${person.id}: position = ${person.position}, speed = ${person.speed}")
        }
      }
    }
  }
}