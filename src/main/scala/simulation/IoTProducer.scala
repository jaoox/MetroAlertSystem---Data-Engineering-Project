package simulation

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import java.util.Properties
import scala.io.Source
import spray.json._
import DefaultJsonProtocol._

object IoTProducer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = "iot-data"

    val jsonSource = Source.fromFile("path/to/your/json/simulation_data.json")
    val jsonData = jsonSource.getLines.mkString

    val dataList = jsonData.parseJson.convertTo[List[Map[String, Any]]]

    dataList.foreach { data =>
      val record = new ProducerRecord[String, String](topic, data.toJson.toString())
      producer.send(record)
    }

    producer.close()
    jsonSource.close()
  }
}
