package simulation.analysis

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object SparkAnalysis {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("Spark Analysis")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Define the schema according to your JSON data
    val schema = new StructType()
      .add("hour", IntegerType)
      .add("personId", IntegerType)
      .add("position", DoubleType)
      .add("scenario", StringType)
      .add("speed", DoubleType)
      .add("station", StringType)
      .add("timestamp", LongType)

    val kafkaData = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "metro-data")
      .load()

    val metroData = kafkaData
      .selectExpr("CAST(value AS STRING) as value")
      .select(from_json($"value", schema).as("data"))
      .select("data.*")

    val alertStream = metroData.filter($"scenario" === "alert" && $"position" < 300)

    val query = alertStream.writeStream
      .outputMode("append")
      .format("console")
      .start()

    query.awaitTermination()
  }
}
