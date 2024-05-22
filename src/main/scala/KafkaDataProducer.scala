package simulation

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import spray.json._
import simulation.model.{MetroData, MetroDataProtocol}

object KafkaDataProducer {
  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    val topic = "metro-data"
    val data = MetroData(System.currentTimeMillis(), "Station1", 1, 10, 50.0, 30.0, "normal")

    val record = new ProducerRecord[String, String](topic, data.toJson.toString())
    producer.send(record)
    producer.close()
  }
}
