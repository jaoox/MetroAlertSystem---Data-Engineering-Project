package simulation.alert

import spray.json._
import simulation.model.{MetroData, MetroDataProtocol}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import java.util.Properties
import scala.collection.JavaConverters._
import org.apache.log4j.{Level, Logger}

object AlertHandler {
  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(getClass.getName)
    logger.setLevel(Level.INFO)

    // Consumer properties
    val consumerProps = new Properties()
    consumerProps.put("bootstrap.servers", "localhost:9092")
    consumerProps.put("group.id", "alert-handler-group")
    consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    consumerProps.put("auto.offset.reset", "earliest")

    val consumer = new KafkaConsumer[String, String](consumerProps)
    consumer.subscribe(java.util.Collections.singletonList("metro-data"))

    // Producer properties
    val producerProps = new Properties()
    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](producerProps)

    try {
      while (true) {
        val records = consumer.poll(1000).asScala
        for (record <- records) {
          handleAlert(record.value(), producer, logger)
        }
      }
    } finally {
      consumer.close()
      producer.close()
    }
  }

  def handleAlert(record: String, producer: KafkaProducer[String, String], logger: Logger): Unit = {
    val data = record.parseJson.convertTo[MetroData]
    if (data.scenario == "alert" && data.position < 300) {
      val alertMessage = s"Alert triggered for data: $data"
      logger.warn(alertMessage)
      // Sending alert to Kafka topic
      val alertRecord = new ProducerRecord[String, String]("alerts-topic", null, alertMessage)
      producer.send(alertRecord)
    } else {
      logger.info(s"Received data: $data")
    }
  }
}
