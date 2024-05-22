package simulation.traffic

import java.time.LocalTime

object TrafficConditions {

  /**
   * Updates the traffic conditions by calculating the current traffic density.
   * @return the current traffic density as a Double.
   */
  def updateTrafficConditions(): Double = {
    val currentTime = LocalTime.now()
    calculateTrafficDensity(currentTime)
  }

  /**
   * Calculates the traffic density based on the current time.
   * Higher density during peak hours (7-9 AM and 4-6 PM).
   * @param currentTime the current time as LocalTime.
   * @return the traffic density as a Double.
   */
  def calculateTrafficDensity(currentTime: LocalTime): Double = {
    // Higher traffic density during morning peak hours
    if (currentTime.isAfter(LocalTime.of(7, 0)) && currentTime.isBefore(LocalTime.of(9, 0))) {
      1.0
    } 
    // Higher traffic density during evening peak hours
    else if (currentTime.isAfter(LocalTime.of(16, 0)) && currentTime.isBefore(LocalTime.of(18, 0))) {
      0.8
    } 
    // Lower traffic density during off-peak hours
    else {
      0.5
    }
  }
}
