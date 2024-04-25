import org.apache.flink.streaming.api.scala._

object SerializationJob {

  def serializeDataStream(
      dataStream: DataStream[SensorData]
  ): DataStream[String] = {
    // Logique pour convertir SensorData en chaîne JSON
    // ...
  }

  // Éventuellement d'autres fonctions liées à la sérialisation
}
