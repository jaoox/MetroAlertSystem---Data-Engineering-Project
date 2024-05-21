package simulation

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.api.common.serialization.SimpleStringSchema
import java.util.Properties
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.apache.flink.api.common.typeinfo.TypeInformation

case class MetroData(timestamp: Long, station: String, personId: Int, hour: Int, position: Double, speed: Double, scenario: String)

object MetroDataProcessing {
  implicit val typeInfoMetroData: TypeInformation[MetroData] = TypeInformation.of(classOf[MetroData])

  def main(args: Array[String]): Unit = {
    implicit val typeInfoString: TypeInformation[String] = TypeInformation.of(classOf[String])

    // Use the local stream execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "test-group")
    properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("auto.offset.reset", "earliest")

    val kafkaConsumer = new FlinkKafkaConsumer[String]("metro-data", new SimpleStringSchema(), properties)

    val stream = env.addSource(kafkaConsumer)

    val dataStream = stream.map { record =>
      implicit val formats = DefaultFormats
      println(s"Received record: $record")
      val data = parse(record).extract[MetroData]
      println(s"Parsed data: $data")
      data
    }

    val alertStream = dataStream.filter { data =>
      val isAlert = data.scenario == "off" && data.position < 300
      if (isAlert) println(s"Alert triggered for data: $data")
      isAlert
    }

    alertStream.print()

    env.execute("Metro Data Processing")
  }
}
