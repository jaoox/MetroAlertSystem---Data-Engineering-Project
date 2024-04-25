import scala.util.Random
package simulation.data
import simulation.model._

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



object PopulationGenerator {
  def generateInitialPopulation(numPeople: Int, railLength: Double): List[Person] = {
    val random = new Random()
    (1 to numPeople).map { id =>
      val position = random.nextDouble() * railLength
      val speed = random.nextDouble() * 5
      new Person(id, position, speed)
    }.toList
  }
}
