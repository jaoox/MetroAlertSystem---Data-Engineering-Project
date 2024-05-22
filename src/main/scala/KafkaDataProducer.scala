package simulation

import java.util.Properties
import scala.io.Source
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object KafkaDataProducer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "all")

    val producer = new KafkaProducer[String, String](props)

    // Utilisation du chemin absolu
    val filePath = "/Users/anas/Doc/Data_Engineering/Data_Engenieur_project/simulation_data.json"
    val source = Source.fromFile(filePath)
    val data = source.getLines().toList
    source.close()

    // Envoi des données à Kafka
    data.foreach { jsonLine =>
      producer.send(new ProducerRecord[String, String]("metro-data", jsonLine))
    }

    producer.close()
  }
}
