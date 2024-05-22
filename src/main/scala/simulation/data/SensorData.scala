package simulation.data

import scala.util.Random

case class SensorData(sensorId: String, timestamp: Long, weight: Double)

object SensorSimulator extends App {
  private val random = new Random()
  private val minWeight = 10.0 
  private val maxWeight = 1000.0 

  def generateWeight(): Double = {
    minWeight + random.nextDouble() * (maxWeight - minWeight)
  }

  def simulateSensorData(sensorId: String): SensorData = {
    val weight = generateWeight()
    SensorData(sensorId, System.currentTimeMillis(), weight)
  }

  def simulateMultipleSensorData(sensorIds: Seq[String]): Seq[SensorData] = {
    sensorIds.map(sensorId => simulateSensorData(sensorId))
  }
}

case class Person(id: Int, position: Double, speed: Double)

object PopulationGenerator {
  private val random = new Random()

  def generateInitialPopulation(numPeople: Int, railLength: Double): List[Person] = {
=======
object PopulationGenerator {
  def generateInitialPopulation(numPeople: Int, railLength: Double, scenario: String): List[Person] = {
    val random = new Random()
    val initialSpeed = scenario match {
      case "rush_hour" => 5.0 // Vitesse élevée pendant les heures de pointe
      case "off_peak" => 3.0 // Vitesse plus lente en période creuse
      case _ => 4.0 // Vitesse moyenne par défaut
    }
>>>>>>> f550f1c73e835b3f7c4af04b30249978da3faa2c
    (1 to numPeople).map { id =>
      // Position aléatoire le long des rails de métro
      val position = random.nextDouble() * railLength
      val speed = random.nextDouble() * 5
      Person(id, position, speed)
    }.toList
  }
}
