package simulation.model

import simulation.data.PopulationGenerator
import simulation.traffic.TrafficConditions
import simulation.movement.PersonMovement
import simulation.serialization.JsonSerializer

object Main {
  def serializeData(population: List[Person], station: String, iteration: Int, scenario: String): List[GeneratedData] = {
    val currentHour = iteration % 24

    population.map { person =>
      GeneratedData(iteration, station, person.id, currentHour, person.position, person.speed, scenario)
    }
  }

  def main(args: Array[String]): Unit = {
    val numPeople = 100
    val railLength = 1000.0
    val scenarios = List("rush", "off")
    var allSerializedData: List[GeneratedData] = List()

    scenarios.foreach { scenario =>
      println(s"Simulation for scenario: $scenario")
      var population = PopulationGenerator.generateInitialPopulation(numPeople, railLength, scenario)

      val numIterations = 10
      (1 to numIterations).foreach { iteration =>
        println(s"Iteration $iteration:")
        val trafficDensity = TrafficConditions.updateTrafficConditions()
        population = PersonMovement.updatePersonPosition(population, railLength, trafficDensity)

        val str = "Ligne " + iteration
        val serializedData = serializeData(population, str, iteration, scenario)
        allSerializedData = allSerializedData ++ serializedData

        serializedData.foreach { data =>
          println(s"Timestamp: ${data.timestamp}, Station: ${data.station}, PersonId: ${data.personId}, Hour: ${data.hour}, Position: ${data.position}, Speed: ${data.speed}, Scenario: ${data.scenario}")
        }
      }
    }

    val json = JsonSerializer.serializeList(allSerializedData)
    val fileName = "simulation_data.json"
    val file = new java.io.PrintWriter(fileName)
    try { file.write(json) } finally { file.close() }
  }
}
