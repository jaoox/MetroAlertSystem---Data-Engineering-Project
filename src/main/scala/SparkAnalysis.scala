package simulation.analysis

import org.apache.spark.sql.SparkSession
import org.json4s._
import org.json4s.jackson.JsonMethods._
import simulation.model.MetroData

object SparkAnalysis {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Spark Analysis").getOrCreate()
    import spark.implicits._

    val kafkaData = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "metro-data")
      .load()

    implicit val formats = DefaultFormats

    val metroData = kafkaData.selectExpr("CAST(value AS STRING)")
      .as[String]
      .map(record => parse(record).extract[MetroData])

    val alertStream = metroData.filter(data => data.scenario == "off" && data.position < 300)

    val query = alertStream.writeStream
      .outputMode("append")
      .format("console")
      .start()

    query.awaitTermination()
  }
}
