package simulation.traffic

object TrafficConditions {
  def updateTrafficConditions(): Double = {
    val currentTime = java.time.LocalTime.now()
    calculateTrafficDensity(currentTime)
  }

  def calculateTrafficDensity(currentTime: java.time.LocalTime): Double = {
    // Densité trafic plus élevée pendant les heures de pointe
    if (currentTime.isAfter(java.time.LocalTime.of(7, 0)) && currentTime.isBefore(java.time.LocalTime.of(9, 0))) {
      // Matin
      1.0
    } else if (currentTime.isAfter(java.time.LocalTime.of(16, 0)) && currentTime.isBefore(java.time.LocalTime.of(18, 0))) {
      // Soir
      0.8
    } else {
      // Hors heures de pointe
      0.5
    }
  }
}
