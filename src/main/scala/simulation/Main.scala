package simulation.model

import simulation.data.PopulationGenerator
import simulation.traffic.TrafficConditions
import simulation.movement.PersonMovement
import simulation.serialization.JsonSerializer
import com.typesafe.scalalogging.LazyLogging

object Main extends LazyLogging {

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
      logger.info(s"Simulation for scenario: $scenario")
      var population = PopulationGenerator.generateInitialPopulation(numPeople, railLength, scenario)

      val numIterations = 10
      (1 to numIterations).foreach { iteration =>
        logger.info(s"Iteration $iteration:")
        val trafficDensity = TrafficConditions.updateTrafficConditions()
        population = PersonMovement.updatePopulationPositions(population, trafficDensity)

        val str = "Ligne " + iteration
        val serializedData = serializeData(population, str, iteration, scenario)
        allSerializedData = allSerializedData ++ serializedData

        serializedData.foreach { data =>
          logger.info(s"Timestamp: ${data.timestamp}, Station: ${data.station}, PersonId: ${data.personId}, Hour: ${data.hour}, Position: ${data.position}, Speed: ${data.speed}, Scenario: ${data.scenario}")
        }
      }
    }

    val json = JsonSerializer.serializeList(allSerializedData)
    val fileName = "src/main/scala/resources/simulation_data.json" // Ensure correct path
    val file = new java.io.PrintWriter(fileName)
    try {
      file.write(json)
      logger.info(s"Data successfully written to $fileName")
    } catch {
      case e: Exception => logger.error(s"Error writing to file $fileName", e)
    } finally {
      file.close()
    }
  }
}
