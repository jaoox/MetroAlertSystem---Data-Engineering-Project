package simulation.alert

import spray.json._
import simulation.model.{MetroData, MetroDataProtocol}
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import java.util.Properties
import scala.collection.JavaConverters._
import org.apache.log4j.{Level, Logger}
import scala.annotation.tailrec

object AlertHandler {
  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(getClass.getName)
    logger.setLevel(Level.INFO)

    val consumerProps = createConsumerProperties()
    val producerProps = createProducerProperties()

    val consumer = new KafkaConsumer[String, String](consumerProps)
    consumer.subscribe(java.util.Collections.singletonList("metro-data"))

    val producer = new KafkaProducer[String, String](producerProps)

    processRecords(consumer, producer, logger)
  }

  def createConsumerProperties(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("group.id", "alert-handler-group")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "earliest")
    props
  }

  def createProducerProperties(): Properties = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props
  }

  @tailrec
  def processRecords(consumer: KafkaConsumer[String, String], producer: KafkaProducer[String, String], logger: Logger): Unit = {
    val records = consumer.poll(1000).asScala
    records.foreach(record => handleAlert(record.value(), producer, logger))
    processRecords(consumer, producer, logger) // Recursively call processRecords
  }

  def handleAlert(record: String, producer: KafkaProducer[String, String], logger: Logger): Unit = {
    val data = record.parseJson.convertTo[MetroData]
    if (data.scenario == "alert" && data.position < 300) {
      val alertMessage = s"Alert triggered for data: $data"
      logger.warn(alertMessage)
      val alertRecord = new ProducerRecord[String, String]("alerts-topic", null, alertMessage)
      producer.send(alertRecord)
    } else {
      logger.info(s"Received data: $data")
    }
  }
}
