package simulation.alert

import spray.json._
import simulation.model.{MetroData, MetroDataProtocol}
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties
import scala.collection.JavaConverters._
import org.apache.log4j.{Level, Logger}

object AlertHandler {
  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(getClass.getName)
    logger.setLevel(Level.INFO)

    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("group.id", "alert-handler-group")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "earliest")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(java.util.Collections.singletonList("metro-data"))

    while (true) {
      val records = consumer.poll(1000).asScala
      for (record <- records) {
        handleAlert(record.value(), logger)
      }
    }
  }

  def handleAlert(record: String, logger: Logger): Unit = {
    val data = record.parseJson.convertTo[MetroData]
    if (data.scenario == "off" && data.position < 300) {
      logger.warn(s"Alert triggered for data: $data")
    } else {
      logger.info(s"Received data: $data")
    }
  }
}
