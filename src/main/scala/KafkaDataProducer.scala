import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object KafkaDataProducer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    val data = List(
      """{"Timestamp": 8, "Station": "Ligne 8", "PersonId": 11, "Hour": 8, "Position": 219.3934086212331, "Speed": 4.0, "Scenario": "off"}""",
      """{"Timestamp": 8, "Station": "Ligne 8", "PersonId": 12, "Hour": 8, "Position": 804.6359635867948, "Speed": 4.0, "Scenario": "off"}""",
      """{"Timestamp": 8, "Station": "Ligne 8", "PersonId": 13, "Hour": 8, "Position": 894.6087557599926, "Speed": 4.0, "Scenario": "off"}"""
    )

    data.foreach { record =>
      producer.send(new ProducerRecord[String, String]("metro-data", record))
    }

    producer.close()
  }
}