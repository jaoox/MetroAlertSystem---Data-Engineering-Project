package simulation.alert

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import simulation.model.AlertMessage
import org.apache.flink.util.Collector

object AlertHandler {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val properties = new java.util.Properties()
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "flink-alert-group")

    val kafkaConsumer = new FlinkKafkaConsumer[String]("alerts", new SimpleStringSchema(), properties)
    val alertStream = env.addSource(kafkaConsumer)

    alertStream
      .map(record => parseAlert(record))
      .filter(alert => alert.isCritical)
      .timeWindowAll(Time.minutes(1))
      .apply((window, alerts, out: Collector[String]) => {
        val alertSummary = s"Window ${window.getStart}-${window.getEnd} has ${alerts.size} critical alerts."
        out.collect(alertSummary)
      })

    env.execute("Flink Alert Handler")
  }

  def parseAlert(record: String): AlertMessage = {
    // Parse logic here
    AlertMessage(record)
  }
}

case class AlertMessage(raw: String) {
  def isCritical: Boolean = {
    // Determine if the alert is critical
    raw.contains("CRITICAL")
  }
}
