package simulation

import java.util.Properties
import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.collection.JavaConverters._
import spray.json._
import simulation.model.{MetroData, MetroDataProtocol}

object MetroDataProcessing {
  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("group.id", "test-group")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "earliest")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(java.util.Collections.singletonList("metro-data"))

    while (true) {
      val records = consumer.poll(100).asScala
      for (record <- records) {
        val data = record.value().parseJson.convertTo[MetroData]
        if (data.scenario == "off" && data.position < 300) {
          println(s"Alert triggered for data: $data")
        } else {
          println(s"Received data: $data")
        }
      }
    }
  }
}
