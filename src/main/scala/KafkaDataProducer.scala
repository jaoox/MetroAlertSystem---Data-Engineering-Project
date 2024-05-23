package simulation

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import spray.json._
import simulation.model.{MetroData, MetroDataProtocol}
import scala.util.Random
import org.apache.log4j.{Level, Logger}

object KafkaDataProducer {
  import MetroDataProtocol._

  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(getClass.getName)
    logger.setLevel(Level.INFO)

    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = "metro-data"

    val random = new Random()
    val stations = Seq("Station1", "Station2", "Station3", "Station4", "Station5")

    (1 to 1000).foreach { _ =>
      val data = MetroData(
        timestamp = System.currentTimeMillis(),
        station = stations(random.nextInt(stations.length)),
        personId = random.nextInt(100),
        hour = random.nextInt(24),
        position = random.nextDouble() * 1000,
        speed = random.nextDouble() * 10,
        scenario = if (random.nextBoolean()) "normal" else "alert"
      )
      val record = new ProducerRecord[String, String](topic, data.toJson.toString())
      producer.send(record)
      logger.info(s"Message sent: $data")
    }

    producer.close()
  }
}
