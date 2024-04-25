<<<<<<< HEAD
// SensorSimulator.scala
import scala.util.Random

object SensorSimulator extends App {
  val random = new Random()

  def generateWeight(): Double = 5 + random.nextDouble() * (500 - 5)

  def simulateSensorData(sensorId: String): SensorData = {
    val weight = generateWeight()
    SensorData(sensorId, System.currentTimeMillis(), weight)
  }

  val sensorId = "sensor-001"
  val simulatedData = simulateSensorData(sensorId)

  println(s"Simulated Data: $simulatedData")
=======
package simulation.data

import scala.util.Random
import simulation.model._

object PopulationGenerator {
  def generateInitialPopulation(numPeople: Int, railLength: Double): List[Person] = {
    val random = new Random()
    (1 to numPeople).map { id =>
      val position = random.nextDouble() * railLength
      val speed = random.nextDouble() * 5
      new Person(id, position, speed)
    }.toList
  }
>>>>>>> ab7f020 (classes et fontions)
}
