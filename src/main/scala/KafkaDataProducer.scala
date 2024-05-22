package simulation

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import spray.json._
import simulation.model.{MetroData, MetroDataProtocol}
import scala.util.Random

object KafkaDataProducer {
  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = "metro-data"

    val random = new Random()
    val stations = Seq("Station1", "Station2", "Station3", "Station4", "Station5")

    val data = (1 to 200).map { _ =>
      val timestamp = System.currentTimeMillis()
      val station = stations(random.nextInt(stations.length))
      val personId = random.nextInt(100)
      val position = random.nextDouble() * 1000
      val speed = random.nextDouble() * 10
      val scenario = if (random.nextBoolean()) "normal" else "alert"
      MetroData(timestamp, station, personId, 10, position, speed, scenario)
    }

    data.foreach { recordData =>
      val record = new ProducerRecord[String, String](topic, recordData.toJson.toString())
      producer.send(record)
    }

    producer.close()
  }
}
