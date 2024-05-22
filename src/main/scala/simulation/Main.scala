package simulation

import simulation.alert.AlertHandler
import simulation.analysis.DataAnalysis

object Main {
  def main(args: Array[String]): Unit = {
    val testRecord = """{"timestamp":1629888123000,"station":"Central","personId":1,"hour":10,"position":150.0,"speed":0.0,"scenario":"off"}"""

    // Call the alert handler
    AlertHandler.handleAlert(testRecord)

    // Call the data analysis
    val analyzedData = DataAnalysis.analyze(testRecord)
    println(s"Analyzed Data: $analyzedData")
  }
}
