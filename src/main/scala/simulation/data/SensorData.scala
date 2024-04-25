package simulation.data

import scala.util.Random

case class SensorData(sensorId: String, timestamp: Long, weight: Double)

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
}
