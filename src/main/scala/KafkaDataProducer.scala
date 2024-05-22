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

<<<<<<< HEAD
    // Utilisation du chemin absolu
    val filePath = "/Users/anas/Doc/Data_Engineering/Data_Engenieur_project/simulation_data.json"
    val source = Source.fromFile(filePath)
    val data = source.getLines().toList
    source.close()

    // Envoi des données à Kafka
    data.foreach { jsonLine =>
      producer.send(new ProducerRecord[String, String]("metro-data", jsonLine))
    }
=======
    val topic = "metro-data"
    val data = MetroData(System.currentTimeMillis(), "Station1", 1, 10, 50.0, 30.0, "normal")
>>>>>>> origin/joao

    val record = new ProducerRecord[String, String](topic, data.toJson.toString())
    producer.send(record)
    producer.close()
  }
}
