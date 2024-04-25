package simulation.traffic

object TrafficConditions {
  // Mdéliser le trafic le long des rails de métro
  def updateTrafficConditions(): Unit = {
    // Densité de population le long des rails de métro
    val currentTime = java.time.LocalTime.now()
    val trafficDensity = calculateTrafficDensity(currentTime) // Densité trafic en fonction de l'heure
  }

  def calculateTrafficDensity(currentTime: java.time.LocalTime): Double = {
    // Densité trafic plus élevée pendant les heures de pointe
    if (currentTime.isAfter(java.time.LocalTime.of(7, 0)) && currentTime.isBefore(java.time.LocalTime.of(9, 0))) {
      // Heures de pointe matin
      return 1.0 // Densité maximale de trafic
    } else if (currentTime.isAfter(java.time.LocalTime.of(16, 0)) && currentTime.isBefore(java.time.LocalTime.of(18, 0))) {
      // Heures de pointe soir
      return 0.8 // Densité élevée de trafic
    } else {
      // Hors heures de pointe
      return 0.5 // Densité moyenne de trafic
    }
  }
}
