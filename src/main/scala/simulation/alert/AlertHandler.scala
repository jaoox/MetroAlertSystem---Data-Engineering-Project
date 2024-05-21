package simulation.alert

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.api.common.serialization.SimpleStringSchema
import java.util.Properties
import spray.json._
import spray.json.DefaultJsonProtocol._

// Définissez IoTData ici pour éviter les conflits d'importation
case class IoTData(timestamp: Long, station: String, personId: Int, hour: Int, position: Double, speed: Double)

object IoTJsonProtocol extends DefaultJsonProtocol {
  implicit val iotDataFormat = jsonFormat6(IoTData)
}

object AlertHandler {
  import IoTJsonProtocol._

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "iot-alerts")

    val kafkaConsumer = new FlinkKafkaConsumer[String]("iot-data", new SimpleStringSchema(), properties)
    val stream = env.addSource(kafkaConsumer)

    val alertStream = stream
      .map(data => parseIoTData(data))
      .filter(_.speed > 10.0)  // Exemple de condition pour générer une alerte

    alertStream.print()

    env.execute("Flink Alert Handler")
  }

  def parseIoTData(data: String): IoTData = {
    data.parseJson.convertTo[IoTData]
  }
}
