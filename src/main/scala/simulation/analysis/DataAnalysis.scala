package simulation.analysis

import org.apache.spark.sql.SparkSession
import spray.json._
import DefaultJsonProtocol._

case class IoTData(timestamp: Long, station: String, personId: Int, hour: Int, position: Double, speed: Double)

object DataAnalysis {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("IoT Data Analysis").getOrCreate()
    import spark.implicits._

    val df = spark.read.json("hdfs://localhost:9000/path/to/iot-data")
    val iotData = df.as[IoTData]

    iotData.createOrReplaceTempView("iot_data")
    val result = spark.sql("SELECT hour, COUNT(*) AS count FROM iot_data WHERE speed > 10.0 GROUP BY hour")

    result.show()
    spark.stop()
  }
}
